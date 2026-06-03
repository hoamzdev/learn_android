package com.example.samplecleanarchitecture.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.sharePres.TokenManager
import com.example.domain.entities.AuthRequest
import com.example.domain.entities.AuthResponse
import com.example.domain.usecase.GetCurrentUserUseCase
import com.example.domain.usecase.LoginUseCase
import com.example.domain.utils.onError
import com.example.domain.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author hwa..
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val tokenManager: TokenManager,
) : ViewModel() {

    private var _logoutAgain = MutableStateFlow<Boolean>(false)
    val logoutAgain = _logoutAgain.asStateFlow()

    //check expiration refresh - token
    init {
        viewModelScope.launch {
            tokenManager.forceLogout.collect {
                _logoutAgain.emit(true)
            }
        }
    }

    //login
    fun login(request: AuthRequest) {
        viewModelScope.launch {
            loginUseCase(request).onSuccess {
                Log.d("AUTH", it.toString())
            }.onError { error ->
                Log.e("AUTH", error.toString())
            }
        }
    }

    //getUser
    fun getCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase().onSuccess { response ->
                Log.e("AUTH", response.toString())
            }.onError {
                Log.e("AUTH", it.toString())
            }
        }
    }
}