package com.example.quiz.data.source.local

import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
enum class SharedPreferencesKeyNames(val keyName: String) {
    USER_ID("user_id")
}