package com.example.empresas.data.data_local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.example.empresas.data.data_local.daos.CompanyDao
import com.example.empresas.data.data_local.daos.HeadersDao
import com.example.empresas.data.data_local.models.CompanyLocalModel
import com.example.empresas.data.data_local.models.HeadersLocal
import retrofit2.Converter

@Database(
    entities = [HeadersLocal::class, CompanyLocalModel::class],
    version = 1,
    exportSchema = false
)
abstract class EmpresasDatabase : RoomDatabase() {

    abstract fun provideHeadersDao(): HeadersDao
    abstract fun provideCompanyDao(): CompanyDao
}