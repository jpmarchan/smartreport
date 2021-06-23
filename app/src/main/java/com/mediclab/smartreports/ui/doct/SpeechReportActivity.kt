package com.mediclab.smartreports.ui.doct

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.format.DateFormat
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.Api
import com.mediclab.smartreports.data.sign.CreateReportParams
import com.mediclab.smartreports.data.sign.CreateReportResponse
import com.mediclab.smartreports.ui.BaseActivity
import com.mediclab.smartreports.utils.Memory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.time.days

class SpeechReportActivity : BaseActivity() {
    lateinit var btn: ImageButton
    lateinit var textTv: TextView
    lateinit var buttonSendR: Button

    private val REQUEST_CODE_SPEECH_INPUT = 300


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speech_report)
        bind()
        onClickEvents()
    }
    private  fun bind(){
        btn = findViewById(R.id.voiceBtn)
        textTv = findViewById(R.id.textVoice)
        buttonSendR = findViewById(R.id.buttonSendR)
    }

    private fun onClickEvents() {
        btn.setOnClickListener {
        speak()
        }

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

    private fun speak() {
        val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Dicta tu reporte")


        try {
            startActivityForResult(mIntent,REQUEST_CODE_SPEECH_INPUT)

        }catch (e: Exception){
            Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && null != data){
                    //get text from result
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    //set the text to textview
                    textTv.text = result!![0]

                }
            }
        }
    }

}