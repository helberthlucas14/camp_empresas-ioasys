package com.example.empresas.data.repositories

import com.example.empresas.data.data_remote.ResultWrapper

interface LoginRepository {
    suspend fun login(email: String, password: String): ResultWrapper<Unit>
}