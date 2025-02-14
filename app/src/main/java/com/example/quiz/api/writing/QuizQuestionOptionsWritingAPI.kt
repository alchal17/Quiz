package com.example.quiz.api.writing

import com.example.quiz.models.database_representation.QuizQuestionOption
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer

class QuizQuestionOptionsWritingAPI(override val client: HttpClient) :
    WritingAPI<QuizQuestionOption>() {
    override val currentRoute: String
        get() = "/quiz_question_option"

    override val serializer: KSerializer<QuizQuestionOption>
        get() = QuizQuestionOption.serializer()
}