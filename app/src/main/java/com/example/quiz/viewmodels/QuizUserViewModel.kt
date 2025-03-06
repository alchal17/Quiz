package com.example.quiz.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.api.ApiResponse
import com.example.quiz.api.reading.QuizUserReadingAPI
import com.example.quiz.api.writing.QuizUserWritingAPI
import com.example.quiz.models.database_representation.QuizUser
import kotlinx.coroutines.launch

class QuizUserViewModel(
    private val readingAPI: QuizUserReadingAPI,
    private val writingAPI: QuizUserWritingAPI
) : ViewModel() {
    fun handleIfUserExistsByEmail(
        email: String,
        onTrue: (userID: Int) -> Unit,
        onFalse: () -> Unit = {}
    ) {
        viewModelScope.launch {
            when (val result = readingAPI.getUserByEmail(email)) {
                is ApiResponse.Error -> {
                    onFalse()
                }

                is ApiResponse.Success -> {
                    val user = result.data
                    user.id?.let { onTrue(it) }
                }
            }
        }
    }

    fun handleIfUserExistsByUsername(
        username: String,
        onTrue: () -> Unit,
        onFalse: () -> Unit = {}
    ) {
        viewModelScope.launch {
            when (readingAPI.getUserByUsername(username)) {
                is ApiResponse.Error -> {
                    onFalse()
                }

                is ApiResponse.Success -> {
                    onTrue()
                }
            }
        }
    }

    suspend fun createUser(user: QuizUser): ApiResponse<Int> {
        return writingAPI.create(user)
    }


    suspend fun findUserById(id: Int): QuizUser? {

        return when (val apiResponse = readingAPI.getById(id)) {
            is ApiResponse.Error -> null
            is ApiResponse.Success -> apiResponse.data
        }
    }

    suspend fun findUserByEmail(email: String): QuizUser?{
        return when(val response = readingAPI.getUserByEmail(email)){
            is ApiResponse.Error -> null
            is ApiResponse.Success -> response.data
        }
    }

    suspend fun findUserByUsername(username: String): QuizUser?{
        return when(val response = readingAPI.getUserByUsername(username)){
            is ApiResponse.Error -> null
            is ApiResponse.Success -> response.data
        }
    }
}