package com.mediclab.smartreports.ui.doct

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.SeekBar
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat

import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.*
import com.mediclab.smartreports.ui.BaseActivity
import com.mediclab.smartreports.utils.Memory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DatePickerActivity : BaseActivity() {
    private  var idpaient : Int = 0

    private  lateinit var continuousSeekBar: SeekBar

    private lateinit var buttonSendR: Button
    private lateinit var seekbar: SeekBar
    private lateinit var candelady: CalendarView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_picker)
        bind()
        onClickEvents()
        continuousSeekBar = seekbar

    }
    fun  bind(){
        seekbar = findViewById(R.id.seekBar)
        candelady = findViewById(R.id.calendarView)
        buttonSendR = findViewById(R.id.btndatep)
    }


    private fun onClickEvents() {
        var date= "";
        var nivel= "";


        Log.d("FATAL", "timestamp ${candelady.date}")
        candelady.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
            val timestamp = view.date
            Log.d("FATAL", "timestamp $timestamp")
            Log.d("FATAL", "timestamp ${candelady.date}")
            val msg = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
            date = msg
        }
        val id_dieta= getIntent().getExtras()?.get("id_dieta");
        val idreport= getIntent().getExtras()?.get("idreport");

        buttonSendR.setOnClickListener {

            continuousSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                   nivel = progress.toString()

                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            })
            Api.retrofitService.createreceta(CreateReceta(date, id_dieta.toString(), idreport.toString() ,nivel)).enqueue(object :
                Callback<CreateRecetaResponse> {
                override fun onResponse(
                    call: Call<CreateRecetaResponse>,
                    response: Response<CreateRecetaResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if(it.reponseCode == true){
                                goTo(HomeDocActivity::class.java,true)
                                showToast("Reporte emitido con exito.")

                            }else{
                                showToast("Error al enviar el reporte")
                            }
                        }
                    } else {
                    }
                }
                override fun onFailure(call: Call<CreateRecetaResponse>, t: Throwable) {

                }
            })
        }
    }

}



