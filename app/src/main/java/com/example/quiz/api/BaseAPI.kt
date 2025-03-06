package com.example.quiz.api

import com.example.quiz.API_PATH
import com.example.quiz.models.Model
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

abstract class BaseAPI<T : @Serializable Model> {

    protected abstract val client: HttpClient

    protected val serverPath = API_PATH

    protected abstract val currentRoute: String

    protected abstract val serializer: KSerializer<T>

    protected val listSerializer = ListSerializer(serializer)

}