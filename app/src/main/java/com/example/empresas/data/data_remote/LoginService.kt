package com.example.empresas.data.data_remote

import com.example.empresas.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface LoginService {

    @POST("users/auth/sign_in")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<Unit>

    companion object {
        fun newInstance(): LoginService = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL + BuildConfig.VERSION_API)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginService::class.java)

        private fun getClient(): OkHttpClient = OkHttpClient().newBuilder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()

    }
}