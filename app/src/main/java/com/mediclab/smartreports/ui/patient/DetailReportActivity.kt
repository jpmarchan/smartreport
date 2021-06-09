package com.mediclab.smartreports.ui.patient

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.Api
import com.mediclab.smartreports.data.sign.ReportsDetail
import com.mediclab.smartreports.ui.BaseActivity
import com.mediclab.smartreports.utils.Logger
import com.mediclab.smartreports.utils.Memory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetailReportActivity : BaseActivity(), TextToSpeech.OnInitListener {
    lateinit var codeReport : TextView
    lateinit var date : TextView
    lateinit var medic : TextView
    lateinit var setName: TextView

    private var tts: TextToSpeech? = null
    private var btnreportorigin: Button? = null
    private var buttonSpeak: Button? = null
    lateinit var reportGenerate: TextView
    lateinit var txtreportoriginal: TextView

    private var pitch= 1.0
    private var speed= 1.0





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_report)
        getReportById()
        bind()
        setName.text = Memory.userName

        buttonSpeak!!.isEnabled = false;
        tts = TextToSpeech(this, this)
        buttonSpeak!!.setOnClickListener { speakOut() }
        btnreportorigin!!.setOnClickListener { speakOut2() }

    }
    fun bind(){
        codeReport = findViewById(R.id.codereport)
        date = findViewById(R.id.dateInsert)
        medic = findViewById(R.id.nameMedicDetail)
        setName = findViewById(R.id.setNameDetail)
        reportGenerate = findViewById(R.id.txtreportimproved)
        txtreportoriginal = findViewById(R.id.txtreportoriginal)
        btnreportorigin = findViewById(R.id.btnreportorigin)
        buttonSpeak = findViewById(R.id.btnSpeech)

    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                buttonSpeak!!.isEnabled = true
                btnreportorigin!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    private fun getReportById(){
        val idreport= getIntent().getExtras()?.get("id_report");
        Api.retrofitService.getReportById(Memory.token,idreport.toString())
            .enqueue(object : Callback<List<ReportsDetail>> {
                override fun onResponse(
                    call: Call<List<ReportsDetail>>,
                    response: Response<List<ReportsDetail>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val reportdetail = response.body()!!
                        codeReport.text = "00${reportdetail[0].id}"
                        date.text = reportdetail[0].fecha
                        medic.text = "${reportdetail[0].namedoc} ${reportdetail[0].lastnamedoc}"
                        reportGenerate.text = "${reportdetail[0].detailgenerate}"
                        txtreportoriginal.text = "${reportdetail[0].detail}"

                    }
                }

                override fun onFailure(call: Call<List<ReportsDetail>>, t: Throwable) {
                    Logger.d("onFailure: ${t.message}")
                }
            })
    }


    private fun speakOut() {
        val text = reportGenerate!!.text.toString()

        tts!!.setLanguage(Locale.forLanguageTag("es"))
        tts!!.setPitch(pitch.toFloat())
        tts!!.setSpeechRate(speed.toFloat());
        tts!!.speak(text, TextToSpeech.LANG_AVAILABLE, null,"")

    }
    private fun speakOut2() {
        val text = txtreportoriginal!!.text.toString()

        tts!!.setLanguage(Locale.forLanguageTag("es"))
        tts!!.setPitch(pitch.toFloat())
        tts!!.setSpeechRate(speed.toFloat());
        tts!!.speak(text, TextToSpeech.LANG_AVAILABLE, null,"")

    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

}


