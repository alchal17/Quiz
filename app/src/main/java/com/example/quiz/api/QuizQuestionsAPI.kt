package com.example.quiz.api

import com.example.quiz.models.request_representation.Base64QuizQuestion
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer

class QuizQuestionsAPI(client: HttpClient): BaseAPI<Base64QuizQuestion>(client) {
    override val currentRoute: String
        get() = "/quiz_question"

    override val serializer: KSerializer<Base64QuizQuestion>
        get() = Base64QuizQuestion.serializer()


}