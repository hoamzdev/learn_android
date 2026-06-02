package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.DepartmentData


//viet truy van room db
@Dao
interface DepartmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: DepartmentData)

    @Update
    suspend fun update(data: DepartmentData)

    @Query("SELECT * FROM department")
    suspend fun getAllData(): MutableList<DepartmentData>

    @Query("DELETE FROM department")
    suspend fun clearDatabase()
}