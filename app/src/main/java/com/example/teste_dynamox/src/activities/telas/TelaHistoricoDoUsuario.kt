package com.example.teste_dynamox.src.activities.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import com.example.teste_dynamox.src.databaseLocal.jogosDosUsuaios
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val listaQuantErros = mutableStateListOf<Long>()
val listaQuantAcertos =  mutableStateListOf<Long>()

@Composable
fun TelaHistoricoDoUsuario(navController: NavController) {
    var quantDeJogos by remember { mutableStateOf(4) }
    val jogosDao = AppDatabase.getDatabase(LocalContext.current).jogosDao()
    var novoJogo by remember { mutableStateOf(false) }

    println("fora do LaunchEffect listaQuantAcertos.size é:  ${listaQuantAcertos.size} e a listaQuantAcertos toda é $listaQuantAcertos")
    println("fora do LaunchEffect listaQuantAcertos.size é:  ${listaQuantErros.size} e a listaQuantAcertos toda é $listaQuantErros")
    println("fora do LaunchedEffet o contadorRespostasCertas  é $contadorRespostasCertas")

    LaunchedEffect(Unit) {
        quantDeJogos = jogosDao.buscarQuantTotalDeJogosPorID(userId = idUsuarioLogado)

        val listaCertasRecebida = jogosDao.buscaQuantCertasPorUserId(userId = idUsuarioLogado)
        val listaErradasRecebida = jogosDao.buscaQuantDeErrosPorUserId(userId = idUsuarioLogado)
        println("DENTRO DO LAUNCHEDEFFECT ListaCertasRecebida foi:  $listaCertasRecebida")
        println("DENTRO DO LAUNCHEDEFFECT ListaErrosRecebido foi:  $listaErradasRecebida")
        listaQuantAcertos.clear()
        listaQuantAcertos.addAll(listaCertasRecebida)
        listaQuantErros.clear()
        listaQuantErros.addAll(listaErradasRecebida)
        println("Para o userId $idUsuarioLogado A quantDeJogos é :  $quantDeJogos")
        println("Para o userID $idUsuarioLogado tivemos uma lista de ${listaQuantAcertos.size} de tamanho, a lista é : $listaQuantAcertos")
        println("Para o userID $idUsuarioLogado tivemos uma lista de ${listaQuantErros.size} de tamanho, a lista é : $listaQuantErros")
    }

    LaunchedEffect(novoJogo) {
        if (novoJogo) { navController.navigate("tela_de_questoes/$statement") }
    }

    LazyColumn(
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
        item {
            Text(
                text = "    O histórico de jogos de $userNameUsuarioLogado é ",
                fontSize = 30.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(25.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("Jogo ", fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Text("Certas ", fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Text("Erradas ", fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(quantDeJogos) {index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                    Box(modifier = Modifier.padding(start = 45.dp), contentAlignment = Alignment.Center) {
                        Text("${index + 1}º", fontSize = 30.sp, color = Color.White)
                    }

                    val certas = listaQuantAcertos.getOrNull(index)
                    println("O item de indice $index da listaQuantAcertos foi $certas ")
                    Box(modifier = Modifier.padding(start = 45.dp), contentAlignment = Alignment.Center) {
                        Text(
                            "$certas",
                            color = Color.Green,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    val erradas = listaQuantErros.getOrNull(index)
                    println("O item de indice $index da listaQuantErros foi $erradas ")
                    Box(modifier = Modifier.padding(start = 45.dp), contentAlignment = Alignment.Center) {
                        Text(
                            "$erradas",
                            color = Color.Red,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 40.dp)
                        )
                    }
                }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Button(
                onClick = {
                    novoJogo = true
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
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaHistoricoDoUsuarioPreview() {
    TelaHistoricoDoUsuario(navController = NavController(LocalContext.current))
}