package com.example.empresas.data.data_local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "headers_table")
data class HeadersLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uid: String,
    val client: String,
    val token: String
)
