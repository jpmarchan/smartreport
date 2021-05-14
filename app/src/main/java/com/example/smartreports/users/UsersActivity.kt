package com.example.smartreports.users

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartreports.R
import com.google.firebase.firestore.FirebaseFirestore


class UsersActivity : AppCompatActivity() {

    lateinit var btn_get_users: Button
    private val db = FirebaseFirestore.getInstance()


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
                    Toast.makeText(this, documentSnapshot.get("name").toString(), Toast.LENGTH_SHORT).show()

                }


        }
    }

}