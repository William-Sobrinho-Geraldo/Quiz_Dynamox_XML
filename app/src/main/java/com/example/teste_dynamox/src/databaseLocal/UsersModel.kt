package com.example.teste_dynamox.src.databaseLocal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabela_de_usuarios")
data class Users(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userName: String,
    val dataDoJogo: String = "",
    val quantDeAcertos: Int = 0,
    val quantDeErros: Int = 0
)

