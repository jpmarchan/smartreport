package com.mediclab.smartreports.data.sign

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

private const val URL = "http://api-smartreport.codeksora.com/"
//private const val URL = "http://23ad2963923d.ngrok.io"

data class SignInParams(
    val email: String,
    val password: String
)

data class SignInResponse(
    val rol: String,
    val response: Boolean,
    val token: String,
    val status: Boolean,
    val userId: Int,
    val userName: String
)

// create users
data class CreatePatientParams(
    val name: String,
    val lastname: String,
    val email: String,
    val password: String,
    val rol: Int,
    val status:  Boolean,
    val dni: Int,
    val sex: Boolean,
    val age: Int
)
data class CreatePatientResponse(
    val message: String = "",
    val status: Boolean
)

data class OriginalReports(
    val id: Int,
    val fecha: String,
    val fkidMedico: Int,
    val fkidPatient: Int,
    val namedoc: String,
    val status: Boolean
)

interface ApiService {

    @POST("sign")
    fun signIn(@Body signInParams: SignInParams): Call<SignInResponse>

    @POST("users")
    fun createPatient(@Body CreatePatientParams: CreatePatientParams): Call<CreatePatientResponse>

    @GET("reportByPatient/{id}")
    fun getReportByPatient(@Header("x-access-token") token: String, @Path("id") patientId: String): Call<List<OriginalReports>>
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