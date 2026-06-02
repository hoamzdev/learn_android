package com.example.data.repository

import com.example.data.base.BaseRepository
import com.example.data.local.dao.DepartmentDao
import com.example.data.model.toData
import com.example.data.model.toDomain
import com.example.domain.entities.DepartmentEntity
import com.example.domain.repository.DepartmentLocalRepository

class DepartmentRepositoryImpl(private val dao: DepartmentDao) : BaseRepository(),
    DepartmentLocalRepository {
    override suspend fun insertDepartment(departmentEntity: DepartmentEntity) {
        return dao.insert(departmentEntity.toData())
    }

    override suspend fun getAllDepartments(): List<DepartmentEntity> {
        return dao.getAllData().map { it.toDomain() }
    }

    override suspend fun updateDepartment(departmentEntity: DepartmentEntity) {
        return dao.update(departmentEntity.toData())
    }

    override suspend fun deleteAllDepartment() {
        return dao.clearDatabase()
    }
}