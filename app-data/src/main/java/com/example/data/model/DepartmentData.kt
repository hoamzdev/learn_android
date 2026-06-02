package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entities.DepartmentEntity
import com.google.gson.annotations.SerializedName

//table in room
@Entity(tableName = "department")
data class DepartmentData(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("code")
    val code: String?
)

//mapper
fun DepartmentData.toDomain() = DepartmentEntity(
    id = id,
    name = name,
    image = image,
    code = code
)

//mapper
fun DepartmentEntity.toData() = DepartmentData(
    id = id,
    name = name,
    image = image,
    code = code
)

