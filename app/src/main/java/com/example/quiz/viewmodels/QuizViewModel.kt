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
    private val _quizQuestions = MutableStateFlow<List<Base64QuizQuestion>>(emptyList())
    val quizQuestions: StateFlow<List<Base64QuizQuestion>> = _quizQuestions.asStateFlow()

    fun saveQuiz(quiz: Base64Quiz, onSuccess: () -> Unit) {
        viewModelScope.launch {
            quizApi.saveQuiz(quiz)
            onSuccess()
        }
    }
}