package com.sophia.saveandshowmyactivitytoday.register

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONException

class PreferenceManager(context: Context) {

    private var sharePreferences = context.getSharedPreferences("preference",Context.MODE_PRIVATE)


    @SuppressLint("CommitPrefEdits")
    fun putBoolean(key: String, value: Boolean) {
        val editor = sharePreferences.edit()
        editor.putBoolean(key,value)
        editor.apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharePreferences.getBoolean(key,false)
    }

    fun putInteger(key: String, value: Int) {
        val editor = sharePreferences.edit()
        editor.putInt(key,value)
        editor.apply()
    }

    fun getInteger(key: String): Int {
        return sharePreferences.getInt(key, DEFAULT_BUFFER_SIZE)
    }

    fun putString(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharePreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String): String? {
        return sharePreferences.getString(key,null)
    }

    fun clear() {
        val editor: SharedPreferences.Editor = sharePreferences.edit()
        editor.clear()
        editor.apply()
    }
}