package com.example.empresas.data.repositories

import android.content.Context
import com.example.empresas.data.data_local.Constants
import com.example.empresas.data.data_local.LocalDataSource
import com.example.empresas.data.data_remote.CompanyService
import com.example.empresas.data.data_remote.ResultWrapper
import com.example.empresas.data.model.Company
import com.example.empresas.extensions.toModel
import com.example.empresas.extensions.wrapResponse

class CompanyRepositoryImpl(
    private val service: CompanyService,
    private val localDataSource: LocalDataSource,
    private val context: Context
) : CompanyRepository {

    override suspend fun getCompanies(): ResultWrapper<List<Company>> =
        when (
            val result = callEnterprisesEndpoint()) {
            is ResultWrapper.Failure -> ResultWrapper.Failure(result.error!!)
            is ResultWrapper.Success -> ResultWrapper.Success(result.data?.companies?.map {
                it.toModel()
            } ?: listOf())
        }


    private suspend fun callEnterprisesEndpoint() =
        wrapResponse(context) {
            service.getCompanies(
                accessToken = localDataSource
                    .getHeadersFromPreferences()[Constants.HEADER_ACCESS_TOKEN] ?: "",

                client = localDataSource
                    .getHeadersFromPreferences()[Constants.HEADER_CLIENT] ?: "",

                uid = localDataSource
                    .getHeadersFromPreferences()[Constants.HEADER_UID] ?: "",
            )
        }

}