package com.hwa.excelllll

interface SOSecureFS {
    class FileAttributes {
        @JvmField var isDirectory = false
        @JvmField var isHidden = false
        @JvmField var isSystem = false
        @JvmField var isWriteable = false
        @JvmField var lastModified = 0L
        @JvmField var length = 0L
    }

    fun closeFile(handle: Any): Boolean
    fun copyFile(source: String, destination: String): Boolean
    fun createDirectory(path: String): Boolean
    fun createFile(path: String): Boolean
    fun deleteFile(path: String): Boolean
    fun fileExists(path: String): Boolean
    fun getFileAttributes(path: String): FileAttributes?
    fun getFileHandleForReading(path: String): Any?
    fun getFileHandleForUpdating(path: String): Any?
    fun getFileHandleForWriting(path: String): Any?
    fun getFileLength(handle: Any): Long
    fun getFileOffset(handle: Any): Long
    fun getSecurePath(): String?
    fun getSecurePrefix(): String?
    fun getTempPath(): String?
    fun isSecurePath(path: String): Boolean
    fun readFromFile(handle: Any, buffer: ByteArray): Int
    fun recursivelyRemoveDirectory(path: String): Boolean
    fun renameFile(source: String, destination: String): Boolean
    fun seekToFileOffset(handle: Any, offset: Long): Boolean
    fun setFileLength(handle: Any, length: Long): Boolean
    fun syncFile(handle: Any): Boolean
    fun writeToFile(handle: Any, buffer: ByteArray): Int
}
