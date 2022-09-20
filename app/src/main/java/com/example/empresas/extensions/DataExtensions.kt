package com.example.empresas.extensions

import android.accounts.NetworkErrorException
import android.content.Context
import android.provider.Settings.System.getString
import com.example.empresas.R
import com.example.empresas.data.data_remote.ResultWrapper
import retrofit2.Response

suspend fun <T> wrapResponse(
    context: Context,
    call: suspend () -> Response<T>
): ResultWrapper<T> {
    return if (context.isConnected) {
        try {
            val response = call()
            ResultWrapper.Success(response.body()!!)
        } catch (error: Throwable) {
            ResultWrapper.Failure(error)
        }
    } else {
        return ResultWrapper.Failure(NetworkErrorException("Sem acesso a internet!"))
    }
}

