package com.hwa.excelllll.excel

enum class ExcelFileType {
    XLS,
    XLSX,
    UNKNOWN;

    companion object {
        fun fromPath(path: String): ExcelFileType {
            return when (path.substringAfterLast('.', "").lowercase()) {
                "xls" -> XLS
                "xlsx" -> XLSX
                else -> UNKNOWN
            }
        }
    }
}
