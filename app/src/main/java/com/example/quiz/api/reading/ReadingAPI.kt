package com.example.quiz.api.reading

import com.example.quiz.api.ApiResponse
import com.example.quiz.api.BaseAPI
import com.example.quiz.models.Model
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

abstract class ReadingAPI<T : @Serializable Model> : BaseAPI<T>() {
    open suspend fun getAll(): ApiResponse<List<T>> {
        val url = "$serverPath$currentRoute/all"
        return try {
            val result = client.get(url)
            if (result.status == HttpStatusCode.OK) {
                val data = Json.decodeFromString(listSerializer, result.bodyAsText())
                ApiResponse.Success(data = data)
            } else {
                ApiResponse.Error(message = result.bodyAsText())
            }
        } catch (e: Exception) {
            ApiResponse.Error(message = e.message ?: "Unknown error")
        }
    }


    open suspend fun getById(id: Int): ApiResponse<T> {
        val url = "$serverPath$currentRoute/$id"

        return try {
            val result = client.get(url)
            if (result.status == HttpStatusCode.OK) {
                val data = Json.decodeFromString(serializer, result.bodyAsText())
                ApiResponse.Success(data = data)
            } else {
                ApiResponse.Error(message = result.bodyAsText())
            }
        } catch (e: Exception) {
            ApiResponse.Error(message = e.message ?: "Unknown error")
        }
    }
}