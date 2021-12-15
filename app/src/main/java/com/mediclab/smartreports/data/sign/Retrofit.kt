package com.mediclab.smartreports.data.sign

import com.mediclab.smartreports.utils.Memory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

private const val URL = "http://ebab-190-234-106-97.ngrok.io"
//private const val URL = "http://ffe518e21eca.ngrok.io"

data class SignInParams(
    val email: String,
    val password: String
)

data class UpdateUser(
    val sex: Boolean,
    val age: Int,
    val departament: String,
    val id: Int,

    )
data class ResponseUpdateUser(
    val message: String,
    val status: Boolean,
    )



data class SignInResponse(
    val rol: Int,
    val response: Boolean,
    val token: String,
    val status: Boolean,
    val userId: Int,
    val userName: String,
    val age: Int,
    val departament: String
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

data class DietaPatient(
    val id: Int,
    val name: String,
    val description: String,
    val desayuno: String,
    val almuerzo: String,
    val cena: String,
    val ingredientesdesayuno: String,
    val ingredientesalmuerzo: String,
    val ingredientescena: String,
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

data class WatchReportResponse(
    val message: String,
    val reponseCode: Boolean,

)

data class SearchPatientParams(
    val dni: String
)

data class ListPatientResponse(
    val id: Int,
    val name: String,
    val lastname: String,
    val dni: Int,
    val email: String,
    val sex: Boolean,
    val age: Int,
    val status: Boolean,
    val aneminum: Int,
    val fkidmedic: Int
)
// Asignar paciente a medico

data class AsingPatientMedicParams(
    val idpatient: Int,
    val idmedic: Int
)

data class AsingPatientMedicResponse(
    val status: Boolean,
    val message: String
)


//crear reporte

data class CreateReportParams(
    val timestamp: String,
    val idmedic: Int,
    val idpatient: String ?,
    val detail: String
)

data class CreateReportResponse(
    val responseMessage: String,
    val reponseCode: Boolean,
    val idReporOriginal: Int
)

data class CreateReceta(
    val fecha: String,
    val iddieta: String ?,
    val idreport: String ? ,
    val nivel :String
)

data class CreateRecetaResponse(
    val responseMessage: String,
    val reponseCode: Boolean,
    val idcita: Int
)


//detalle reportes con reporte generado

interface ApiService {


    @POST("sign")
    fun signIn(@Body signInParams: SignInParams): Call<SignInResponse>
    //updateuser
    @POST("updateUser")
    fun updateUser( @Body UpdateUser: UpdateUser, @Header("x-access-token") token: String = Memory.token): Call<ResponseUpdateUser>

    @POST("users")
    fun createPatient(@Body CreatePatientParams: CreatePatientParams): Call<CreatePatientResponse>

    @GET("reportByPatient/{id}")
    fun getReportByPatient(@Header("x-access-token") token: String = Memory.token, @Path("id") patientId: String): Call<List<OriginalReport>>
    @GET("getDietas")
    fun getDietaPatient(@Header("x-access-token") token: String = Memory.token): Call<List<DietaPatient>>

    @GET("getDietaById/{id}")
    fun getDietaById(@Header("x-access-token") token: String = Memory.token, @Path("id") dietaId: String): Call<DietaPatient>
    @GET("getDietaByIdreport/{id}")
    fun getDietaByIdReport(@Header("x-access-token") token: String = Memory.token, @Path("id") dietaId: String): Call<DietaPatient>

    @GET("getReportByPatientOne/{id}")
    fun getReportByPatientOne(@Header("x-access-token") token: String = Memory.token, @Path("id") patientId: String): Call<OriginalReport>
    //detalle reporte por id
    @GET("getReportById/{id}")
    fun getReportById(@Header("x-access-token") token: String = Memory.token, @Path("id") reportId: String): Call<ReportsDetail>

    //Visualizar reporte
    @GET("WatchByReport/{id}")
    fun WatchByReport(@Header("x-access-token") token: String = Memory.token, @Path("id") reportId: String): Call<WatchReportResponse>

    //listado paciente por dni
    @POST("searchPatientsByDni")
    fun searchPatientsByDni( @Body SearchPatientParams: SearchPatientParams, @Header("x-access-token") token: String = Memory.token): Call<ListPatientResponse>


    //Asignar paciente a medico
    @PUT("asingPatientMedic")
    fun asingPatientMedic( @Body AsingPatientMedicParams: AsingPatientMedicParams, @Header("x-access-token") token: String = Memory.token): Call<AsingPatientMedicResponse>

    //Crear reporte
    @POST("report")
    fun report( @Body CreateReportParams: CreateReportParams, @Header("x-access-token") token: String = Memory.token): Call<CreateReportResponse>
    @POST("createreceta")
    fun createreceta( @Body CreateReceta: CreateReceta, @Header("x-access-token") token: String = Memory.token): Call<CreateRecetaResponse>


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