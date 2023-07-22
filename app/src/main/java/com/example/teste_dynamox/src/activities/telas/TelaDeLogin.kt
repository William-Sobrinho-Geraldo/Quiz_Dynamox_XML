package com.example.teste_dynamox.src.activities.telas

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings.Global.putString
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.teste_dynamox.R
import com.example.teste_dynamox.src.api.ApiService.quizApi
import com.example.teste_dynamox.src.api.QuizModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


var statement: String? = null

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TelaDeLogin(navController: NavController) {
    var userName by remember { mutableStateOf("") }

    var isApiRequestCompleted by remember { mutableStateOf(false) }

    LaunchedEffect(isApiRequestCompleted) {
        if(isApiRequestCompleted){
            // Navega para a próxima tela com a variável "statement" como argumento
            navController.navigate("tela_de_questoes/$statement")
        }
    }

    fun fazerRequisicaoENavegar() {
        // Use a Coroutine para fazer a chamada da API na thread IO
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = quizApi.getPergunta()
                if (response.isSuccessful) {
                    val quizResponse = response.body()
                    //quizModelResult.value = quizResponse
                    statement = quizResponse?.statement
                    val options = quizResponse?.options

                    println("o statement é :  $statement   e options é : $options")
                    if (quizResponse != null) {

                        println("Resposta da API: $quizResponse") // Imprimir a resposta no console
                        // Navegue para a próxima tela e passe a resposta da API como argumento
                    } else {
                        println("Resposta da API está vazia.")
                    }
                } else {
                    // Lida com erros da resposta da API
                }
            } catch (e: Exception) {
                // Lida com erros da requisição
            }
            isApiRequestCompleted = true
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Nome de usuário",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 42.dp)
            )
            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                placeholder = { Text("Digite seu usuário aqui !") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = {
                    fazerRequisicaoENavegar()
                },
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
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
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_login_24),
                    contentDescription = "Entrar",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    "Entrar",
                    color = Color.White,
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
            }
        }
    }


}


@Preview
@Composable
fun TelaDeLoginPreviewConposable() {
    val navController = rememberNavController()
    TelaDeLogin(navController = navController)
}