package com.example.teste_dynamox.src.databaseLocal

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tabela_de_jogos_dos_usuarios",
    foreignKeys = [ForeignKey(entity = Users::class, parentColumns = ["id"], childColumns = ["userId"], onDelete = ForeignKey.CASCADE)]
    )
data class jogosDosUsuaios(
    @PrimaryKey(autoGenerate = true) val jogoId: Long = 0,
    var userId : Long?,
    val dataDoJogo: Long = 0,
    val quantDeAcertos: Int = 0,
    val quantDeErros: Int = 0
)

