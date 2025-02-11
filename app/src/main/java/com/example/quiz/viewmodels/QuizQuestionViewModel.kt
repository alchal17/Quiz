package com.example.quiz.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.api.ApiResponse
import com.example.quiz.api.QuizQuestionsAPI
import com.example.quiz.models.database_representation.QuizQuestion
import com.example.quiz.models.request_representation.Base64QuizQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizQuestionViewModel(private val quizQuestionsAPI: QuizQuestionsAPI) : ViewModel() {
    private val _text: MutableStateFlow<String> = MutableStateFlow("")
    val text: StateFlow<String> = _text.asStateFlow()

    private val _base64Image: MutableStateFlow<String?> = MutableStateFlow(null)
    val base64Image: StateFlow<String?> = _base64Image.asStateFlow()

    private val _multipleChoices: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val multipleChoices: StateFlow<Boolean> = _multipleChoices.asStateFlow()

    private val _secondsToAnswer: MutableStateFlow<Int> = MutableStateFlow(15)
    val secondsToAnswer: StateFlow<Int> = _secondsToAnswer.asStateFlow()

    private val _quizQuestionsQuery: MutableStateFlow<List<QuizQuestion>> = MutableStateFlow(
        emptyList()
    )
    val quizQuestionsQuery: StateFlow<List<QuizQuestion>> = _quizQuestionsQuery.asStateFlow()


    fun setText(newText: String) {
        _text.value = newText
    }

    fun setImage(newBase64Image: String?) {
        _base64Image.value = newBase64Image
    }

    fun setMultipleChoices(value: Boolean) {
        _multipleChoices.value = value
    }

    fun setSecondsToAnswer(seconds: Int) {
        _secondsToAnswer.value = seconds
    }


    fun getById(questionId: Int) {
        viewModelScope.launch {
            when (val apiResponse = quizQuestionsAPI.getById(questionId)) {
                is ApiResponse.Error -> TODO(
                    "Handle error case when not finding a quiz" +
                            "question by id"
                )

                is ApiResponse.Success -> {
                    val base64QuizQuestion = apiResponse.data

                    setText(base64QuizQuestion.text)
                    setImage(base64QuizQuestion.base64Image)
                    setMultipleChoices(base64QuizQuestion.multipleChoices)
                    setSecondsToAnswer(base64QuizQuestion.secondsToAnswer)
                }
            }
        }
    }

    suspend fun createQuizQuestion(quizId: Int): ApiResponse<Int> {
        val base64QuizQuestion = Base64QuizQuestion(
            id = null,
            quizId = quizId,
            text = _text.value,
            base64Image = _base64Image.value,
            multipleChoices = _multipleChoices.value,
            secondsToAnswer = _secondsToAnswer.value
        )
        return quizQuestionsAPI.create(base64QuizQuestion)
    }

    suspend fun updateQuizQuestion(questionId: Int, quizId: Int): ApiResponse<String> {
        val base64QuizQuestion = Base64QuizQuestion(
            id = questionId,
            quizId = quizId,
            text = _text.value,
            base64Image = _base64Image.value,
            multipleChoices = _multipleChoices.value,
            secondsToAnswer = _secondsToAnswer.value
        )
        return quizQuestionsAPI.update(base64QuizQuestion, quizId)
    }

    suspend fun deleteQuizQuestion(questionId: Int): ApiResponse<String> {
        return quizQuestionsAPI.deleteById(questionId)
    }
}