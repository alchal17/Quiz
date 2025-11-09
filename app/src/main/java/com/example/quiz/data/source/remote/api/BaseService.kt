package com.example.quiz.data.source.remote.api

import com.example.quiz.API_PATH
import io.ktor.client.HttpClient

abstract class BaseService {

    protected abstract val client: HttpClient

    companion object {
        protected const val SERVER_PATH = API_PATH
    }
}