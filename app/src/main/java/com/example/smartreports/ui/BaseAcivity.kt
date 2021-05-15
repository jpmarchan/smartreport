package com.example.smartreports.ui

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun goTo(targetClass: Class<*>, isCleared: Boolean = false, extraLabel: String = "",
             extraData: String = "") {
        val intent = Intent(this, targetClass.javaClass)
        if (isCleared) {
            // use intent.apply {}
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (extraData.isNotEmpty()) {
            intent.putExtra(extraLabel, extraData)
        }
        startActivity(intent)
    }
}