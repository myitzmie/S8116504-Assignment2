package com.s8116504.assignment2.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s8116504.assignment2.data.model.LoginRequest
import com.s8116504.assignment2.data.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = repository.login(username, password)
                _loginState.value = LoginState.Success(response.keypass)
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Login failed")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val keypass: String) : LoginState()
    data class Error(val message: String) : LoginState()
}