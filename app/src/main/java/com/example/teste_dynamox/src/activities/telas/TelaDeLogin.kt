package com.example.teste_dynamox.src.activities.telas

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.compose.rememberNavController
import com.example.teste_dynamox.R
import com.example.teste_dynamox.src.api.ApiService.quizApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

var statement: String? = null
var optionss: MutableList<String>? = mutableListOf("", "1")
var id: String? = ""

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TelaDeLogin(navController: NavController) {
    var userName by remember { mutableStateOf("") }
    var isApiRequestCompleted by remember { mutableStateOf(false) }
    val usuariosValidos = listOf("admin", "William", "CEO Dynamox", " ")

    LaunchedEffect(isApiRequestCompleted) {
        if (isApiRequestCompleted) {
            // Navega para a próxima tela com a variável "statement" como argumento
            navController.navigate("tela_de_questoes/$statement")
        }
    }

    fun fazerRequisicao() {
        // Use a Coroutine para fazer a chamada da API na thread IO
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = quizApi.getPergunta()
                if (response.isSuccessful) {
                    val quizResponse = response.body()
                    statement = quizResponse?.statement
                    optionss = quizResponse?.options
                    id = quizResponse?.id
                    println("TelaDeLogin APÓS O GET -> O ID é $id, statement é: $statement e optionss é : $optionss")
                } else {
                    println("A requisição falhou!")
                }
            } catch (e: Exception) {
                println("O erro encontrado foi: $e")
            }
            isApiRequestCompleted = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFa14a50)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(
            "Quiz Dynamox",
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(25.dp),
            color = Color(0xFFffffff)

        )
        Spacer(modifier = Modifier.height(110.dp))

        Text(
            text = "Nome de usuário",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 42.dp),
            color = Color(0xFFffffff)
        )
        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            placeholder = {
                Text(
                    "Digite seu usuário aqui !",
                    color = Color(0xFFffffff)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .border(
                    width = 2.dp, shape = RoundedCornerShape(16.dp), brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFCC481A),
                            Color(0xFFCC481A),
                            Color(0xFF76110C)
                        )
                    )
                )
            //border = BorderStroke(2.dp, Color.Red)
        )

        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = { fazerRequisicao() },
            enabled = userName in usuariosValidos,
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaDeLoginPreviewConposable() {
    val navController = rememberNavController()
    TelaDeLogin(navController = navController)
}