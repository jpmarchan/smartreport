package com.mediclab.smartreports.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mediclab.smartreports.R
import android.speech.tts.TextToSpeech
import java.util.*


open class BaseActivity : AppCompatActivity() {

    private val mIntent = Intent()
    private lateinit var progress: ProgressDialog

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissDialog()
    }

    fun goTo(
        targetClass: Class<*>, isCleared: Boolean = false, extraLabel: String = "",
        extraData: String = ""
    ) {
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
    fun goTo1(
        targetClass: Class<*>, isCleared: Boolean = false, extraLabel: String = "",
        extraData: String = "", extraLabel1: String = "",
        extraData1: String = ""
    ) {
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
        if (extraData1.isNotEmpty()) {
            mIntent.putExtra(extraLabel1, extraData1)
        }
        startActivity(mIntent)
    }



    fun showDialogProgress(message: String = "") {
        progress = ProgressDialog.show(
            this, "",
            message, true
        )
    }

    fun dismissDialog() {
        if (::progress.isInitialized) {
            progress.dismiss()
        }
    }
    fun changeListener(input: EditText, btn: Button){
        input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.isNotEmpty()) {
                        btn.setBackgroundResource(R.drawable.bg_button_selected)
                    } else {
                        btn.setBackgroundResource(R.drawable.bg_button_normal)
                    }
                }
            }
        })
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