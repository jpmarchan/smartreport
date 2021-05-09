package com.example.smartreports.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.smartreports.MainActivity
import com.example.smartreports.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var user: EditText
    lateinit var pass: EditText
    lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        user = findViewById(R.id.txt_user)
        pass = findViewById(R.id.txt_pass)
        btn = findViewById(R.id.sign_button)
        sign()

    }

    fun sign(){



        btn.setOnClickListener {

            if (user.text.toString().isNotEmpty() && pass.text.toString().isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        user.text.toString(),
                        pass.text.toString()
                    ).addOnCompleteListener {
                        print(it.isSuccessful)
                        if (it.isSuccessful) {

                            Toast.makeText(this, "ingresando", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            /*Seteamos los flags al intent para que inicie una nueva actividad
                             sin posibilidad de regresar al activity anterior*/
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                            //Iniciamos la actividad mediante el intent
                            startActivity(intent)
                        } else {
                            showAlert()
                        }
                    }
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


}