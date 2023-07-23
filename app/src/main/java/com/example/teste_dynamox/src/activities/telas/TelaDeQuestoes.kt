package com.example.teste_dynamox.src.activities.telas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teste_dynamox.src.api.ApiService
import com.example.teste_dynamox.src.api.optionss
import com.example.teste_dynamox.src.api.statement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun TelaDeQuestoes(navController: NavController) {

    var requisicaoCompleta by remember { mutableStateOf(false) }
    LaunchedEffect(requisicaoCompleta) {
        if (requisicaoCompleta) {
            navController.navigate("tela_de_questoes/{statement}")
        }
    }

    fun atualizarPagina() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = ApiService.quizApi.getPergunta()
                if (response.isSuccessful) {
                    val quizResponse = response.body()
                    statement = quizResponse?.statement
                    optionss = quizResponse?.options
                    quizResponse.let { println("Statement e optionss são:  ${statement}   E   $optionss") }
                }
            } catch (e: Exception) {
                println("O erro foi $e")
            }
            requisicaoCompleta = true
        }
    }
    // Conteúdo da tela de questões aqui
    // Exibe a pergunta e as opções recebidas da API
    Column() {
        Text(text = "a pergunta é: $statement", modifier = Modifier.padding(16.dp))
        val firstOption: String = optionss!!.get(0)
        Text(text = "A) ${optionss!!.get(0)}", modifier = Modifier.padding(16.dp))
        Text(text = "B) ${optionss!!.get(1)}", modifier = Modifier.padding(16.dp))
        Text(text = "C) ${optionss!!.get(2)}", modifier = Modifier.padding(16.dp))
        Text(text = "D) ${optionss!!.get(3)}", modifier = Modifier.padding(16.dp))
        Text(text = "E) ${optionss!!.get(4)}", modifier = Modifier.padding(16.dp))

        Text(
            "Bem vindo a tela de questões",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 60.dp)
        )

        Button(onClick = {
            atualizarPagina()
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Refresh!", fontSize = 20.sp)
        }
    }

}
