package com.sophia.saveandshowmyactivitytoday.register

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private var sharePreferences = context.getSharedPreferences("preference",Context.MODE_PRIVATE)

    @SuppressLint("CommitPrefEdits")
    fun putBoolean(key: String, value: Boolean) {
        val editor = sharePreferences.edit()
        editor.putBoolean(key,value)
    }

    fun getBoolean(key: String): Boolean {
        return sharePreferences.getBoolean(key,false)
    }

    fun putString(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharePreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String): String {
        return sharePreferences.getString(key,null).toString()
    }

    fun clear() {
        val editor: SharedPreferences.Editor = sharePreferences.edit()
        editor.clear()
        editor.apply()
    }
}