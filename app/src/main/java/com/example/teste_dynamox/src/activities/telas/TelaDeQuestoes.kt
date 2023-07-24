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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teste_dynamox.src.api.AnswerRequest
import com.example.teste_dynamox.src.api.ApiService
import com.example.teste_dynamox.src.api.ServerResponse
import com.example.teste_dynamox.src.util.mostrarToast
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var numeroDaPergunta = 1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDeQuestoes(navController: NavController, context: Context) {
    val modifierCard: Modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .clip(RoundedCornerShape(16.dp))
        .padding(horizontal = 8.dp)


    var respostaCerta by remember { mutableStateOf(10) }

    var alternativaEscolhida: Int? = null
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
                    id = quizResponse?.id
                    quizResponse.let { println("TelaDeQuestões => O ID: $id Statement: $statement e optionss: $optionss") }
                }
            } catch (e: Exception) {
                println("O erro foi $e")
            }
            requisicaoCompleta = true
        }
    }


    fun checkAnswer(url: String?, userAnswer: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val answerRequest = AnswerRequest(answer = userAnswer)
            val requestBody =
                RequestBody.create("application/json".toMediaType(), Gson().toJson(answerRequest))
            println("requestBody é:  $requestBody")
            val call = ApiService.quizApi.checkAnswer(url, answerRequest)

            call.enqueue(object : Callback<ServerResponse> {
                override fun onResponse(
                    call: Call<ServerResponse>,
                    response: Response<ServerResponse>
                ) {
                    if (response.isSuccessful) {
                        val serverResponse = response.body()
                        println("A resposta do servidor foi: $serverResponse")

                        if (serverResponse == ServerResponse(result = true)) {
                            if (alternativaEscolhida == 0) {
                                respostaCerta = 0
                            }
                            if (alternativaEscolhida == 1) {
                                respostaCerta = 1
                            }
                            if (alternativaEscolhida == 2) {
                                respostaCerta = 2
                            }
                            if (alternativaEscolhida == 3) {
                                respostaCerta = 3
                            }
                            if (alternativaEscolhida == 4) {
                                respostaCerta = 4
                            }
                            mostrarToast(
                                "CERTO -> respostaCerta é $respostaCerta",
                                context = context
                            )
                            println("a Alternativa escolhida foi: $alternativaEscolhida")
                        } else {
                            respostaCerta = 10
                            mostrarToast(
                                "ERRADO -> respostaCerta é $respostaCerta",
                                context = context
                            )
                        }
                    } else {
                        println("deu ruim")
                    }
                }

                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

    // Exibe a pergunta e as opções recebidas da API
    Column(Modifier.padding(12.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Pergunta $numeroDaPergunta de 10: $statement",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))


        for (index in 0..4) {
            val backgroundColor = if (index == respostaCerta) {
                CardDefaults.cardColors(containerColor = Color.Green)
            } else {
                CardDefaults.cardColors(containerColor = Color.LightGray)
            }

            Card(
                colors = backgroundColor,
                modifier = modifierCard,
                onClick = {
                    checkAnswer(
                        url = "https://quiz-api-bwi5hjqyaq-uc.a.run.app/answer?questionId=$id",
                        userAnswer = optionss!![index]
                    )
                    alternativaEscolhida = index
                }
            ) {
                Text(
                    text = "${'A' + index}) ${optionss!![index]}",
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(65.dp))

        Button(
            onClick = {
                if (numeroDaPergunta < 10) {
                    atualizarPagina()
                    numeroDaPergunta++
                } else { navController.navigate("tela_de_resultado") }

            }, modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.extraLarge)
        ) {
            Text(text = "Próximo!", fontSize = 20.sp)
        }
    }
}

