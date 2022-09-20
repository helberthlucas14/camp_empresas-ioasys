package com.example.empresas.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empresas.data.model.Company
import com.example.empresas.data.data_remote.ResultWrapper
import com.example.empresas.data.repositories.CompanyRepository
import com.example.empresas.extensions.viewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val respository: CompanyRepository
) : ViewModel() {

    private val _companyListLiveData by viewState<List<Company>>()
    val companyListLiveData: LiveData<ViewState<List<Company>>> = _companyListLiveData

    fun getCompanies() {
        setLoading(true)
        viewModelScope.launch(Dispatchers.Main) {
            val response = respository.getCompanies()
            handleResponse(response)
            setLoading(false)
        }
    }

    private fun handleResponse(response: ResultWrapper<List<Company>>) {
        when (response) {
            is ResultWrapper.Success -> {
                _companyListLiveData.value = ViewState.success(response.data)
            }
            is ResultWrapper.Failure -> _companyListLiveData.value = ViewState.error(response.error)
        }
    }

    private fun setLoading(state: Boolean) {
        _companyListLiveData.value = ViewState.loading(state)
    }
}