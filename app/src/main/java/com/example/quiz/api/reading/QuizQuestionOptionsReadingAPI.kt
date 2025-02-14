package com.example.quiz.api.reading

import com.example.quiz.models.database_representation.QuizQuestionOption
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer

class QuizQuestionOptionsReadingAPI(override val client: HttpClient) :
    ReadingAPI<QuizQuestionOption>() {
    override val currentRoute: String
        get() = "/quiz_question_option"

    override val serializer: KSerializer<QuizQuestionOption>
        get() = QuizQuestionOption.serializer()
}