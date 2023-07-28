package com.example.teste_dynamox.src.databaseLocal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface JogosDao {

    @Insert
    suspend fun inserirJogo(jogo: jogosDosUsuaios)

//    @Query("SELECT * FROM tabela_de_jogos_dos_usuarios WHERE id= :id")
//    suspend fun buscarJogosDoUsuarioPorId(
//        id: Long
//    ) : List<jogosDosUsuaios>

    @Delete
    suspend fun deletarJogos(jogoDoUsuario: jogosDosUsuaios)

}