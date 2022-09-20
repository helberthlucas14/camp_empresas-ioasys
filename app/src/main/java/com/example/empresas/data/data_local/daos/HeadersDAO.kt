package com.example.empresas.data.data_local.daos

import androidx.room.*
import com.example.empresas.data.data_local.models.HeadersLocal

@Dao
interface HeadersDao {

    @Insert
    suspend fun saveHeaders(headers: HeadersLocal)

    @Query("SELECT * FROM headers_table;")
    suspend fun getHeaders(): HeadersLocal

}