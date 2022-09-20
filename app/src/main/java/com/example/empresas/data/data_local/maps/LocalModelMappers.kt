package com.example.empresas.data.data_local.maps

import com.example.empresas.data.data_local.Constants
import com.example.empresas.data.data_local.Constants.HEADER_UID
import com.example.empresas.data.data_local.Constants.HEADER_CLIENT
import com.example.empresas.data.data_local.Constants.KEY_ACCESS_TOKEN
import com.example.empresas.data.data_local.models.HeadersLocal
import okhttp3.Headers

object LocalModelMappers {

    fun Headers.toLocalModel() =
        HeadersLocal(
            uid = this[HEADER_UID] ?: "",
            client = this[HEADER_CLIENT] ?: "",
            token = this[KEY_ACCESS_TOKEN] ?: ""
        )

    fun HeadersLocal.fromLocalModel() =
        Headers.Builder()
            .add(Constants.HEADER_ACCESS_TOKEN, token)
            .add(HEADER_CLIENT, client)
            .add(HEADER_UID, uid)
            .build()
}