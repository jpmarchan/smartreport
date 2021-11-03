package com.mediclab.smartreports.ui.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mediclab.smartreports.R
import com.mediclab.smartreports.ui.BaseActivity
import com.mediclab.smartreports.utils.Memory

class UpdateActivity : BaseActivity() {
    lateinit var btnupdate: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        bind()
        onClickEvents()
    }

    private fun bind() {
        //etUser = findViewById(R.id.txt_user)
        //etPass = findViewById(R.id.txt_pass)
        //etPass = findViewById(R.id.txt_pass)
        btnupdate = findViewById(R.id.update_button)

    }

    private fun onClickEvents() {
        btnupdate.setOnClickListener {
            goTo(HomePatientActivity::class.java)
        }
    }
}