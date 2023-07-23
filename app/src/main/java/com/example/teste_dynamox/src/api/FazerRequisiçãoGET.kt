package com.example.teste_dynamox.src.api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

var statement: String? = null
var optionss: MutableList<String>? = mutableListOf("", "1")


//fun fazerRequisicaoENavegar( isApiRequestCompleted : Boolean = false) {
//    // Use a Coroutine para fazer a chamada da API na thread IO
//    GlobalScope.launch(Dispatchers.IO) {
//        try {
//            val response = ApiService.quizApi.getPergunta()
//            if (response.isSuccessful) {
//                val quizResponse = response.body()
//                statement = quizResponse?.statement
//                optionss = quizResponse?.options
//                println("APÓS O GET -> O statement é : $statement    e options é : $optionss")
//
//            } else {
//                // Lida com erros da resposta da API
//            }
//        } catch (e: Exception) {
//            // Lida com erros da requisição
//        }
//
//    }
//
//}
