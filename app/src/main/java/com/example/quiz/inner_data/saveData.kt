package com.example.quiz.inner_data

import android.content.Context

fun saveUserData(userId: Int, isSignedIn: Boolean, context: Context) {
    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().apply {
        putInt("user_id", userId)
        putBoolean("isSignedIn", isSignedIn)
        apply()
    }
}
