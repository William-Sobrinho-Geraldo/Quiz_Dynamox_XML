package com.example.teste_dynamox.src.activities.telas

sealed class TelasRoute (val route : String){
    object TelaDeLogin : TelasRoute(route = "tela_de_login")
    object TelaDeQuestoes : TelasRoute(route = "tela_de_questoes")
    object TelaDeResultado : TelasRoute(route = "tela_de_resultado")
}
