package com.example.empresas.extensions

import com.example.empresas.BuildConfig.BASE_URL
import com.example.empresas.data.model.Company
import com.example.empresas.data.model.CompanyType
import com.example.empresas.data.data_remote.CompanyResponse
import com.example.empresas.data.data_remote.CompanyTypeResponse

fun CompanyResponse.toModel(): Company {
    return Company(
        id = id,
        companyName = enterpriseName,
        pathImage = GetPathUrl(photo),
        description = description,
        country = country ?: "",
        companyType = enterpriseType.toModel()
    )
}

private fun GetPathUrl(photo: String): String {
    if (!"".equals(photo)) {
        return BASE_URL + photo.substring(1)
    }
    return ""
}

fun CompanyTypeResponse.toModel(): CompanyType {
    return CompanyType(
        id = id,
        companyTypeName = enterpriseTypeName
    )
}