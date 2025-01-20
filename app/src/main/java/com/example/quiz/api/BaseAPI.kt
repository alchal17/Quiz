package com.example.quiz.api

import com.example.quiz.apiPath
import io.ktor.client.HttpClient

abstract class BaseAPI(protected val client: HttpClient) {
    protected val serverPath = apiPath
}