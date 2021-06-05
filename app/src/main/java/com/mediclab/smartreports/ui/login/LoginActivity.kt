package com.mediclab.smartreports.ui.login

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.Api
import com.mediclab.smartreports.data.sign.SignInParams
import com.mediclab.smartreports.data.sign.SignInResponse
import com.mediclab.smartreports.ui.BaseActivity
import com.mediclab.smartreports.ui.patient.HomePatientActivity
import com.mediclab.smartreports.ui.users.UserCreateActivity
import com.mediclab.smartreports.utils.Logger
import com.mediclab.smartreports.utils.Memory
import com.google.android.material.textfield.TextInputEditText
import com.mediclab.smartreports.ui.doct.HomeDocActivity
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
        changeListener(etUser, signInButton)
        changeListener(etPass, signInButton)

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
                override fun onResponse(
                    call: Call<SignInResponse>,
                    response: Response<SignInResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (it.response) {
                                singByRol(it.userId.toString(), it.userName, it.token, it.rol, it.status )
                            } else {
                                dismissDialog()
                                showAlert()
                            }
                        }
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


    private fun singByRol(id: String = "", userName : String = "", token: String = "", role: Int = 0, status: Boolean = false ){
        if(!status){
            dismissDialog()
            showToast("Aun no tiene autorizacion para ingresar.")
        }else{
            Memory.userName = userName
            Memory.token = token
            Memory.id = id
            if (cbRemember.isChecked) {
                Memory.saveInMemory("user", user)
                Memory.saveInMemory("pass", pass)
            } else {
                Memory.delete("user")
                Memory.delete("pass")
            }
            if(role == 1){
                goTo(HomePatientActivity::class.java, true)
            }else{
                goTo(HomeDocActivity::class.java, true)
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

fun String.isAValidEmail(): Boolean {
    return this.matches(emailPattern.toRegex())
}