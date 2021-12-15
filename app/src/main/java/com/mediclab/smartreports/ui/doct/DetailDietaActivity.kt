package com.mediclab.smartreports.ui.doct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.*
import com.mediclab.smartreports.ui.BaseActivity
import com.mediclab.smartreports.utils.Logger
import com.mediclab.smartreports.utils.Memory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailDietaActivity : BaseActivity() {


    lateinit var ingdesayuno: TextView
    lateinit var name: TextView
    lateinit var ingalmuerzo: TextView
    lateinit var ingcena: TextView
    lateinit var predesayuno: TextView
    lateinit var prealmuerzo: TextView
    lateinit var precena: TextView
    lateinit var btnasign : Button
    private var iddieta : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_dieta)
        bind()
        getReportById()
        onClickEvents()
    }
    fun bind(){

        name = findViewById(R.id.textViename)
        ingdesayuno = findViewById(R.id.ingdesayuno)
        ingalmuerzo = findViewById(R.id.ingalmuerzo)
        ingcena = findViewById(R.id.ingcena)
        predesayuno = findViewById(R.id.predesayuno)
        prealmuerzo = findViewById(R.id.prealmuerzo)
        precena = findViewById(R.id.precena)
        btnasign = findViewById(R.id.btnregisterdieta)

    }

    private fun getReportById(){
        val idreport= getIntent().getExtras()?.get("id_dieta");
        Api.retrofitService.getDietaById(Memory.token,idreport.toString())
            .enqueue(object : Callback<DietaPatient> {
                override fun onResponse(
                    call: Call<DietaPatient>,
                    response: Response<DietaPatient>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val reportdetail = response.body()!!

                        name.text = reportdetail.name
                        ingdesayuno.text = reportdetail.ingredientesdesayuno
                        ingalmuerzo.text = reportdetail.ingredientesalmuerzo
                        ingcena.text = reportdetail.ingredientescena
                        predesayuno.text = reportdetail.desayuno
                        prealmuerzo.text = reportdetail.almuerzo
                        precena.text = reportdetail.cena
                        iddieta = reportdetail.id
                    }
                }
                override fun onFailure(call: Call<DietaPatient>, t: Throwable) {
                    Logger.d("onFailure: ${t.message}")
                }
            })

    }

    private fun onClickEvents() {
        val idreport = getIntent().getExtras()?.get("idreport")
        btnasign.setOnClickListener {
            goTo1(DatePickerActivity::class.java, false,"id_dieta","$iddieta","idreport","$idreport")
        }
    }

}