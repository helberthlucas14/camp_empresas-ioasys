package com.example.empresas.data.data_local.maps

import com.example.empresas.data.data_local.models.CompanyLocalModel
import com.example.empresas.data.data_remote.CompanyResponse
import com.example.empresas.data.model.Company

object CompanyModelMappers {

    fun CompanyResponse.toCompanyModel() =
        CompanyLocalModel(
            id = id
        )

    fun CompanyLocalModel.toModel() =
        Company(
            id = id
        )

}