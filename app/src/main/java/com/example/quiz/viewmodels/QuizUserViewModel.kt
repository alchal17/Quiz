package com.example.quiz.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.api.ApiResponse
import com.example.quiz.api.QuizUsersAPI
import com.example.quiz.models.database_representation.QuizUser
import kotlinx.coroutines.launch

class QuizUserViewModel(private val quizUserApi: QuizUsersAPI) : ViewModel() {
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


    suspend fun findUserById(id: Int): QuizUser? {
        return when(val apiResponse = quizUserApi.getById(id)){
            is ApiResponse.Error -> null
            is ApiResponse.Success -> apiResponse.data
        }
    }

    suspend fun findUserByEmail(email: String): QuizUser? =
        quizUserApi.getUserByEmail(email).getOrNull()

    suspend fun findUserByUsername(username: String): QuizUser? =
        quizUserApi.getUserByUsername(username).getOrNull()
}