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

    private val _quizDescription = MutableStateFlow("")
    var quizDescription: StateFlow<String> = _quizDescription.asStateFlow()

    private val _base64Image = MutableStateFlow<String?>(null)
    val base64Image: StateFlow<String?> = _base64Image.asStateFlow()

    private val _base64QuizQuestions = MutableStateFlow<List<Base64QuizQuestion>>(emptyList())
    val base64QuizQuestions: StateFlow<List<Base64QuizQuestion>> =
        _base64QuizQuestions.asStateFlow()

    fun setName(newValue: String) {
        _quizName.value = newValue
    }

    fun setDescription(newValue: String) {
        _quizDescription.value = newValue
    }

    fun setImage(newBase64: String) {
        _base64Image.value = newBase64
    }

    fun addBase64Question() {
        val emptyBase64QuizQuestion = Base64QuizQuestion(
            text = "",
            base64Image = null,
            multipleChoices = false,
            options = emptyList()
        )
        _base64QuizQuestions.value += emptyBase64QuizQuestion
    }


    fun editBase64Question(index: Int, editedQuestion: Base64QuizQuestion) {
        val questionsCopy =
            _base64QuizQuestions.value.toTypedArray().apply { this[index] = editedQuestion }
        _base64QuizQuestions.value = questionsCopy.toList()
    }

    fun saveQuiz(userId: Int, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            val base64Quiz = Base64Quiz(
                name = quizName.value,
                userId = userId,
                base64Image = base64Image.value,
                questions = base64QuizQuestions.value
            )
            quizApi.saveQuiz(base64Quiz)
            onSuccess()
        }
    }
}