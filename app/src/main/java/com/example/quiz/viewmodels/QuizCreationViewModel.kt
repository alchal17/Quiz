package com.example.quiz.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.api.QuizApi
import com.example.quiz.models.database_representation.QuizQuestionOption
import com.example.quiz.models.request_representation.Base64Quiz
import com.example.quiz.models.request_representation.Base64QuizQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizCreationViewModel(private val quizApi: QuizApi) : ViewModel() {
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

    fun setImage(newBase64: String?) {
        _base64Image.value = newBase64
    }

    fun addEmptyBase64Question() {
        val emptyBase64QuizQuestion = Base64QuizQuestion(
            text = "",
            base64Image = null,
            multipleChoices = false,
            options = List(2) { QuizQuestionOption(text = "", isCorrect = false) },
            secondsToAnswer = 10
        )
        _base64QuizQuestions.value += emptyBase64QuizQuestion
    }

    fun addEmptyQuestionOption(questionIndex: Int) {
        val emptyOption = QuizQuestionOption(text = "", isCorrect = false)
        val question = _base64QuizQuestions.value[questionIndex]
        val optionListWithNewOption = question.options.toMutableList().apply { add(emptyOption) }
        editBase64Question(questionIndex, question.copy(options = optionListWithNewOption))
    }

    fun editQuestionOption(
        questionIndex: Int,
        optionIndex: Int,
        updatedOption: QuizQuestionOption
    ) {
        val question = _base64QuizQuestions.value[questionIndex]
        val updatedOptionArray = question.options.toTypedArray()
        updatedOptionArray[optionIndex] = updatedOption
        val updatedQuestion = question.copy(options = updatedOptionArray.toList())
        editBase64Question(questionIndex, updatedQuestion)

    }

    fun deleteQuestionOption(questionIndex: Int, optionIndex: Int) {
        val quizQuestion = _base64QuizQuestions.value[questionIndex]
        val options = quizQuestion.options
        val editedOptions = options.toMutableList().apply { removeAt(optionIndex) }
        val editedQuestion = quizQuestion.copy(options = editedOptions)
        editBase64Question(questionIndex, editedQuestion)
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
                description = _quizDescription.value,
                questions = base64QuizQuestions.value
            )
            quizApi.saveQuiz(base64Quiz)
            onSuccess()
        }
    }
}