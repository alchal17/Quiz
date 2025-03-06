package com.example.quiz.models.request_representation

import com.example.quiz.models.Model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Base64QuizQuestion(
    override val id: Int? = null,
    @SerialName("quiz_id")
    val quizId: Int,
    val text: String,
    @SerialName("base64_image")
    val base64Image: String?,
    @SerialName("multiple_choices")
    val multipleChoices: Boolean,
    @SerialName("seconds_to_answer")
    val secondsToAnswer: Int,
    @SerialName("oder_number")
    val orderNumber: Int
) : Model
