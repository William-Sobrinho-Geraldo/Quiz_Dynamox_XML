package com.example.teste_dynamox.src.activities.telas

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teste_dynamox.src.api.ApiService
import com.example.teste_dynamox.src.api.optionss
import com.example.teste_dynamox.src.api.statement
import com.example.teste_dynamox.src.util.mostrarToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDeQuestoes(navController: NavController, context: Context) {
    val modifierCard: Modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .clip(RoundedCornerShape(16.dp))
        .padding(horizontal = 8.dp)

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
    Column(Modifier.padding(12.dp)) {
        Text(
            text = "Pergunta: $statement",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Card(modifier = modifierCard,
            onClick = { mostrarToast("${optionss!!.get(0)}", context = context) }) {
            Text(text = "A) ${optionss!!.get(0)}", modifier = Modifier.padding(16.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Card(modifier = modifierCard,
            onClick = { mostrarToast("${optionss!!.get(1)}", context = context) }) {
            Text(text = "B) ${optionss!!.get(1)}", modifier = Modifier.padding(16.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = modifierCard,
            onClick = { mostrarToast("${optionss!!.get(2)}", context = context) }) {
            Text(text = "C) ${optionss!!.get(2)}", modifier = Modifier.padding(16.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))

        Card(modifier = modifierCard,
            onClick = { mostrarToast("${optionss!!.get(3)}", context = context) }) {
            Text(text = "D) ${optionss!!.get(3)}", modifier = Modifier.padding(16.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))

        Card(modifier = modifierCard,
            onClick = { mostrarToast("${optionss!!.get(4)}", context = context) }) {
            Text(text = "E) ${optionss!!.get(4)}", modifier = Modifier.padding(16.dp))
        }
        Spacer(modifier = Modifier.height(65.dp))


        Button(onClick = {
            atualizarPagina()
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Próximo!", fontSize = 20.sp)
        }
    }
}
