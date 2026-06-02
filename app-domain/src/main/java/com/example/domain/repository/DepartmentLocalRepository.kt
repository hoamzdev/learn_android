package com.example.domain.repository

import com.example.domain.entities.DepartmentEntity

interface DepartmentLocalRepository {
    suspend fun insertDepartment(departmentEntity: DepartmentEntity)
    suspend fun getAllDepartments(): List<DepartmentEntity>
    suspend fun updateDepartment(departmentEntity: DepartmentEntity)
    suspend fun deleteAllDepartment()
}