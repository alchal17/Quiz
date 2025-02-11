package com.example.quiz.models.database_representation

import com.example.quiz.models.Model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestionOption(
    override val id: Int? = null,
    val text: String,
    @SerialName("is_correct")
    val isCorrect: Boolean,
    @SerialName("quiz_question_id")
    val quizQuestionId: Int,
) : Model
