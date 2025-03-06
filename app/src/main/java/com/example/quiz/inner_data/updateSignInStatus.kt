package com.example.quiz.inner_data

import android.content.Context

fun updateSignInStatus(userId: Int, isSignedIn: Boolean, context: Context) {
    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    val storedUserId = sharedPreferences.getInt("user_id", -1) // Default -1 means no user saved

    if (storedUserId == userId) {
        sharedPreferences.edit().apply {
            putBoolean("isSignedIn", isSignedIn)
            apply()
        }
    }
}
