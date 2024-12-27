package com.example.quiz.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.api.QuizApi
import com.example.quiz.models.request_representation.Base64Quiz
import com.example.quiz.models.request_representation.Base64QuizQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel(private val quizApi: QuizApi) : ViewModel() {
    private val _quizName = MutableStateFlow("")
    val quizName: StateFlow<String> = _quizName.asStateFlow()

    private val _base64Image = MutableStateFlow<String?>(null)
    val base64Image: StateFlow<String?> = _base64Image.asStateFlow()

    private val _quizBase64Questions = MutableStateFlow<List<Base64QuizQuestion>>(emptyList())
    val quizBase64Questions: StateFlow<List<Base64QuizQuestion>> =
        _quizBase64Questions.asStateFlow()

    fun setName(newValue: String) {
        _quizName.value = newValue
    }

    fun setImage(newBase64: String) {
        _base64Image.value = newBase64
    }

    fun addBase64Question(base64QuizQuestion: Base64QuizQuestion) {
        _quizBase64Questions.value += base64QuizQuestion
    }

    fun saveQuiz(userId: Int, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            val base64Quiz = Base64Quiz(
                name = quizName.value,
                userId = userId,
                base64Image = base64Image.value,
                questions = quizBase64Questions.value
            )
            quizApi.saveQuiz(base64Quiz)
            onSuccess()
        }
    }
}