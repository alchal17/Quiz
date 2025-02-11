package com.example.quiz.api

import com.example.quiz.models.database_representation.QuizQuestionOption
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer

class QuizQuestionOptionsAPI(client: HttpClient): BaseAPI<QuizQuestionOption>(client) {
    override val currentRoute: String
        get() = "/quiz_question_option"

    override val serializer: KSerializer<QuizQuestionOption>
        get() = QuizQuestionOption.serializer()
}