package com.example.teste_dynamox.src.repository

import com.example.teste_dynamox.src.api.QuizModel
import com.example.teste_dynamox.src.api.ServicesApi
import com.example.teste_dynamox.src.databaseLocal.Users
import com.example.teste_dynamox.src.databaseLocal.UsersDao
import retrofit2.Response


class Repository(
    val dao : UsersDao,
    val ServicesApi : ServicesApi
) {
    suspend fun buscaIdPeloUserNameRepository(userNameDigitadoPeloUsuario: String): Long? {
        return dao.buscaIdPeloUserName(userNameDigitadoPeloUsuario)
    }

    suspend fun getPerguntaRepository() : Response<QuizModel> {
        return ServicesApi.getPergunta()
    }

    suspend fun buscaTodosUsuariosRepository(): List<Users> {
        return dao.buscaTodosUsuarios()
    }
}