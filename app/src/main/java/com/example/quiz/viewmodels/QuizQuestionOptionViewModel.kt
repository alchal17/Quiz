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


    suspend fun getById(id: Int): ApiResponse<QuizQuestionOption> {
        return readingAPI.getById(id)
    }

    suspend fun createQuizQuestionOption(quizQuestionOption: QuizQuestionOption): ApiResponse<Int> {
        return writingAPI.create(quizQuestionOption)
    }

    suspend fun updateQuizQuestionOption(
        optionId: Int,
        option: QuizQuestionOption
    ): ApiResponse<String> {
        return writingAPI.update(option, optionId)
    }

    suspend fun deleteQuizQuestionOption(id: Int): ApiResponse<String> {
        return writingAPI.deleteById(id)
    }
}