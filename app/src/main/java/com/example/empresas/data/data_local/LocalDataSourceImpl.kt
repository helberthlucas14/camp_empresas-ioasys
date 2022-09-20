package com.example.empresas.data.data_local

import android.content.SharedPreferences
import com.example.empresas.data.data_local.Constants.HEADER_ACCESS_TOKEN
import com.example.empresas.data.data_local.Constants.HEADER_CLIENT
import com.example.empresas.data.data_local.Constants.HEADER_UID
import com.example.empresas.data.data_local.Constants.KEY_ACCESS_TOKEN
import com.example.empresas.data.data_local.Constants.KEY_CLIENT
import com.example.empresas.data.data_local.Constants.KEY_UID
import com.example.empresas.data.data_local.maps.LocalModelMappers.fromLocalModel
import com.example.empresas.data.data_local.maps.LocalModelMappers.toLocalModel
import com.example.empresas.data.data_local.daos.CompanyDao
import com.example.empresas.data.data_local.daos.HeadersDao
import okhttp3.Headers

class LocalDataSourceImpl(
    private val sharedPreferences: SharedPreferences,
    private val headersDAO: HeadersDao,
) : LocalDataSource {

    override suspend fun saveToLocalDatabase(headers: Headers) {
        headersDAO.saveHeaders(headers.toLocalModel())
    }

    override suspend fun getFromLocalDatabase() {
        headersDAO.getHeaders().fromLocalModel()
    }

    override fun saveHeadersToPreferences(headers: Headers) {
        sharedPreferences.apply {
            val editor = edit()
            editor.putString(KEY_UID, headers[HEADER_UID])
            editor.putString(KEY_CLIENT, headers[HEADER_CLIENT])
            editor.putString(KEY_ACCESS_TOKEN, headers[HEADER_ACCESS_TOKEN])
            editor.apply()
        }
    }

    override fun getHeadersFromPreferences() = sharedPreferences.run {
        val uid = getString(KEY_UID, "") ?: ""
        val client = getString(KEY_CLIENT, "") ?: ""
        val token = getString(KEY_ACCESS_TOKEN, "") ?: ""
        Headers.Builder()
            .add(HEADER_ACCESS_TOKEN, token)
            .add(HEADER_CLIENT, client)
            .add(HEADER_UID, uid)
            .build()
    }
}