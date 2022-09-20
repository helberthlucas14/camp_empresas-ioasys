package com.example.empresas

import android.app.Application
import com.example.empresas.injection.dataLocalModule
import com.example.empresas.injection.dataModule
import com.example.empresas.injection.dataRemoteModule
import com.example.empresas.injection.presetationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EmpresasApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpKoin()
    }

    private fun setUpKoin() {
        startKoin {
            androidContext(this@EmpresasApplication)
            modules(
                listOf(
                    presetationModule,
                    dataModule,
                    dataLocalModule,
                    dataRemoteModule
                )
            )
        }
    }
}