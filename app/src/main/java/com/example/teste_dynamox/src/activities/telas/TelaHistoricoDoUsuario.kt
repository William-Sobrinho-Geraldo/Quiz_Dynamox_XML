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
import com.example.teste_dynamox.src.databaseLocal.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TelaHistoricoDoUsuario(navController: NavController) {

    var podeNavegarParaOutraTela by remember { mutableStateOf(false) }
    val jogosDao = AppDatabase.getDatabase(LocalContext.current).jogosDao()
    var quantDeJogos : Int = 2

    LaunchedEffect(Unit){
        quantDeJogos = jogosDao.buscarQuantTotalDeJogosPorID(userId = idUsuarioLogado)
        println("A quantDeJogos é :  $quantDeJogos" )
    }

    LaunchedEffect(podeNavegarParaOutraTela) {
        if (podeNavegarParaOutraTela) {
            navController.navigate("tela_historico_do_usuario")
        }
    }

    fun salvarResultadosEReiniciarQuiz(
        idDoUsuario: Long,
        dataDoJogo: String,
        quantRespostasCertas: Int,
        quantRespostasErradas: Int
    ) {
        CoroutineScope(Dispatchers.IO).launch {

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
            text = "    O histórico do usuário é  ",
            fontSize = 30.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(25.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Jogo ", fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.Bold,)
            Text("Certas ", fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.Bold,)
            Text("Erradas ", fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.Bold,)
        }
        Spacer(modifier = Modifier.height(16.dp))

        for (indice in 1 .. quantDeJogos) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                var contadorRespostasErradas = 10 - contadorRespostasCertas
                Text("${indice}º", fontSize = 30.sp, color = Color.White)
                Text(
                    "$contadorRespostasErradas",
                    color = Color.Green,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    "$contadorRespostasErradas",
                    color = Color.Red,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                )

            }


        }


        Spacer(modifier = Modifier.height(70.dp))

        Button(
            onClick = {
                contadorRespostasCertas = 0
                numeroDaPergunta = 1

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
            Text("Novo jogo", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaHistoricoDoUsuarioPreview() {
    TelaHistoricoDoUsuario(navController = NavController(LocalContext.current))
}