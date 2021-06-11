package com.mediclab.smartreports.data.sign

import com.mediclab.smartreports.utils.Memory
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
    val rol: Int,
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
    val phone: Int,
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

data class OriginalReport(
    val id: Int,
    val fecha: String,
    val fkidMedico: Int,
    val fkidPatient: Int,
    val namedoc: String,
    val lastnamedoc: String,
    val status: Boolean
)


data class ReportsDetail(
    val id: Int,
    val fecha: String,
    val fkidMedico: Int,
    val fkidPatient: Int,
    val detail: String,
    val status: Boolean,
    val namedoc: String,
    val lastnamedoc: String,
    val detailgenerate: String,
    val idreportgenerate: Int,
    val statusgenerate: Boolean
    )
//detalle reportes con reporte generado

interface ApiService {


    @POST("sign")
    fun signIn(@Body signInParams: SignInParams): Call<SignInResponse>

    @POST("users")
    fun createPatient(@Body CreatePatientParams: CreatePatientParams): Call<CreatePatientResponse>

    @GET("reportByPatient/{id}")
    fun getReportByPatient(@Header("x-access-token") token: String = Memory.token, @Path("id") patientId: String): Call<List<OriginalReport>>

    @GET("getReportByPatientOne/{id}")
    fun getReportByPatientOne(@Header("x-access-token") token: String = Memory.token, @Path("id") patientId: String): Call<OriginalReport>
    //detalle reporte por id
    @GET("getReportById/{id}")
    fun getReportById(@Header("x-access-token") token: String = Memory.token, @Path("id") reportId: String): Call<ReportsDetail>

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