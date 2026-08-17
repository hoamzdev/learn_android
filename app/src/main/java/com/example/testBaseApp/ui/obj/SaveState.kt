package com.example.testBaseApp.ui.obj

sealed class SaveState<out T>{
    data class Success<T>(val data : T) : SaveState<T>()
    data class Error(val message : String) : SaveState<Nothing>()
    object Loading : SaveState<Nothing>()
}