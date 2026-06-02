package com.example.samplecleanarchitecture.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.utils.ApiResult
import com.example.samplecleanarchitecture.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

open class BaseViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var countLoading = 0

    protected fun <T> runAsync(
        request: suspend () -> ApiResult<T>,
        onSuccess: (T) -> Unit = {},
        onError: (ApiResult.Error) -> Unit = {},
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = request()) {
                is ApiResult.Success -> onSuccess(result.data)
                is ApiResult.Error -> onError(result)
            }
        }
    }

    protected fun <T> runAsyncWithLoading(
        request: suspend () -> ApiResult<T>,
        onSuccess: (T) -> Unit = {},
        onError: (ApiResult.Error) -> Unit = {},
    ) {
        showLoading()
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = request()) {
                is ApiResult.Success -> onSuccess(result.data)
                is ApiResult.Error -> onError(result)
            }
            hideLoading()
        }
    }

    protected fun <T> runAsyncUiState(
        state: MutableStateFlow<UiState<T>>,
        request: suspend () -> T,
        onSuccess: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {},
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = UiState.Loading
            try {
                val result = request()
                onSuccess(result)
            } catch (e: Exception) {
                state.value = UiState.Error(e)
                onError(e)
            }
        }
    }

    protected fun <T> runAsyncUiStateWithLoading(
        state: MutableStateFlow<UiState<T>>,
        request: suspend () -> T,
        onSuccess: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {},
    ) {
        showLoading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state.value = UiState.Loading
                try {
                    val result = request()
                    onSuccess(result)
                } catch (e: Exception) {
                    state.value = UiState.Error(e)
                    onError(e)
                }
                hideLoading()
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.d("tag111", "runAsyncUiStateWithLoading $ex")
            }
        }
    }

    private val mutex = Mutex()
    protected fun showLoading() {
        viewModelScope.launch {
            mutex.withLock {
                countLoading++
                _isLoading.postValue(countLoading > 0)
            }
        }
    }

    protected fun hideLoading() {
        viewModelScope.launch {
            mutex.withLock {
                countLoading--
                _isLoading.postValue(countLoading > 0)
            }
        }
    }
}
