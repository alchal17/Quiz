package com.example.quiz.viewmodels

import androidx.lifecycle.ViewModel
import com.example.quiz.api.ApiResponse
import com.example.quiz.api.reading.Base64QuizReadingAPI
import com.example.quiz.api.reading.QuizReadingAPI
import com.example.quiz.api.writing.Base64QuizWritingAPI
import com.example.quiz.models.database_representation.Quiz
import com.example.quiz.models.request_representation.Base64Quiz

class QuizViewModel(
    private val base64QuizWritingAPI: Base64QuizWritingAPI,
    private val base64QuizReadingAPI: Base64QuizReadingAPI,
    private val quizReadingAPI: QuizReadingAPI
) :
    ViewModel() {


    suspend fun getBase64QuizById(id: Int): ApiResponse<Base64Quiz> {
        return base64QuizReadingAPI.getById(id)
    }

    suspend fun getQuizById(id: Int): ApiResponse<Quiz> {
        return quizReadingAPI.getById(id)
    }

    suspend fun getAllQuizzes(): ApiResponse<List<Quiz>> {
        return quizReadingAPI.getAll()
    }

    suspend fun createBase64Quiz(quiz: Base64Quiz): ApiResponse<Int> {
        return base64QuizWritingAPI.create(quiz)
    }

    suspend fun updateQuiz(quizId: Int, quiz: Base64Quiz): ApiResponse<String> {
        return base64QuizWritingAPI.update(quiz, quizId)
    }

    suspend fun deleteQuiz(quizId: Int): ApiResponse<String> {
        return base64QuizWritingAPI.deleteById(quizId)
    }

    suspend fun getByUserId(userId: Int): ApiResponse<List<Quiz>> {
        return quizReadingAPI.getByUserId(userId)
    }

    suspend fun getQuestionsNumber(quizId: Int):ApiResponse<Int>{
        return quizReadingAPI.getQuestionsNumber(quizId)
    }
}