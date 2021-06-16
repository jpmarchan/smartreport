package com.mediclab.smartreports.ui.doct

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.*
import com.mediclab.smartreports.ui.BaseActivity
import com.mediclab.smartreports.ui.patient.HomePatientActivity
import com.mediclab.smartreports.utils.Logger
import com.mediclab.smartreports.utils.Memory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeDocActivity : BaseActivity() {
    var statusCb: Boolean = false
    lateinit var setName: TextView
    lateinit var dnisearc: EditText
    lateinit var namesearch: EditText
    lateinit var lasnamesearch: EditText
    lateinit var btnSearch: Button

    private lateinit var cbshearch: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dock)
        bind()
        setName.text = Memory.userName
        search()

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
        cbshearch =  findViewById(R.id.cbSearch)

    }
    private fun search(){
        btnSearch.setOnClickListener {

            if (statusCb){

            }else{
                Logger.d("entrando")
                    goTo(DetailSearchPatientActivity::class.java, false,"dnish","${dnisearc.text}")

            }
        }
    }

    fun checkBoxClic(v: View) {
        val  cbshearch = v as CheckBox
         cbshearch.isChecked
        if(cbshearch.isChecked){
            namesearch.visibility = View.VISIBLE
            lasnamesearch.visibility = View.VISIBLE
            statusCb = true
        }else{
            statusCb = false
            namesearch.visibility = View.GONE
            lasnamesearch.visibility = View.GONE
        }
    }




}