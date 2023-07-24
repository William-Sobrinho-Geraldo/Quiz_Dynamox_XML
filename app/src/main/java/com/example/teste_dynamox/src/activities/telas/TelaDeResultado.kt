package com.example.teste_dynamox.src.activities.telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TelaDeResultado(navController : NavController) {
    Column {
        Text(
            text = "    Obrigado por participar do Quiz Dynamox, seu resultado foi: ",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(25.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            Text("Respostas certas: ", fontSize = 16.sp)
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
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            var contadorRespostasErradas = 10 - contadorRespostasCertas
            Text("Respostas erradas: ", fontSize = 16.sp)
            Text(
                "$contadorRespostasErradas",
                color = Color.Red,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(25.dp),
            )
        }

        Button(
            onClick = {
                contadorRespostasCertas = 0
                numeroDaPergunta = 1
                navController.navigate("tela_de_questoes/{statement}")
            },
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large),

            ) {
            Text("Reiniciar Quiz", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaDeResultadoPreview() {
    TelaDeResultado(navController = NavController(LocalContext.current))
}