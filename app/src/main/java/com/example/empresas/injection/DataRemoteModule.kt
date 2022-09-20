package com.example.empresas.injection

import com.example.empresas.data.data_remote.CompanyService
import com.example.empresas.data.data_remote.LoginService
import org.koin.dsl.module

val dataRemoteModule = module {

    single<LoginService> { LoginService.newInstance() }
    single<CompanyService> { CompanyService.newInstance() }
}