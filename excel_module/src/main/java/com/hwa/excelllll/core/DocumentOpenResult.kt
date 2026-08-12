package com.hwa.excelllll.core

import com.hwa.excelllll.SODoc

sealed interface DocumentOpenResult {
    data class Success(val document: SODoc) : DocumentOpenResult
    data class Error(val code: Int, val message: String? = null) : DocumentOpenResult
}
