package com.example.smartreports.ui.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.smartreports.R
import com.example.smartreports.ui.BaseActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

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
        nameCreate = findViewById(R.id.nameUserRegister)
        lastNameCreate = findViewById(R.id.lastnameUserRegister)
        dniCreate = findViewById(R.id.dniUserRegister)
        emailCreate = findViewById(R.id.emailUserRegister)
        phoneCreate = findViewById(R.id.phoneUserRegister)
        passCreate = findViewById(R.id.passCreate)
        btnCreate = findViewById(R.id.btnUserRegister)
        existAccount = findViewById(R.id.existAccount)

        createUser()

    }

    fun createUser(){
        btnCreate.setOnClickListener {
            db.collection("Users").document(emailCreate.text.toString()).set(
                hashMapOf("name" to nameCreate.text.toString(),
                    "lastname" to lastNameCreate.text.toString(),
                    "phone" to phoneCreate.text.toString(),
                    "rol" to "paciente",
                    "status" to false,
                    "dni" to dniCreate.text.toString()
                )
            )
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                emailCreate.text.toString(),
                passCreate.text.toString()
            ).addOnCompleteListener {task ->
                if(task.isSuccessful){
                    showToast("Solicitud de registro enviada.")
                }
            }

        }

    }
}