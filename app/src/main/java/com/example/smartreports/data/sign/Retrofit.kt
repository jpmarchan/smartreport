package com.example.smartreports.data.sign

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

private const val URL = "http://api-smartreport.codeksora.com/"

data class SignInParams(
    val email: String,
    val password: String
)

data class SignInResponse(
    val rol: String,
    val response: Boolean,
    val token: String,
    val status: Boolean,
    val userId: String,
    val userName: String
)

interface ApiService {

    @POST("sign")
    fun signIn(@Body signInParams: SignInParams): Call<SignInResponse>
}

object Api {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(URL)
        .build()

    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}