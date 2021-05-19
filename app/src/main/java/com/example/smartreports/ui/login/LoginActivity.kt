package com.example.smartreports.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.smartreports.R
import com.example.smartreports.ui.BaseActivity
import com.example.smartreports.ui.patient.HomeActivity
import com.example.smartreports.ui.users.UserCreateActivity
import com.example.smartreports.utils.Logger
import com.example.smartreports.utils.Memory
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity() {

    private lateinit var etUser: TextInputEditText
    private lateinit var etPass: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpButton: TextView
    private lateinit var cbRemember: CheckBox
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    private lateinit var user: String
    private lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Very important!!!
        Memory.initMemory(this)

        bind()
        signInWithFirebase()
        onClickEvents()
    }

    private fun bind() {
        etUser = findViewById(R.id.txt_user)
        etPass = findViewById(R.id.txt_pass)
        signInButton = findViewById(R.id.signin_button)
        signUpButton = findViewById(R.id.signup_button)
        cbRemember = findViewById(R.id.cbRemember)

        if (Memory.exists("user")) {
            etUser.setText(Memory.getValue("user"))
            etPass.setText(Memory.getValue("pass"))
            cbRemember.isChecked = true
            signInButton.setBackgroundResource(R.drawable.bg_button_selected)
        }

        etUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                if (s != null){
                    if (s.isNotEmpty()){
                        signInButton.setBackgroundResource(R.drawable.bg_button_selected)
                    } else {
                        signInButton.setBackgroundResource(R.drawable.bg_button_normal)
                    }
                }
            }
        })
    }

    private fun onClickEvents() {
        signUpButton.setOnClickListener {
            goTo(UserCreateActivity::class.java)
        }
    }

    private fun signInWithFirebase() {
        signInButton.setOnClickListener {
            showDialogProgress("Autenticando con el servidor")
            user = etUser.text.toString()
            pass = etPass.text.toString()

            if (user.isNotEmpty() && pass.isNotEmpty()) {
                if (user.trim { it <= ' ' }.matches(emailPattern.toRegex())) {
                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(user, pass).addOnCompleteListener {
                            if (it.isSuccessful) {
                                db.collection("Users").document(user)
                                    .get().addOnSuccessListener { documentSnapshot ->
                                        val status =
                                            documentSnapshot.get("status").toString().toBoolean()
                                        if (status) {
                                            if (cbRemember.isChecked) {
                                                Memory.saveInMemory("user", user)
                                                Memory.saveInMemory("pass", pass)
                                            } else {
                                                Memory.delete("user")
                                                Memory.delete("pass")
                                            }
                                            goTo(HomeActivity::class.java, true, "email", user)
                                        } else {
                                            showToast("Aun no tienes la aprobacion para ingresar.")
                                        }
                                    }
                            } else {
                                showAlert()
                            }
                        }
                } else {
                    etUser.error = "Correo inválido"
                }
            } else {
                etUser.error = "Campos requeridos"
                etPass.error = "Campos requeridos"
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autentificando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}