package com.example.data.api

import com.example.data.model.DepartmentData
import com.example.data.model.EmployeeData
import retrofit2.http.GET


//cac method cho viec tuong tac vs be
interface CompanyApi {

    @GET("Department")
    suspend fun getDepartment(): List<DepartmentData>

    @GET("employee")
    suspend fun getEmployee(): List<EmployeeData>
}
