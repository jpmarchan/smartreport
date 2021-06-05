package com.mediclab.smartreports.ui.doct

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.ui.BaseActivity

class HomeDocActivity : BaseActivity() {
    lateinit var setName: TextView
    lateinit var dnisearc: EditText
    lateinit var namesearch: EditText
    lateinit var lasnamesearch: EditText
    lateinit var btnSearch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dock)
        bind()
    }

    private  fun bind(){
        setName = findViewById(R.id.setNameDoc)
        btnSearch = findViewById(R.id.btnSearchPatient)
        dnisearc = findViewById(R.id.dnisearch)
        namesearch = findViewById(R.id.txtSearchNamePatient)
        lasnamesearch = findViewById(R.id.txtSearchLasnamePatient)
        changeListener(dnisearc, btnSearch)
        changeListener(namesearch, btnSearch)
        changeListener(lasnamesearch, btnSearch)

    }


}