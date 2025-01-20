package com.example.quiz.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.api.QuizApi
import com.example.quiz.models.database_representation.Quiz
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizReadingViewModel(private val quizApi: QuizApi) : ViewModel() {
    private val _quizzes = MutableStateFlow<List<Quiz>>(emptyList())

    val quizzes = _quizzes.asStateFlow()

    fun getAll() {
        viewModelScope.launch {
            _quizzes.value = quizApi.getAll()
        }
    }
}