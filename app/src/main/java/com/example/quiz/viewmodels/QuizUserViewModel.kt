package com.example.quiz.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.api.QuizUsersApi
import com.example.quiz.models.QuizUser
import kotlinx.coroutines.launch

class QuizUserViewModel(private val quizUserApi: QuizUsersApi) : ViewModel() {
    fun handleIfUserExistsByEmail(
        email: String,
        onTrue: (userID: Int) -> Unit,
        onFalse: () -> Unit = {}
    ) {
        viewModelScope.launch {
            val result = quizUserApi.getUserByEmail(email)
            if (result.isSuccess) {
                val user = result.getOrNull()
                user?.id?.let { id -> onTrue(id) }
            } else {
                onFalse()
            }
        }
    }

    fun handleIfUserExistsByUsername(
        username: String,
        onTrue: () -> Unit,
        onFalse: () -> Unit = {}
    ) {
        viewModelScope.launch {
            if (quizUserApi.getUserByUsername(username).isSuccess) onTrue() else onFalse()
        }
    }

    suspend fun createUser(user: QuizUser): Int {
        val userId = quizUserApi.createUser(user)
        return userId
    }


    suspend fun findUserById(id: Int): QuizUser? = quizUserApi.getUserById(id).getOrNull()

    suspend fun findUserByEmail(email: String): QuizUser? =
        quizUserApi.getUserByEmail(email).getOrNull()

    suspend fun findUserByUsername(username: String): QuizUser? =
        quizUserApi.getUserByUsername(username).getOrNull()
}