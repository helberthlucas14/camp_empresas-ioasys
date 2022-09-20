package com.example.empresas.data.data_local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "companies_table")
data class CompanyLocalModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)