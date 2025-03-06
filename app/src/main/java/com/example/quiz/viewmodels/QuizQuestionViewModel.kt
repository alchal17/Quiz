package com.example.quiz.viewmodels

import androidx.lifecycle.ViewModel
import com.example.quiz.api.ApiResponse
import com.example.quiz.api.reading.Base64QuizQuestionReadingAPI
import com.example.quiz.api.reading.QuizQuestionReadingAPI
import com.example.quiz.api.writing.Base64QuizQuestionWritingAPI
import com.example.quiz.models.database_representation.QuizQuestion
import com.example.quiz.models.request_representation.Base64QuizQuestion

class QuizQuestionViewModel(
    private val base64QuizQuestionReadingAPI: Base64QuizQuestionReadingAPI,
    private val base64QuizQuestionWritingAPI: Base64QuizQuestionWritingAPI,
    private val quizQuestionReadingAPI: QuizQuestionReadingAPI
) : ViewModel() {

    suspend fun getQuestionsByQuizId(quizId: Int): ApiResponse<List<QuizQuestion>> {
        return quizQuestionReadingAPI.getQuestionsByQuizId(quizId)
    }

    suspend fun getBase64QuestionById(questionId: Int): ApiResponse<Base64QuizQuestion> {
        return base64QuizQuestionReadingAPI.getById(questionId)
    }

    suspend fun createQuizQuestion(base64QuizQuestion: Base64QuizQuestion): ApiResponse<Int> {
        return base64QuizQuestionWritingAPI.create(base64QuizQuestion)
    }

    suspend fun updateQuizQuestion(
        questionId: Int,
        base64QuizQuestion: Base64QuizQuestion
    ): ApiResponse<String> {
        return base64QuizQuestionWritingAPI.update(base64QuizQuestion, questionId)
    }

    suspend fun deleteQuizQuestion(questionId: Int): ApiResponse<String> {
        return base64QuizQuestionWritingAPI.deleteById(questionId)
    }
}