#!/usr/bin/env python3
"""Rename an equal-length JNI package prefix inside an Android ELF library."""

from __future__ import annotations

import argparse
import struct
from pathlib import Path


def elf_hash(name: bytes) -> int:
    value = 0
    for byte in name:
        value = (value << 4) + byte
        high = value & 0xF0000000
        if high:
            value ^= high >> 24
        value &= ~high
    return value & 0xFFFFFFFF


def gnu_hash(name: bytes) -> int:
    value = 5381
    for byte in name:
        value = ((value * 33) + byte) & 0xFFFFFFFF
    return value


def section_table(data: bytearray) -> dict[str, tuple[int, int, int]]:
    if data[:4] != b"\x7fELF" or data[5] != 1:
        raise ValueError("Only little-endian ELF files are supported")

    elf_class = data[4]
    if elf_class == 2:
        shoff = struct.unpack_from("<Q", data, 40)[0]
        shentsize, shnum, shstrndx = struct.unpack_from("<HHH", data, 58)
        header_format = "<IIQQQQIIQQ"
    elif elf_class == 1:
        shoff = struct.unpack_from("<I", data, 32)[0]
        shentsize, shnum, shstrndx = struct.unpack_from("<HHH", data, 46)
        header_format = "<IIIIIIIIII"
    else:
        raise ValueError(f"Unsupported ELF class: {elf_class}")

    headers = [
        struct.unpack_from(header_format, data, shoff + index * shentsize)
        for index in range(shnum)
    ]
    names_offset, names_size = headers[shstrndx][4], headers[shstrndx][5]
    names = data[names_offset : names_offset + names_size]

    result: dict[str, tuple[int, int, int]] = {}
    for header in headers:
        name_start = header[0]
        name_end = names.find(0, name_start)
        name = bytes(names[name_start:name_end]).decode("ascii")
        result[name] = (header[4], header[5], header[9])
    return result


def symbol_names(
    data: bytearray,
    dynsym: tuple[int, int, int],
    dynstr: tuple[int, int, int],
) -> list[bytes]:
    sym_offset, sym_size, sym_entry_size = dynsym
    str_offset, str_size, _ = dynstr
    strings = data[str_offset : str_offset + str_size]
    names = []
    for offset in range(sym_offset, sym_offset + sym_size, sym_entry_size):
        name_start = struct.unpack_from("<I", data, offset)[0]
        name_end = strings.find(0, name_start)
        names.append(bytes(strings[name_start:name_end]))
    return names


def rebuild_sysv_hash(
    data: bytearray,
    section: tuple[int, int, int],
    names: list[bytes],
) -> None:
    offset, size, _ = section
    bucket_count, chain_count = struct.unpack_from("<II", data, offset)
    if chain_count != len(names):
        raise ValueError("SysV hash symbol count does not match .dynsym")

    buckets = [0] * bucket_count
    chains = [0] * chain_count
    for symbol_index in range(1, chain_count):
        bucket = elf_hash(names[symbol_index]) % bucket_count
        if buckets[bucket] == 0:
            buckets[bucket] = symbol_index
        else:
            current = buckets[bucket]
            while chains[current] != 0:
                current = chains[current]
            chains[current] = symbol_index

    values = [bucket_count, chain_count, *buckets, *chains]
    encoded = struct.pack(f"<{len(values)}I", *values)
    if len(encoded) > size:
        raise ValueError("Rebuilt SysV hash exceeds its ELF section")
    data[offset : offset + len(encoded)] = encoded


def rebuild_gnu_hash(
    data: bytearray,
    section: tuple[int, int, int],
    names: list[bytes],
    elf_class: int,
) -> None:
    offset, size, _ = section
    _, symbol_offset, bloom_size, bloom_shift = struct.unpack_from("<IIII", data, offset)
    word_bits = 64 if elf_class == 2 else 32
    word_format = "Q" if elf_class == 2 else "I"
    word_size = word_bits // 8

    # One bucket makes every symbol from symbol_offset onward a single valid
    # contiguous chain. This avoids reordering .dynsym while fully rebuilding
    # lookup data for the renamed JNI exports.
    bloom = [0] * bloom_size
    hashes = [gnu_hash(name) for name in names[symbol_offset:]]
    for value in hashes:
        word = (value // word_bits) % bloom_size
        bloom[word] |= (1 << (value % word_bits)) | (
            1 << ((value >> bloom_shift) % word_bits)
        )

    output = bytearray(struct.pack("<IIII", 1, symbol_offset, bloom_size, bloom_shift))
    output.extend(struct.pack(f"<{bloom_size}{word_format}", *bloom))
    output.extend(struct.pack("<I", symbol_offset if hashes else 0))
    for index, value in enumerate(hashes):
        output.extend(struct.pack("<I", (value & 0xFFFFFFFE) | (index == len(hashes) - 1)))

    if len(output) > size:
        raise ValueError("Rebuilt GNU hash exceeds its ELF section")
    data[offset : offset + size] = output + bytes(size - len(output))


def patch(path: Path, old_path: bytes, new_path: bytes) -> None:
    if len(old_path) != len(new_path):
        raise ValueError("JNI package paths must have equal byte length")

    data = bytearray(path.read_bytes())
    sections = section_table(data)
    required = (".dynsym", ".dynstr", ".hash", ".gnu.hash")
    missing = [name for name in required if name not in sections]
    if missing:
        raise ValueError(f"Missing ELF sections: {', '.join(missing)}")

    old_symbol = old_path.replace(b"/", b"_")
    new_symbol = new_path.replace(b"/", b"_")
    replacements = data.count(old_path) + data.count(old_symbol)
    if replacements == 0:
        raise ValueError(f"No references to {old_path.decode()} found")

    data[:] = data.replace(old_path, new_path).replace(old_symbol, new_symbol)
    names = symbol_names(data, sections[".dynsym"], sections[".dynstr"])
    rebuild_sysv_hash(data, sections[".hash"], names)
    rebuild_gnu_hash(data, sections[".gnu.hash"], names, data[4])
    path.write_bytes(data)
    print(f"{path}: renamed {replacements} references and rebuilt ELF hashes")


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("library", type=Path)
    parser.add_argument("old_package", help="JNI path, for example com/artifex/solib")
    parser.add_argument("new_package", help="equal-length replacement JNI path")
    args = parser.parse_args()
    patch(args.library, args.old_package.encode("ascii"), args.new_package.encode("ascii"))


if __name__ == "__main__":
    main()
