package com.example.teste_dynamox.src.databaseLocal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface UsersDao {
    @Insert(onConflict = REPLACE)
    suspend fun inserirNovoUsuario(user: Users)

    @Query("SELECT * FROM tabela_de_usuarios")
    suspend fun buscaTodosUsuarios(): List<Users>

    @Query("SELECT * FROM tabela_de_usuarios WHERE userName = :userNameLogado")
    suspend fun buscaUsuarioLogadoPeloUserName( userNameLogado : String) : Users

    @Query("SELECT id FROM tabela_de_usuarios WHERE userName = :userName")
    suspend fun buscaIdPeloUserName(userName: String): Long?

    @Query("SELECT COUNT(*) FROM tabela_de_usuarios WHERE userName = :userName")
    suspend fun existeUserNameNoBancoLocal (userName: String) : Boolean

    @Query("SELECT userName FROM tabela_de_usuarios WHERE id = :userId")
    suspend fun buscaUserNamePeloUserId(userId: Long): String

    @Delete
    suspend fun deletarUsuario(user: Users)
}