package com.example.teste_dynamox.src.api

import retrofit2.Response
import retrofit2.http.GET

interface QuizApi {
    @GET(BASE_URL)
    suspend fun getPergunta(): Response<QuizModel>
}