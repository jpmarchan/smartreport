package com.mediclab.smartreports.ui.users

import android.os.Bundle
import android.widget.Button
import com.mediclab.smartreports.R
import com.mediclab.smartreports.ui.BaseActivity

class UsersActivity : BaseActivity() {

    lateinit var btn_get_users: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        val bundle = intent.extras
        val email:String = bundle?.getString("email").toString()
        btn_get_users = findViewById(R.id.get_button)
        getUsers(email)
    }

    fun getUsers(email: String){
        btn_get_users.setOnClickListener {

            db.collection("Users").document(email)
                .get().addOnSuccessListener {
                    documentSnapshot ->
                    showToast(documentSnapshot.get("name").toString())
                }
        }
    }

}