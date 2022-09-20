package com.example.empresas.injection

import com.example.empresas.presentation.LoginViewModel
import com.example.empresas.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presetationModule = module {
    viewModel<LoginViewModel> { LoginViewModel(get()) }
    viewModel<MainViewModel> { MainViewModel(get()) }
}