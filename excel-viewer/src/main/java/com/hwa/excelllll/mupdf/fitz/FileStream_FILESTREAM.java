package com.hwa.excelllll.mupdf.fitz;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/* JADX INFO: loaded from: classes.dex */
public class FileStream implements SeekableInputStream, SeekableOutputStream {
    protected RandomAccessFile file;

    public FileStream(File file, String str) throws IOException {
        this.file = new RandomAccessFile(file, str);
    }

    public void close() throws IOException {
        this.file.close();
    }

    @Override // com.artifex.mupdf.fitz.SeekableStream
    public long position() throws IOException {
        return this.file.getFilePointer();
    }

    @Override // com.artifex.mupdf.fitz.SeekableInputStream
    public int read(byte[] bArr) throws IOException {
        return this.file.read(bArr);
    }

    @Override // com.artifex.mupdf.fitz.SeekableStream
    public long seek(long j, int i) throws IOException {
        RandomAccessFile randomAccessFile;
        long filePointer;
        if (i != 0) {
            if (i == 1) {
                randomAccessFile = this.file;
                filePointer = randomAccessFile.getFilePointer();
            } else if (i == 2) {
                randomAccessFile = this.file;
                filePointer = randomAccessFile.length();
            }
            randomAccessFile.seek(filePointer + j);
        } else {
            this.file.seek(j);
        }
        return this.file.getFilePointer();
    }

    @Override // com.artifex.mupdf.fitz.SeekableOutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.file.write(bArr, i, i2);
    }

    public FileStream(String str, String str2) throws IOException {
        this.file = new RandomAccessFile(str, str2);
    }
}
