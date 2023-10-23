package com.example.teste_dynamox.src.activities.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teste_dynamox.src.activities.viewModel.TelaDeLoginViewModel
import com.example.teste_dynamox.src.api.AppRetrofit
import com.example.teste_dynamox.src.databaseLocal.AppDatabase
import com.example.teste_dynamox.src.databaseLocal.jogosDosUsuaios
import com.example.teste_dynamox.src.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun TelaDeResultado(navController: NavController, telaDeLoginViewModelResultados : TelaDeLoginViewModel) {
    var podeReiniciarOQuiz by remember { mutableStateOf(false) }
    var podeMostrarHistorico by remember { mutableStateOf(false) }
    val idUserLogado = telaDeLoginViewModelResultados.userNameLogado.collectAsState().value.id


    val repository = Repository(
        AppDatabase.getDatabase(LocalContext.current).userDao(),
        AppDatabase.getDatabase(LocalContext.current).jogosDao(),
        AppRetrofit.ServicesApi
    )

    //Reiniciar o quiz caso o botão reiniciar quiz seja clicado
    LaunchedEffect(podeReiniciarOQuiz) {
        if (podeReiniciarOQuiz) {
            navController.navigate("tela_de_questoes/{statement}")
            contadorRespostasCertas = 0
            numeroDaPergunta = 1
        }
    }
    //Navegar para histórico caso o botão Histórico de jogos seja clicado
    LaunchedEffect(podeMostrarHistorico) {
        if (podeMostrarHistorico) {
            navController.navigate("tela_historico_do_usuario")
        }
    }

    fun inserirJogoPeloUserIdEReiniciarOQuiz(userId: Long?, quantDeAcertos: Long, quantDeErros: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.inserirJogoRepository(
                jogosDosUsuaios(
                    userId = userId,
                    quantDeAcertos = quantDeAcertos,
                    quantDeErros = quantDeErros
                )
            )
            podeReiniciarOQuiz = true
        }
    }

    fun inserirJogoPeloUserIdEMostrarHistorico(userId: Long?, quantDeAcertos: Long, quantDeErros: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.inserirJogoRepository(
                jogosDosUsuaios(
                    userId = userId,
                    quantDeAcertos = quantDeAcertos,
                    quantDeErros = quantDeErros
                )
            )
            podeMostrarHistorico = true
            println(
                "A quant de acertos foi $contadorRespostasCertas e " +
                        "de erros foi ${Math.abs(contadorRespostasCertas - 10)}"
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2C2C2C),
                        Color(0xFF44142D),
                        Color(0xFF181818),
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(
            text = "    Obrigado por participar do Quiz Dynamox, seu resultado foi: ",
            fontSize = 30.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(25.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Respostas certas: ", fontSize = 18.sp, color = Color.White)
            Text(
                "$contadorRespostasCertas",
                color = Color.Green,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(25.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            var contadorRespostasErradas = 10 - contadorRespostasCertas
            Text("Respostas erradas: ", fontSize = 18.sp, color = Color.White)
            Text(
                "$contadorRespostasErradas",
                color = Color.Red,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(25.dp),
            )
        }

        Spacer(modifier = Modifier.height(70.dp))

        Button(
            onClick = {
                inserirJogoPeloUserIdEReiniciarOQuiz(
                    userId = idUserLogado,
                    quantDeAcertos = contadorRespostasCertas,
                    quantDeErros = Math.abs(contadorRespostasCertas - 10)
                )

            },
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .padding(horizontal = 26.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.extraLarge)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF76110C),
                            Color(0xFFCC481A),
                            Color(0xFFFEC651),
                        )
                    )
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )

        ) {
            Text("Reiniciar Quiz", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                inserirJogoPeloUserIdEMostrarHistorico(
                    userId = idUserLogado,
                    quantDeAcertos = contadorRespostasCertas,
                    quantDeErros = Math.abs(contadorRespostasCertas - 10)
                )

            },
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.extraLarge)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF76110C),
                            Color(0xFFCC481A),
                            Color(0xFFFEC651),
                        )
                    )
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )

        ) {
            Text("Histórico de jogos", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaDeResultadoPreview() {
    TelaDeResultado(navController = NavController(LocalContext.current), koinViewModel())
}