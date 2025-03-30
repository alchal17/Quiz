package com.example.quiz.viewmodels

import androidx.lifecycle.ViewModel
import com.example.quiz.api.ApiResponse
import com.example.quiz.api.reading.QuizQuestionOptionsReadingAPI
import com.example.quiz.api.writing.QuizQuestionOptionsWritingAPI
import com.example.quiz.models.database_representation.QuizQuestionOption

class QuizQuestionOptionViewModel(
    private val readingAPI: QuizQuestionOptionsReadingAPI,
    private val writingAPI: QuizQuestionOptionsWritingAPI
) :
    ViewModel() {

    suspend fun findAllByQuizQuestionId(id: Int): ApiResponse<List<QuizQuestionOption>> {
        return readingAPI.findAllByQuizQuestionId(id)
    }

    suspend fun getById(id: Int): ApiResponse<QuizQuestionOption> {
        return readingAPI.getById(id)
    }

    suspend fun createQuizQuestionOption(quizQuestionOption: QuizQuestionOption): ApiResponse<Int> {
        return writingAPI.create(quizQuestionOption)
    }

    suspend fun createMultiple(options: List<QuizQuestionOption>): ApiResponse<List<Int>> {
        return writingAPI.createMultiple(options)
    }

    suspend fun updateQuizQuestionOption(
        optionId: Int,
        option: QuizQuestionOption
    ): ApiResponse<String> {
        return writingAPI.update(option, optionId)
    }

    suspend fun replaceQuestionOptions(
        questionId: Int,
        options: List<QuizQuestionOption>
    ): ApiResponse<String> {
        return writingAPI.replaceQuestionOptions(questionId, options)
    }

    suspend fun deleteQuizQuestionOption(id: Int): ApiResponse<String> {
        return writingAPI.deleteById(id)
    }
}