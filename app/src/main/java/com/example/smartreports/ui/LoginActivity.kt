package com.example.smartreports.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.smartreports.MainActivity
import com.example.smartreports.R

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

        if (user.text.toString().isEmpty() || pass.text.toString().isEmpty()) {
            Toast.makeText(this, "Debe completar los campos", Toast.LENGTH_SHORT).show()
        }

        btn.setOnClickListener {
            if(user.text.toString() == "yennerjp" && pass.text.toString() == "123456"
                || user.text.toString() == "harol" && pass.text.toString() == "654321") {
                Toast.makeText(this, "ingresando", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)

                /*Seteamos los flags al intent para que inicie una nueva actividad
                sin posibilidad de regresar al activity anterior*/
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                //Iniciamos la actividad mediante el intent
                startActivity(intent)

            }else{
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

    }

}