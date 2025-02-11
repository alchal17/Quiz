package com.example.quiz.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.api.ApiResponse
import com.example.quiz.api.QuizApi
import com.example.quiz.models.database_representation.Quiz
import com.example.quiz.models.request_representation.Base64Quiz
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel(private val quizApi: QuizApi) : ViewModel() {
    private val _quizName: MutableStateFlow<String> = MutableStateFlow("")
    val quizName: StateFlow<String> = _quizName.asStateFlow()

    private val _base64Image: MutableStateFlow<String?> = MutableStateFlow(null)
    val base64Image: StateFlow<String?> = _base64Image.asStateFlow()

    private val _description: MutableStateFlow<String> = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _quizzesQuery: MutableStateFlow<List<Quiz>> = MutableStateFlow(emptyList())
    val quizzesQuery: StateFlow<List<Quiz>> = _quizzesQuery.asStateFlow()

    fun setQuizName(newQuizName: String) {
        _quizName.value = newQuizName
    }

    fun setBase64Image(newBase64Image: String?) {
        _base64Image.value = newBase64Image
    }

    fun setDescription(newDescription: String) {
        _description.value = newDescription
    }


    fun getById(quizId: Int) {
        viewModelScope.launch {
            when (val apiResponse = quizApi.getById(1)) {
                is ApiResponse.Error -> TODO("Handle error case when not finding a quiz by id")
                is ApiResponse.Success -> {
                    val base64Quiz = apiResponse.data
                    setQuizName(base64Quiz.name)
                    setDescription(base64Quiz.description ?: "")
                    setBase64Image(base64Quiz.base64Image)
                }
            }
        }
    }

    suspend fun createBase64Quiz(userId: Int): ApiResponse<Int> {
        val base64Quiz = Base64Quiz(
            id = null,
            name = _quizName.value,
            userId = userId,
            base64Image = _base64Image.value,
            description = _description.value.ifEmpty { null }
        )
        return quizApi.create(base64Quiz)
    }

    suspend fun updateQuiz(quizId: Int, userId: Int): ApiResponse<String> {
        val base64Quiz = Base64Quiz(
            id = quizId,
            name = _quizName.value,
            userId = userId,
            base64Image = _base64Image.value,
            description = _description.value
        )
        return quizApi.update(base64Quiz, quizId)
    }

    suspend fun deleteQuiz(quizId: Int): ApiResponse<String> {
        return quizApi.deleteById(quizId)
    }
}