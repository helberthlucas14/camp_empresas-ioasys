package com.example.empresas.data.repositories

import android.content.Context
import com.example.empresas.data.data_local.LocalDataSource
import com.example.empresas.data.data_remote.LoginRequest
import com.example.empresas.data.data_remote.LoginService
import com.example.empresas.extensions.wrapResponse

class LoginRepositoryImpl(
    private val service: LoginService,
    private val localDataSource: LocalDataSource,
    private val context: Context
) : LoginRepository {

    override suspend fun login(email: String, password: String) =
        wrapResponse(context) {
            service.login(
                LoginRequest(
                    email = email,
                    password = password
                )
            ).apply {
                localDataSource.saveHeadersToPreferences(headers())
            }
        }

}