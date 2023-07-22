package com.example.teste_dynamox.src.activities.telas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TelaDeQuestoes(statement: String?, options: List<String>) {
    println("NA TelaDeQuestoes: options é: $optionss")
    // Conteúdo da tela de questões aqui
    // Exibe a pergunta e as opções recebidas da API
    Column() {
        Text(text = "a pergunta é: $statement", modifier = Modifier.padding(16.dp))
        val firstOption: String = optionss!!.get(0)
        Text(text = "A) ${optionss!!.get(0)}", modifier = Modifier.padding(16.dp))
        Text(text = "B) ${optionss!!.get(1)}", modifier = Modifier.padding(16.dp))
        Text(text = "C) ${optionss!!.get(2)}", modifier = Modifier.padding(16.dp))
        Text(text = "D) ${optionss!!.get(3)}", modifier = Modifier.padding(16.dp))



        Text(
            "Bem vindo a tela de questões",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 60.dp)
        )
    }

}
