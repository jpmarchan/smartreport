package com.mediclab.smartreports.ui.doct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.ui.BaseActivity

class DatePickerActivity : BaseActivity() {
    lateinit var btn: ImageButton
    lateinit var textTv: TextView
    lateinit var buttonSendR: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        bind()
        onClickEvents()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_picker)
    }
    fun  bind(){
        buttonSendR = findViewById(R.id.btndatepicker)
    }

    private fun onClickEvents() {
        buttonSendR.setOnClickListener {
            goTo(DietasListActivity::class.java)
        }
    }

}



