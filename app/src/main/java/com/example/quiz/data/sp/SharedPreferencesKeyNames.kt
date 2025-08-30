package com.example.quiz.data.sp

import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
enum class SharedPreferencesKeyNames(val keyName: String) {
    USER_ID("user_id")
}