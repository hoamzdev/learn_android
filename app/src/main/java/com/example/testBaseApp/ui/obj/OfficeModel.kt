package com.example.testBaseApp.ui.obj

import java.io.Serializable


data class OfficeModel(
    var absolutePath: String? = null,
    var createAt: String? = null,
    var isDirectory: Boolean = false,
    var isStarred: Boolean = false,
    var lastModified: Long? = 0L,
    var length: Long? = 0L,
    var nameFile: String? = null,
    var fileUri: String? = null,
    var isChecked: Boolean = false
) : Comparable<OfficeModel>, Serializable {
    override fun compareTo(other: OfficeModel): Int {
        return this.nameFile?.compareTo(other.nameFile ?: "") ?: 0
    }
}