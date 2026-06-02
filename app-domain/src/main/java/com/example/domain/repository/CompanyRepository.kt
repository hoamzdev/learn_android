package com.example.domain.repository

import com.example.domain.entities.DepartmentEntity
import com.example.domain.entities.EmployeeEntity
import com.example.domain.utils.ApiResult


//dinh nghia cac cac fun nghiep vu o day (dang abstract fun)
interface CompanyRepository {

    suspend fun getDepartment(): ApiResult<List<DepartmentEntity>>

    suspend fun getEmployee(departmentCode: String): ApiResult<List<EmployeeEntity>>
}
