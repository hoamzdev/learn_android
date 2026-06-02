package com.example.data.repository

import com.example.data.api.CompanyApi
import com.example.data.base.BaseRepository
import com.example.data.model.toDomain
import com.example.domain.entities.DepartmentEntity
import com.example.domain.entities.EmployeeEntity
import com.example.domain.repository.CompanyRepository
import com.example.domain.utils.ApiResult
import javax.inject.Inject

//repo in data (impl repository declared in domain)
class CompanyRepositoryImpl @Inject constructor(private val api: CompanyApi) : BaseRepository(), CompanyRepository {

    //chi can goi ra thoi, cai retrofit tu lam ho
    override suspend fun getDepartment(): ApiResult<List<DepartmentEntity>> = safeApiCall {
        api.getDepartment().map { it.toDomain() }
    }

    override suspend fun getEmployee(departmentCode: String): ApiResult<List<EmployeeEntity>> =
        safeApiCall {
            api.getEmployee().filter { it.departmentCode == departmentCode }.map { it.toDomain() }
        }
}
