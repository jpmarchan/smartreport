package com.example.smartreports.utils

import android.content.Context
import android.content.SharedPreferences

object Memory {

    var userName: String = ""
    var token: String = ""

    private lateinit var sharedPref: SharedPreferences

    fun initMemory(context: Context) {
        sharedPref = context.getSharedPreferences("raa", Context.MODE_PRIVATE)
    }

    fun saveInMemory(key: String, values: String) {
        Logger.d("Saving key=$key, values=$values")
        sharedPref.edit().putString(key, values).apply()
    }

    fun exists(key: String): Boolean {
        return sharedPref.contains(key)
    }

    fun delete(key: String) {
        Logger.d("Removing key=$key")
        sharedPref.edit().remove(key).apply()
    }

    fun getValue(key: String): String {
        return sharedPref.getString(key, "") ?: ""
    }
}