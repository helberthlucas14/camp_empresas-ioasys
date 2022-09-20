package com.example.empresas.injection

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.empresas.data.data_local.Constants
import com.example.empresas.data.data_local.DatabaseConfiguration
import com.example.empresas.data.data_local.LocalDataSource
import com.example.empresas.data.data_local.LocalDataSourceImpl
import com.example.empresas.data.data_local.daos.CompanyDao
import com.example.empresas.data.data_local.daos.HeadersDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

var dataLocalModule = module {

    fun provideSharedPreferences(context: Context) =
        context.getSharedPreferences(
            Constants.SHARED_PREF_FILE,
            MODE_PRIVATE
        )

    single<LocalDataSource> { LocalDataSourceImpl(get(), get()) }

    single<SharedPreferences> { provideSharedPreferences(androidContext()) }

    single<CompanyDao> {
        DatabaseConfiguration.getDatabaseInstance(androidContext()).provideCompanyDao()
    }
    single<HeadersDao> {
        DatabaseConfiguration.getDatabaseInstance(androidContext()).provideHeadersDao()
    }
}