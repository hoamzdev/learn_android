#!/usr/bin/env python3
import struct
import sys

def align_elf(filename):
    with open(filename, 'r+b') as f:
        # Read ELF Header
        magic = f.read(4)
        if magic != b'\x7fELF':
            print(f"Error: {filename} is not a valid ELF file.")
            return False

        ei_class = ord(f.read(1))
        if ei_class != 2: # 2 = 64-bit
            print(f"Error: {filename} is not a 64-bit ELF file.")
            return False

        # Read header offsets for 64-bit ELF
        f.seek(32)
        e_phoff = struct.unpack('<Q', f.read(8))[0]
        f.seek(54)
        e_phentsize = struct.unpack('<H', f.read(2))[0]
        e_phnum = struct.unpack('<H', f.read(2))[0]

        updated = False
        for i in range(e_phnum):
            ph_offset = e_phoff + (i * e_phentsize)
            f.seek(ph_offset)
            p_type = struct.unpack('<I', f.read(4))[0]

            # PT_LOAD = 1
            if p_type == 1:
                align_offset = ph_offset + 48
                f.seek(align_offset)
                p_align = struct.unpack('<Q', f.read(8))[0]

                if p_align < 16384:
                    f.seek(align_offset)
                    f.write(struct.pack('<Q', 16384))
                    print(f"Updated LOAD segment {i} alignment from {hex(p_align)} to 0x4000 (16KB)")
                    updated = True

        if updated:
            print(f"Successfully aligned {filename} to 16KB!")
        else:
            print(f"{filename} is already aligned to 16KB or requires no changes.")
        return True

if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Usage: python fix_16kb_elf.py <path_to_so_file>")
        sys.exit(1)
    align_elf(sys.argv[1])