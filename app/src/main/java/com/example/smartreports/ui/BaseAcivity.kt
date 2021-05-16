package com.example.smartreports.ui

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

open class BaseActivity: AppCompatActivity() {

    private val mIntent = Intent()
    val db = FirebaseFirestore.getInstance()


    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun goTo(targetClass: Class<*>, isCleared: Boolean = false, extraLabel: String = "",
             extraData: String = "") {
        mIntent.setClass(this, targetClass)
        if (isCleared) {
            // use intent.apply {}
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (extraData.isNotEmpty()) {
            mIntent.putExtra(extraLabel, extraData)
        }
        startActivity(mIntent)
    }
}