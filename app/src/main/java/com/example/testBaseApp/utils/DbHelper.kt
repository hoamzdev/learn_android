package com.example.testBaseApp.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.testBaseApp.R
import com.example.testBaseApp.ui.obj.OfficeModel
import java.io.File
import kotlin.apply

class DbHelper private constructor(context: Context) :
    SQLiteOpenHelper(context.applicationContext, DATABASE_NAME, null, DATABASE_VERSION) {
    private val appContext: Context = context.applicationContext

    companion object {
        private const val DATABASE_NAME = "pdf_history.db"
        private const val DATABASE_VERSION = 2
        private var instance: DbHelper? = null

        @Synchronized
        fun getInstance(context: Context): DbHelper {
            return instance ?: DbHelper(context).also { instance = it }
        }
    }

    /** * Original: m21928d() * * Mở database. */
    fun openDatabase(): SQLiteDatabase {
        return writableDatabase
    }

    /** * Original: m21927b() * * Đóng database. */
    fun closeDatabase() {
        close()
    }

    /** * Thêm file vào danh sách starred. * * Original: m21926a() */
    fun addToStarred(path: String) {
        val database = openDatabase()
        val values = ContentValues().apply {
            put(
                "path",
                path
            )
        }
        database.replace("stared_pdfs", null, values)
    }

    /** * Lấy danh sách file đã mở gần đây. * * Original: m21929e() */
    @SuppressLint("Recycle")
    fun getHistoryFiles(): ArrayList<OfficeModel> {
        val files = ArrayList<OfficeModel>()
        val database = openDatabase()
        val cursor = database.rawQuery(
            "SELECT * FROM history_pdfs ORDER BY last_accessed_at DESC",
            null
        )
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val path = it.getString(it.getColumnIndexOrThrow("path"))
                    val file = File(path)
                    if (file.exists()) {
                        val officeModel = createOfficeModel(
                            file,
                            starred = isStarred(path)
                        )
                        files.add(officeModel)
                    } else {
                        removeFromHistory(path)
                    }
                } while (it.moveToNext())
            }
        }
        return files
    }

    /** * Lấy danh sách file đã starred. * * Original: m21930h() */
    @SuppressLint("Recycle")
    fun getStarredFiles(): ArrayList<OfficeModel> {
        val files = ArrayList<OfficeModel>()
        val database = openDatabase()
        val cursor = database.rawQuery(
            "SELECT * FROM stared_pdfs ORDER BY created_at DESC",
            null
        )
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val path = it.getString(it.getColumnIndexOrThrow("path"))
                    val file = File(path)
                    if (file.exists()) {
                        val officeModel =
                            createOfficeModel(file, starred = true)
                        files.add(officeModel)
                    } else {
                        removeFromStarred(path)
                    }
                } while (it.moveToNext())
            }
        }
        return files
    }

    /** * Kiểm tra file có nằm trong history hay không. * * Original: m21931i() */
    fun isInHistory(path: String): Boolean {
        val database = openDatabase()
        val cursor = database.query(
            "history_pdfs",
            arrayOf("path"),
            "path = ?",
            arrayOf(path),
            null,
            null,
            null
        )
        return cursor.use { it.moveToFirst() }
    }

    /** * Kiểm tra file có được starred hay không. * * Original: m21932j() */
    fun isStarred(path: String): Boolean {
        val database = openDatabase()
        val cursor = database.query(
            "stared_pdfs",
            arrayOf("path"),
            "path = ?",
            arrayOf(path),
            null,
            null,
            null
        )
        return cursor.use { it.moveToFirst() }
    }

    /** * Xóa file khỏi history. * * Original: m21933k() */
    fun removeFromHistory(path: String) {
        openDatabase().delete("history_pdfs", "path = ?", arrayOf(path))
    }

    /** * Xóa file khỏi starred. * * Original: m21934l() */
    fun removeFromStarred(path: String) {
        openDatabase().delete("stared_pdfs", "path = ?", arrayOf(path))
    }

    /** * Đổi path của file trong history. * * Original: m21935p() */
    fun updateHistoryPath(oldPath: String, newPath: String) {
        try {
            val values = ContentValues().apply {
                put(
                    "path",
                    newPath
                )
            }
            openDatabase().update("history_pdfs", values, "path = ?", arrayOf(oldPath))
        } catch (e: Exception) {
            Toast.makeText(appContext, "failed", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    /** * Đổi path của file trong starred. * * Original: m21936t() */
    fun updateStarredPath(oldPath: String, newPath: String) {
        try {
            val values = ContentValues().apply { put("path", newPath) }
            openDatabase().update(
                "stared_pdfs",
                values,
                "path = ?",
                arrayOf(oldPath)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /** * Tạo OfficeModel từ File. */
    private fun createOfficeModel(file: File, starred: Boolean): OfficeModel {
        return OfficeModel(
            absolutePath = file.absolutePath,
            fileUri = file.absolutePath,
            length = file.length(),
            lastModified = file.lastModified(),
            nameFile = file.name,
            isDirectory = file.isDirectory,
            isStarred = starred
        )
    }

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(""" CREATE TABLE IF NOT EXISTS history_pdfs ( _id INTEGER PRIMARY KEY AUTOINCREMENT, path TEXT UNIQUE, last_accessed_at DATETIME DEFAULT (DATETIME('now','localtime')) ) """.trimIndent())
        database.execSQL(""" CREATE TABLE IF NOT EXISTS stared_pdfs ( _id INTEGER PRIMARY KEY AUTOINCREMENT, path TEXT UNIQUE, created_at DATETIME DEFAULT (DATETIME('now','localtime')) ) """.trimIndent())
        database.execSQL(""" CREATE TABLE IF NOT EXISTS last_opened_page ( _id INTEGER PRIMARY KEY AUTOINCREMENT, path TEXT UNIQUE, page_number INTEGER ) """.trimIndent())
        database.execSQL(""" CREATE TABLE IF NOT EXISTS bookmarks ( _id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, path TEXT, page_number INTEGER UNIQUE, created_at DATETIME DEFAULT (DATETIME('now','localtime')) ) """.trimIndent())
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion == 1) {
            database.execSQL(""" CREATE TABLE IF NOT EXISTS last_opened_page ( _id INTEGER PRIMARY KEY AUTOINCREMENT, path TEXT UNIQUE, page_number INTEGER ) """.trimIndent())
            database.execSQL(""" CREATE TABLE IF NOT EXISTS bookmarks ( _id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, path TEXT, page_number INTEGER UNIQUE, created_at DATETIME DEFAULT (DATETIME('now','localtime')) ) """.trimIndent())
        }
    }

}