package com.example.smartreports.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.smartreports.R
import com.example.smartreports.data.sign.Api
import com.example.smartreports.data.sign.SignInParams
import com.example.smartreports.data.sign.SignInResponse
import com.example.smartreports.ui.BaseActivity
import com.example.smartreports.ui.patient.HomeActivity
import com.example.smartreports.ui.users.UserCreateActivity
import com.example.smartreports.utils.Logger
import com.example.smartreports.utils.Memory
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

class LoginActivity : BaseActivity() {

    private lateinit var etUser: TextInputEditText
    private lateinit var etPass: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpButton: TextView
    private lateinit var cbRemember: CheckBox

    private lateinit var user: String
    private lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Very important!!!
        Memory.initMemory(this)

        bind()
        signInWithServer()
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

    private fun signInWithServer() {
        signInButton.setOnClickListener {
            user = etUser.text.toString()
            pass = etPass.text.toString()

            if (user.isEmpty()) {
                etUser.error = "Ingrese un correo válido"
                return@setOnClickListener
            }
            if (pass.isEmpty()) {
                etPass.error = "Ingrese una contraseña"
                return@setOnClickListener
            }

            showDialogProgress("Autenticando con el servidor...")
            val signInParams = SignInParams(user, pass)
            Logger.d("signInParams: $signInParams")
            Api.retrofitService.signIn(SignInParams(user, pass)).enqueue(object :
                Callback<SignInResponse> {
                override fun onResponse(call: Call<SignInResponse>, response: Response<SignInResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (it.response) {
                                Memory.userName = it.userName?: ""
                                if (cbRemember.isChecked) {
                                    Memory.saveInMemory("user", user)
                                    Memory.saveInMemory("pass", pass)
                                } else {
                                    Memory.delete("user")
                                    Memory.delete("pass")
                                }
                                goTo(HomeActivity::class.java, true)
                            } else {
                                dismissDialog()
                                showAlert()
                            }
                        }
                        dismissDialog()
                        showAlert()
                    } else {
                        dismissDialog()
                        showAlert()
                    }
                }

                override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                    dismissDialog()
                    showAlert()
                }
            })
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

fun String.isAValidEmail(): Boolean {
    return this.matches(emailPattern.toRegex())
}