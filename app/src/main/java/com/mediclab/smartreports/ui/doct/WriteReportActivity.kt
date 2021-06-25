package com.mediclab.smartreports.ui.doct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.Api
import com.mediclab.smartreports.data.sign.CreateReportParams
import com.mediclab.smartreports.data.sign.CreateReportResponse
import com.mediclab.smartreports.ui.BaseActivity
import com.mediclab.smartreports.utils.Memory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteReportActivity : BaseActivity() {
    lateinit var buttonSendR: Button
    lateinit var textTv: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_report)
        bind()
        onClickEvents()
    }

    private  fun bind(){
        textTv = findViewById(R.id.textWrite)
        buttonSendR = findViewById(R.id.buttonSendReport)
    }



    private fun onClickEvents() {

        buttonSendR.setOnClickListener {

            val currentTimestamp = System.currentTimeMillis()

            val idpatient = getIntent().getExtras()?.get("idpatient")

            Api.retrofitService.report(CreateReportParams(currentTimestamp.toString(), Memory.id.toInt(), idpatient.toString() ,textTv.text.toString())).enqueue(object :
                Callback<CreateReportResponse> {
                override fun onResponse(
                    call: Call<CreateReportResponse>,
                    response: Response<CreateReportResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if(it.reponseCode == true){
                                showToast("Reporte enviado con exito")
                                onBackPressed()
                            }else{
                                showToast("Error al enviar el reporte")
                            }
                        }
                    } else {
                    }
                }
                override fun onFailure(call: Call<CreateReportResponse>, t: Throwable) {

                }
            })
        }
    }
}