package com.example.teste_dynamox.src.databaseLocal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface JogosDao {

    @Insert
    suspend fun inserirJogo(jogo: jogosDosUsuaios)

    @Query("SELECT COUNT(*) FROM tabela_de_jogos_dos_usuarios WHERE userId = :userId")
    suspend fun buscarQuantTotalDeJogosPorID (userId : Long?) : Int


    @Delete
    suspend fun deletarJogos(jogoDoUsuario: jogosDosUsuaios)

}