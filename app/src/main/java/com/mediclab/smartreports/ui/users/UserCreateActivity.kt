package com.mediclab.smartreports.ui.users

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.*
import com.mediclab.smartreports.ui.BaseActivity
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserCreateActivity : BaseActivity() {

    lateinit var nameCreate: TextInputEditText
    lateinit var lastNameCreate: TextInputEditText
    lateinit var dniCreate: TextInputEditText
    lateinit var emailCreate: TextInputEditText
    lateinit var phoneCreate: TextInputEditText
    lateinit var passCreate: TextInputEditText
    lateinit var btnCreate: Button
    lateinit var existAccount: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_create)
        bling()
        createUser()


    }

    fun bling(){
        nameCreate = findViewById(R.id.nameUserRegister)
        lastNameCreate = findViewById(R.id.lastnameUserRegister)
        dniCreate = findViewById(R.id.dniUserRegister)
        emailCreate = findViewById(R.id.emailUserRegister)
        phoneCreate = findViewById(R.id.phoneUserRegister)
        passCreate = findViewById(R.id.passCreate)
        btnCreate = findViewById(R.id.btnUserRegister)
        existAccount = findViewById(R.id.existAccount)

    }
    fun getText(){


    }

    fun createUser(){
        btnCreate.setOnClickListener {
            val dni: Int = dniCreate.text.toString().toInt()
            Api.retrofitService.createPatient(CreatePatientParams(nameCreate.text.toString(),
                lastNameCreate.text.toString(),emailCreate.text.toString(), passCreate.text.toString()
            , 1, false, dni, true,0)).enqueue(object :
                Callback<CreatePatientResponse> {
                override fun onResponse(call: Call<CreatePatientResponse>, response:
                Response<CreatePatientResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if(it.status){
                                showToast("Solicitud de registro exitosa.")
                                onBackPressed()
                            }else{
                                showToast("Correo ya exitente.")
                            }
                        }
                        dismissDialog()
                    } else {
                        dismissDialog()
                    }
                }
                override fun onFailure(call: Call<CreatePatientResponse>, t: Throwable) {
                    showToast("Error.")

                }
            })


        }

    }
}