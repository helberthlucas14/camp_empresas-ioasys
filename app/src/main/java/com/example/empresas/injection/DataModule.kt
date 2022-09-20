package com.example.empresas.injection

import com.example.empresas.data.repositories.CompanyRepository
import com.example.empresas.data.repositories.CompanyRepositoryImpl
import com.example.empresas.data.repositories.LoginRepository
import com.example.empresas.data.repositories.LoginRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single<LoginRepository> { LoginRepositoryImpl(get(), get(), androidContext()) }
    single<CompanyRepository> { CompanyRepositoryImpl(get(), get(), androidContext()) }

}