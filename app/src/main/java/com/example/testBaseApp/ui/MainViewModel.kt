package com.example.testBaseApp.ui

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class ExcelFileItem(
    val id: Long,
    val name: String,
    val contentUri: Uri,
    val size: Long
)

@HiltViewModel
class MainViewModel @Inject constructor(
    @param:ApplicationContext
    private val context: Context
) : ViewModel() {

    private val _excelFiles = MutableLiveData<List<String>>()
    val excelFiles: LiveData<List<String>> = _excelFiles

}