package com.example.teste_dynamox.src.repository

import androidx.lifecycle.MutableLiveData
import com.example.teste_dynamox.src.api.AnswerRequest
import com.example.teste_dynamox.src.api.QuizModel
import com.example.teste_dynamox.src.api.ServerResponse
import com.example.teste_dynamox.src.api.ServicesApi
import com.example.teste_dynamox.src.databaseLocal.JogosDao
import com.example.teste_dynamox.src.databaseLocal.Users
import com.example.teste_dynamox.src.databaseLocal.UsersDao
import com.example.teste_dynamox.src.databaseLocal.jogosDosUsuaios
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Repository(
    val usersDao: UsersDao,
    val jogosDao: JogosDao,
    val servicesApi: ServicesApi
) {
    suspend fun buscaIdPeloUserNameRepository(userNameDigitadoPeloUsuario: String): Long? {
        return usersDao.buscaIdPeloUserName(userNameDigitadoPeloUsuario)
    }

    fun getPerguntaRepository(): Call<QuizModel> {
        return servicesApi.getPergunta()
    }

    suspend fun buscaTodosUsuariosRepository(): List<Users> {
        return usersDao.buscaTodosUsuarios()
    }

    suspend fun inserirJogoRepository(jogo: jogosDosUsuaios) {
        return jogosDao.inserirJogo(jogo)
    }

    suspend fun inserirNovoUsuarioRepository(user: Users) {
        return usersDao.inserirNovoUsuario(user)
    }

    fun checkAnswerRepository(url: String?, answerRequest: AnswerRequest): Call<ServerResponse> {
        return servicesApi.checkAnswer(url,answerRequest)
    }

    suspend fun buscarQuantTotalDeJogosPorIDRepository(userId: Long?): Int {
        return jogosDao.buscarQuantTotalDeJogosPorID(userId)
    }

    suspend fun buscaQuantCertasPorUserIdRepository(userId: Long?): Collection<Long> {
        return  jogosDao.buscaQuantCertasPorUserId(userId)
    }

    suspend fun buscaQuantDeErrosPorUserIdRepository(userId: Long?): Collection<Long> {
        return jogosDao.buscaQuantDeErrosPorUserId(userId)
    }

    suspend fun verificarSeUserNameEstaCadastrado(userName: String): Boolean {
        return withContext(Dispatchers.IO) {
            usersDao.existeUserNameNoBancoLocal (userName)
        }
    }

    suspend fun buscaUsuarioLogadoPeloUsername(userNameLogado: String): Users {
        return usersDao.buscaUsuarioLogadoPeloUserName(userNameLogado)
    }

}