package com.example.smartreports.ui.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.smartreports.R
import com.example.smartreports.ui.BaseActivity

class HomeActivity : BaseActivity() {
    lateinit var setName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setName = findViewById(R.id.setName)

        val bundle = intent.extras
        val email:String = bundle?.getString("email").toString()
        getUsers(email)
    }
    fun getUsers(email: String){

        db.collection("Users").document(email)
            .get().addOnSuccessListener {
                    documentSnapshot ->
                setName.setText(documentSnapshot.get("name").toString())
            }
    }
}