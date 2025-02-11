package com.example.quiz.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.api.ApiResponse
import com.example.quiz.api.QuizQuestionOptionsAPI
import com.example.quiz.models.database_representation.QuizQuestionOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizQuestionOptionViewModel(private val questionOptionsAPI: QuizQuestionOptionsAPI) :
    ViewModel() {
    private val _text: MutableStateFlow<String> = MutableStateFlow("")
    val text: StateFlow<String> = _text.asStateFlow()

    private val _isCorrect: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isCorrect: StateFlow<Boolean> = _isCorrect.asStateFlow()

    fun setText(newText: String) {
        _text.value = newText
    }

    fun setCorrect(value: Boolean) {
        _isCorrect.value = value
    }


    fun getById(id: Int) {
        viewModelScope.launch {
            when (val apiResponse = questionOptionsAPI.getById(id)) {
                is ApiResponse.Error -> TODO(
                    "Handle error case when not finding a question " +
                            "option by id"
                )

                is ApiResponse.Success -> {
                    val option = apiResponse.data
                    setText(option.text)
                    setCorrect(option.isCorrect)
                }
            }
        }
    }

    suspend fun createQuizQuestionOption(quizQuestionId: Int): ApiResponse<Int> {
        val quizQuestionOption = QuizQuestionOption(
            id = null,
            text = _text.value,
            isCorrect = _isCorrect.value,
            quizQuestionId = quizQuestionId
        )
        return questionOptionsAPI.create(quizQuestionOption)
    }

    suspend fun updateQuizQuestionOption(optionId: Int, questionId: Int): ApiResponse<String> {
        val quizQuestionOption = QuizQuestionOption(
            id = optionId,
            text = _text.value,
            isCorrect = _isCorrect.value,
            quizQuestionId = questionId
        )
        return questionOptionsAPI.update(quizQuestionOption, questionId)
    }

    suspend fun deleteQuizQuestionOption(id: Int): ApiResponse<String> {
        return questionOptionsAPI.deleteById(id)
    }
}