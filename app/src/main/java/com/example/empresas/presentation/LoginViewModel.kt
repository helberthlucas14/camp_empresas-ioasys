package com.example.empresas.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empresas.R
import com.example.empresas.data.data_remote.ResultWrapper
import com.example.empresas.data.repositories.LoginRepository
import com.example.empresas.extensions.viewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository
) : ViewModel() {

    private val _heardersLiveData by viewState<Unit>()
    val headerLiveData: LiveData<ViewState<Unit>> = _heardersLiveData

    private val _emailFieldError = MutableLiveData<Int?>()
    val emailFieldError: LiveData<Int?> = _emailFieldError

    private val _passwordFieldError = MutableLiveData<Int?>()
    val passwordFieldError: LiveData<Int?> = _passwordFieldError

    private val _btnLoginState = MutableLiveData<Boolean>()
    val btnLoginState: LiveData<Boolean> = _btnLoginState

    private var isValidLogin = true;


    fun clearFieldPasswordErrors() {
        _passwordFieldError.value = null
        setBtnLoginState(true)
    }

    fun login(email: String, password: String) {
        isValidLogin = true

        viewModelScope.launch(Dispatchers.Main) {

            _emailFieldError.value = getErrorStringIfEmpty(email)
            _passwordFieldError.value = getErrorStringIfEmpty(password)

            if (isValidLogin) {
                setLoading(true)
                handleLogin(repository.login(email, password))
            }
            setBtnLoginState(isValidLogin)
        }
    }

    private fun handleLogin(response: ResultWrapper<Unit>) {
        when (response) {
            is ResultWrapper.Success -> _heardersLiveData.value = ViewState.success(Unit)
            is ResultWrapper.Failure -> _heardersLiveData.value = ViewState.error(response.error)
        }
        setLoading(false)
    }

    private fun getErrorStringIfEmpty(value: String): Int? {
        return if (value.isEmpty()) {
            isValidLogin = false
            R.string.login_field_error
        } else null
    }

    private fun setLoading(state: Boolean) {
        _heardersLiveData.value = ViewState.loading(state)
    }

    private fun setBtnLoginState(value: Boolean) {
        _btnLoginState.value = value
    }
}