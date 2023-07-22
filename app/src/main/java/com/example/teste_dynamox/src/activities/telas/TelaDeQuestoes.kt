package com.example.teste_dynamox.src.activities.telas

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TelaDeQuestoes(statement: String?, options: List<String>) {

    // Conteúdo da tela de questões aqui
        // Exibe a pergunta e as opções recebidas da API
        Text(text = "a pergunta é: $statement")
        options.forEach { option ->
            Text(text = option)
        }


    Text("Bem vindo a tela de questões", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 60.dp))
}
