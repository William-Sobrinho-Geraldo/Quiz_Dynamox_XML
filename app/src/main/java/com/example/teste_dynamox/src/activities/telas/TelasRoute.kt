package com.example.teste_dynamox.src.activities.telas

sealed class TelasRoute(val route: String) {
    object TelaDeLogin : TelasRoute(route = "tela_de_login")
    object TelaDeQuestoes : TelasRoute(route = "tela_de_questoes")
    object TelaDeResultado : TelasRoute(route = "tela_de_resultado")
    object TelaDeCadastroDeUsuario : TelasRoute(route = "tela_de_cadastro_de_usuario")
    object TelaHistoricoDoUsuario : TelasRoute(route = "tela_historico_do_usuario")
}
