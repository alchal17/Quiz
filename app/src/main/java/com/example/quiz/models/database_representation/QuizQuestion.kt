package com.example.quiz.models.database_representation

import com.example.quiz.models.Model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestion(
    override val id: Int? = null,
    @SerialName("quiz_id")
    val quizId: Int,
    val text: String,
    @SerialName("image_path")
    val imagePath: String?,
    @SerialName("multiple_choices")
    val multipleChoices: Boolean,
    @SerialName("seconds_to_answer")
    val secondsToAnswer: Int,
    @SerialName("oder_number")
    val orderNumber: Int
) : Model