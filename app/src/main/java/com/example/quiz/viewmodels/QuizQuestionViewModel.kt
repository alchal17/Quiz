package com.example.quiz.viewmodels

import androidx.lifecycle.ViewModel
import com.example.quiz.api.ApiResponse
import com.example.quiz.api.reading.Base64QuizQuestionOptionsReadingAPI
import com.example.quiz.api.writing.Base64QuizQuestionOptionsWritingAPI
import com.example.quiz.models.request_representation.Base64QuizQuestion

class QuizQuestionViewModel(
    private val base64QuizQuestionOptionsReadingAPI: Base64QuizQuestionOptionsReadingAPI,
    private val base64QuizQuestionOptionsWritingAPI: Base64QuizQuestionOptionsWritingAPI
) : ViewModel() {

    suspend fun getById(questionId: Int): ApiResponse<Base64QuizQuestion> {
        return base64QuizQuestionOptionsReadingAPI.getById(questionId)
    }

    suspend fun createQuizQuestion(base64QuizQuestion: Base64QuizQuestion): ApiResponse<Int> {
        return base64QuizQuestionOptionsWritingAPI.create(base64QuizQuestion)
    }

    suspend fun updateQuizQuestion(
        questionId: Int,
        base64QuizQuestion: Base64QuizQuestion
    ): ApiResponse<String> {
        return base64QuizQuestionOptionsWritingAPI.update(base64QuizQuestion, questionId)
    }

    suspend fun deleteQuizQuestion(questionId: Int): ApiResponse<String> {
        return base64QuizQuestionOptionsWritingAPI.deleteById(questionId)
    }
}