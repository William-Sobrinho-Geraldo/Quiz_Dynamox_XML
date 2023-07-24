package com.example.teste_dynamox.src.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://quiz-api-bwi5hjqyaq-uc.a.run.app"

object ApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val quizApi: QuizApi = retrofit.create(QuizApi::class.java)
}