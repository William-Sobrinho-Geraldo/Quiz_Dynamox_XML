package com.example.teste_dynamox.src.databaseLocal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface UsersDao {
    @Insert(onConflict = REPLACE)
    suspend fun inserirNovoUsuario(user: Users)

    @Query("SELECT * FROM tabela_de_usuarios")
    suspend fun buscaTodosUsuarios() : List<Users>
}