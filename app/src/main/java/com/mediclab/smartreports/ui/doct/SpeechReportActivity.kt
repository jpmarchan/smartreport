package com.mediclab.smartreports.ui.doct

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.mediclab.smartreports.R
import java.lang.Exception
import java.util.*

class SpeechReportActivity : AppCompatActivity() {
    lateinit var btn: ImageButton
    lateinit var textTv: TextView

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
    }

    private fun onClickEvents() {
        btn.setOnClickListener {
        speak()
        }
    }

    private fun speak() {
        val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"hola de nuevo")


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