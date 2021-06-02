package com.mediclab.smartreports.ui

import android.app.ProgressDialog
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

open class BaseActivity: AppCompatActivity() {

    private val mIntent = Intent()
    val db = FirebaseFirestore.getInstance()
    private lateinit var progress: ProgressDialog

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissDialog()
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

    fun showDialogProgress(message: String = "") {
        progress = ProgressDialog.show(this, "",
            message, true)
    }

    fun dismissDialog() {
        if (::progress.isInitialized) {
            progress.dismiss()
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