package com.example.quiz.api

import com.example.quiz.apiPath
import com.example.quiz.models.Model
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

abstract class BaseAPI<T : @Serializable Model> {

    protected abstract val client: HttpClient

    protected val serverPath = apiPath

    protected abstract val currentRoute: String

    protected abstract val serializer: KSerializer<T>

}