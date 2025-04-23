package com.example.travelmanager.data

import android.content.Context

class DataManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    fun saveUserId(userId: String) {
        sharedPreferences.edit().putString("USER_ID_KEY", userId).apply()
    }

    fun getUserId(): String? {
        return sharedPreferences.getString("USER_ID_KEY", null)
    }

    fun clearUserId() {
        sharedPreferences.edit().remove("USER_ID_KEY").apply()
    }
}