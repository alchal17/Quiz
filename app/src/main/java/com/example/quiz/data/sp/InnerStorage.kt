package com.example.quiz.data.sp

interface InnerStorage {
    suspend fun setInt(sharedPreferencesKeyName: SharedPreferencesKeyNames, value: Int)

    suspend fun getInt(
        sharedPreferencesKeyName: SharedPreferencesKeyNames,
        defaultValue: Int = 0
    ): Int

    suspend fun setString(sharedPreferencesKeyNames: SharedPreferencesKeyNames, value: String)

    suspend fun getString(
        sharedPreferencesKeyNames: SharedPreferencesKeyNames,
        defaultValue: String = ""
    ): String

    suspend fun setBoolean(sharedPreferencesKeyNames: SharedPreferencesKeyNames, value: Boolean)

    suspend fun getBoolean(
        sharedPreferencesKeyNames: SharedPreferencesKeyNames,
        defaultValue: Boolean = false
    ): Boolean
}

