package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.DepartmentDao
import com.example.data.model.DepartmentData

@Database(entities = [DepartmentData::class], version = 1, exportSchema = false)
abstract class DepartmentRoomDatabase : RoomDatabase() {
    abstract fun dao(): DepartmentDao
}
