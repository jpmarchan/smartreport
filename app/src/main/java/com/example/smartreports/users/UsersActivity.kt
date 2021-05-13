package com.example.smartreports.users

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartreports.R
import com.google.firebase.firestore.FirebaseFirestore


class UsersActivity : AppCompatActivity() {

    lateinit var btn_get_users: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        btn_get_users = findViewById(R.id.get_button)
        getUsers()

    }

    fun getUsers(){
        btn_get_users.setOnClickListener {

            val db = FirebaseFirestore.getInstance()
            db.collection("Users")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            Toast.makeText(this, document.id, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

}