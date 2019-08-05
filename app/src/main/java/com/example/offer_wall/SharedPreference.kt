package com.example.offer_wall

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {

    companion object {
        private const val PREFS_NAME = "ALLOW_PREFERENCES"
    }

    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun containsPreference(keyName: String): Boolean {
        return sharedPref.contains(keyName)
    }

    fun setSharedPreference(keyName: String, status: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(keyName, status)
        editor.apply()
    }

    fun getSharedPreference(keyName: String): Boolean {
        return sharedPref.getBoolean(keyName, false)
    }

}