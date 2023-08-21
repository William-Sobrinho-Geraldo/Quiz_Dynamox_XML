package com.example.teste_dynamox.src.repository

import com.example.teste_dynamox.src.api.QuizModel
import com.example.teste_dynamox.src.api.ServicesApi
import com.example.teste_dynamox.src.databaseLocal.JogosDao
import com.example.teste_dynamox.src.databaseLocal.Users
import com.example.teste_dynamox.src.databaseLocal.UsersDao
import com.example.teste_dynamox.src.databaseLocal.jogosDosUsuaios
import retrofit2.Response


class Repository(
    val usersDao: UsersDao,
    val jogosDao: JogosDao,
    val ServicesApi: ServicesApi
) {
    suspend fun buscaIdPeloUserNameRepository(userNameDigitadoPeloUsuario: String): Long? {
        return usersDao.buscaIdPeloUserName(userNameDigitadoPeloUsuario)
    }

    suspend fun getPerguntaRepository(): Response<QuizModel> {
        return ServicesApi.getPergunta()
    }

    suspend fun buscaTodosUsuariosRepository(): List<Users> {
        return usersDao.buscaTodosUsuarios()
    }

    suspend fun buscaTodosUsuarios(): List<Users> {
        return usersDao.buscaTodosUsuarios()
    }

    suspend fun inserirJogo(jogo: jogosDosUsuaios) {
        return jogosDao.inserirJogo(jogo)
    }

    suspend fun inserirNovoUsuario(user: Users): Long? {
        return usersDao.inserirNovoUsuario(user)
    }
}