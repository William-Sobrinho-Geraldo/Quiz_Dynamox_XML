package com.example.teste_dynamox.src.activities.telas

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teste_dynamox.R
import com.example.teste_dynamox.src.api.AppRetrofit
import com.example.teste_dynamox.src.databaseLocal.AppDatabase
import com.example.teste_dynamox.src.databaseLocal.Users
import com.example.teste_dynamox.src.databaseLocal.jogosDosUsuaios
import com.example.teste_dynamox.src.repository.Repository
import com.example.teste_dynamox.src.util.mostrarToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TelaDeCadastroDeUsuario(navController: NavController, context: Context) {
    var userName by remember { mutableStateOf("") }
    var isApiRequestCompleted by remember { mutableStateOf(false) }
    println("Os userNamesNoBancoDeDadosLocal são:  $userNamesNoBancoDeDadosLocal")


    val repository = Repository(
        AppDatabase.getDatabase(LocalContext.current).userDao(),
        AppDatabase.getDatabase(LocalContext.current).jogosDao(),
        AppRetrofit.ServicesApi
    )

//    val userDao = AppDatabase.getDatabase(LocalContext.current).userDao()
//    val jogosDao = AppDatabase.getDatabase(LocalContext.current).jogosDao()


    var usuariosNoBancoDeDados: MutableList<Users> = mutableListOf()
    println("usuariosNoBancoDeDados são: $usuariosNoBancoDeDados")

    LaunchedEffect(Unit) {
        usuariosNoBancoDeDados.addAll(repository.buscaTodosUsuarios())
        println("usuariosNoBancoDeDados são:  $usuariosNoBancoDeDados")
        val userNames = usuariosNoBancoDeDados.map { user -> user.userName }
        println("usernames é:  $userNames")
        userNamesNoBancoDeDadosLocal.addAll(userNames)
    }

    fun cadastrarUsuarioESeusJogos(userName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = Users(userName = userName)
            val jogo = jogosDosUsuaios(0,0,0,0)

            val userId = repository.inserirNovoUsuario(user)
            jogo.userId = userId
            repository.inserirJogo(jogo)
        }
    }

    LaunchedEffect(isApiRequestCompleted) {
        if (isApiRequestCompleted) {
            navController.navigate("tela_de_questoes/$statement")
        }
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
                //    color = Color(0xFF232323)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        item {
            Text(
                "Cadastre-se",
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(25.dp),
                color = Color(0xFFffffff)

            )
            Spacer(modifier = Modifier.height(110.dp))

            Text(
                text = "Nome de usuário",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 78.dp),
                color = Color(0xFFffffff)
            )

            OutlinedTextField(
                textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                value = userName,
                onValueChange = { userName = it },
                placeholder = {
                    Text(
                        "Cadastre seu username !",
                        color = Color(0xFFffffff)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
//                .border(BorderStroke(5.dp,Color.Transparent))
                    .border(
                        width = 2.dp, shape = RoundedCornerShape(5.dp), brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFCC481A),
                                Color(0xFFCC481A),
                                Color(0xFF76110C)
                            )
                        )
                    )
            )
            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = {
                    if (userNamesNoBancoDeDadosLocal.contains(userName)) {
                        mostrarToast("Usuário $userName já cadastrado!", context = context)
                    } else {
                        cadastrarUsuarioESeusJogos(userName)
                        mostrarToast("Usuario $userName cadastrado com sucesso", context = context)
                    }
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
                Text(
                    "Cadastrar",
                    color = Color.White,
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.width(24.dp))
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_person_add_alt_1_24),
                    contentDescription = "Entrar",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TelaDeCadastroDeUsuarioPreview() {
    TelaDeCadastroDeUsuario(
        navController = NavController(LocalContext.current),
        context = LocalContext.current
    )
}
