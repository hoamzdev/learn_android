package com.example.domain.usecase

import com.example.domain.entities.DepartmentEntity
import com.example.domain.repository.DepartmentLocalRepository

class DepartmentLocalUseCase(private val repository: DepartmentLocalRepository) {
    suspend fun getAllDepartment(): List<DepartmentEntity> =
        repository.getAllDepartments()

    suspend fun insertDepartments(departments: DepartmentEntity) =
        repository.insertDepartment(departments)

    suspend fun deleteAllDepartments() =
        repository.deleteAllDepartment()

    suspend fun updateDepartment(department: DepartmentEntity) =
        repository.updateDepartment(department)
}