package com.mediclab.smartreports.ui.users

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.*
import com.mediclab.smartreports.ui.BaseActivity
import com.google.android.material.textfield.TextInputEditText
import com.mediclab.smartreports.ui.login.LoginActivity
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
    lateinit var terms: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_create)
        bling()
        createUser()
        onClickEvents()

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
        terms = findViewById(R.id.terminosid)

        changeListener(nameCreate, btnCreate)
        changeListener(lastNameCreate, btnCreate)
        changeListener(emailCreate, btnCreate)
        changeListener(phoneCreate, btnCreate)
        changeListener(passCreate, btnCreate)

    }


    fun createUser(){
        btnCreate.setOnClickListener {

            if (validateData()) {
                val dni: Int = dniCreate.text.toString().toInt()
                Api.retrofitService.createPatient(CreatePatientParams(nameCreate.text.toString(),
                    lastNameCreate.text.toString(),emailCreate.text.toString(), passCreate.text.toString(),phoneCreate.text.toString().toInt()
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

    private fun validateData(): Boolean {
        if (nameCreate.text.toString().isEmpty()) {
            nameCreate.error = "Nombre requerido"
            return false
        }

        if (lastNameCreate.text.toString().isEmpty()) {
            lastNameCreate.error = "Apellidos requerido"
            return false
        }
        if (dniCreate.text.toString().isEmpty()) {
            dniCreate.error = "Dni requerido"
            return false
        }
        if (emailCreate.text.toString().isEmpty()) {
            emailCreate.error = "Email requerido"
            return false
        }
        if (passCreate.text.toString().isEmpty()) {
            passCreate.error = "Password requerido"
            return false
        }
        return true
    }

    private fun onClickEvents() {
        existAccount.setOnClickListener {
            goTo(LoginActivity::class.java)
        }
        terms.setOnClickListener {
            goTo(TermsActivity::class.java)
        }
    }

}