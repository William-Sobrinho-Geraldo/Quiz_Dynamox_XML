package com.example.teste_dynamox.src.api

import com.google.gson.annotations.SerializedName

data class QuizModel(
    val id: String?,
    val statement: String?,
    val options: MutableList<String>,
)

data class ServerResponse(
    @SerializedName("result")
    val result: Boolean
)

data class AnswerRequest(
    val answer: String
)
