package com.example.empresas.data.repositories

import com.example.empresas.data.data_remote.ResultWrapper
import com.example.empresas.data.model.Company

interface CompanyRepository {
    suspend fun getCompanies(): ResultWrapper<List<Company>>
}