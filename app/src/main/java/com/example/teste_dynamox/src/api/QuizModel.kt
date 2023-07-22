package com.example.teste_dynamox.src.api

data class QuizModel (
    val id : String?,
    val statement: String?,
    val options: MutableList<String>,
)
