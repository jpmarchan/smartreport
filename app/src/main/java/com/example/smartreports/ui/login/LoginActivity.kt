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
import com.example.smartreports.ui.users.UserCreateActivity
import com.example.smartreports.ui.users.UsersActivity
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
                            Log.d("tag", it.isSuccessful.toString())
                            if (it.isSuccessful) {
                                 val intent = Intent(this, UsersActivity::class.java)
                                intent.putExtra("email", user.text.toString())
                                /*Seteamos los flags al intent para que inicie una nueva actividad
-                                 sin posibilidad de regresar al activity anterior*/
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                //Iniciamos la actividad mediante el intent
                                 startActivity(intent)
                                 showToast("Ingresando...")
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
            startActivity(Intent(this, UserCreateActivity::class.java))
        }

    }


}