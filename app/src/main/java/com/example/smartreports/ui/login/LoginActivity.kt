package com.example.smartreports.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.smartreports.R
import com.example.smartreports.ui.BaseActivity
import com.example.smartreports.ui.patient.HomeActivity
import com.example.smartreports.ui.users.UserCreateActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity() {

    lateinit var user: TextInputEditText
    lateinit var pass: EditText
    lateinit var btn: Button
    lateinit var goregister: TextView
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        user = findViewById(R.id.txt_user)
        pass = findViewById(R.id.txt_pass)
        btn = findViewById(R.id.sign_button)
        goregister = findViewById(R.id.goToRegis)

        sign()
        goToRegister()

    }

    fun sign(){
        btn.setOnClickListener {
            if (user.text.toString().isNotEmpty() && pass.text.toString().isNotEmpty()) {
                if (user.text.toString().trim { it <= ' ' }.matches(emailPattern.toRegex())) {
                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(
                            user.text.toString(),
                            pass.text.toString()
                        ).addOnCompleteListener {
                            if (it.isSuccessful) {
                                db.collection("Users").document(user.text.toString())
                                    .get().addOnSuccessListener {
                                            documentSnapshot ->
                                        var status = false
                                        status = documentSnapshot.get("status").toString().toBoolean()
                                        if(status){
                                            goTo(HomeActivity::class.java, true, "email", user.text.toString())
                                        }else{
                                            showToast("Aun no tienes la aprobacion para ingresar.")
                                        }
                                    }
                            } else {
                                showAlert()
                            }
                        }
                }else{
                    user.error = "correo invalido"
                }
            }else {
                user.error = "Campos requeridos"
                pass.error = "Campos requeridos"
            }
        }
    }



    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autentificando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun goToRegister(){
        goregister.setOnClickListener {
            goTo(UserCreateActivity::class.java)
        }

    }


}