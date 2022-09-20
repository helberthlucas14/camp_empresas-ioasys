package com.example.empresas.data.data_remote

import com.example.empresas.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import java.util.concurrent.TimeUnit

interface CompanyService {

    @GET("enterprises")
    suspend fun getCompanies(
        @Header("access-token") accessToken: String,
        @Header("client") client: String,
        @Header("uid") uid: String
    ): Response<GetCompaniesResponse>

    companion object {
        fun newInstance(): CompanyService = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL + BuildConfig.VERSION_API)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CompanyService::class.java)

        private fun getClient(): OkHttpClient = OkHttpClient().newBuilder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()

    }
}