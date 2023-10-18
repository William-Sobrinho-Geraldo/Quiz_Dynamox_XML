package com.example.teste_dynamox.src.activities.telas

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.teste_dynamox.R
import com.example.teste_dynamox.src.activities.viewModel.TelaDeLoginViewModel
import com.example.teste_dynamox.src.api.AppRetrofit
import com.example.teste_dynamox.src.databaseLocal.AppDatabase
import com.example.teste_dynamox.src.databaseLocal.Users
import com.example.teste_dynamox.src.repository.Repository
import com.example.teste_dynamox.src.util.mostrarToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.koinInject

var statement: String? = null
var optionss: MutableList<String>? = mutableListOf("", "1")
var id: String? = ""
val userNamesNoBancoDeDadosLocal = mutableListOf<String>()
var idUsuarioLogado: Long? = null

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDeLogin(navController: NavController, context: Context) {
    //get<TelaDeLoginViewModel>()     koinInject()
    val telaDeLoginViewModel = koinViewModel<TelaDeLoginViewModel>()
    val userNameDigitadoNoLogin = telaDeLoginViewModel.userNameDigitado.collectAsState()
    //val usuarioLogado = telaDeLoginViewModel.usuarioLogado.collectAsState()


    var podeNavegarParaOutraTela by remember { mutableStateOf(false) }
    val usuariosNoBancoDeDados: MutableList<Users> = mutableListOf()
    val repository = Repository(
        usersDao = AppDatabase.getDatabase(LocalContext.current).userDao(),
        jogosDao = AppDatabase.getDatabase(LocalContext.current).jogosDao(),
        servicesApi = AppRetrofit.ServicesApi
    )

    LaunchedEffect(Unit) {
        usuariosNoBancoDeDados.addAll(repository.buscaTodosUsuariosRepository())
        val userNames = usuariosNoBancoDeDados.map { user -> user.userName }
        userNamesNoBancoDeDadosLocal.addAll(userNames)
    }

    //Navegar para telaDeQuestões após concluir a requisição
    LaunchedEffect(podeNavegarParaOutraTela) {
        if (podeNavegarParaOutraTela) navController.navigate("tela_de_questoes/$statement")
    }


    fun fazerRequisicaoENavegarParaProximaTela() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                //buscando usuário logado - LOCALMENTE
                val idEncontrado = repository.buscaIdPeloUserNameRepository(userNameDigitadoNoLogin.value)
                if (idEncontrado != null) {
                    println("idEncontrado foi :  $idEncontrado")
                    idUsuarioLogado = idEncontrado
                    telaDeLoginViewModel.atualizaUsuarioLogado(userNameDigitadoNoLogin.value)
                } else println("não encontramos nenhum usuário")


                //buscando dados das perguntas - API
                //val response = repository.getPerguntaRepository()
                val response = repository.getPerguntaRepository()
                Log.i("TAG", "response é:   $response  ")
                if (response.isSuccessful) {
                    val quizResponse = response.body()
                    statement = quizResponse?.statement
                    optionss = quizResponse?.options
                    id = quizResponse?.id
                } else {
                    println("A requisição falhou!")
                }
                podeNavegarParaOutraTela = true
            } catch (e: Exception) {
                println("O erro encontrado foi: $e")
            }
        }
    }


    // CONSTRUINDO A TELA DE LOGIN
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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        item {
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
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 78.dp),
                color = Color(0xFFffffff)
            )

            OutlinedTextField(
                isError = true,
                textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                value = userNameDigitadoNoLogin.value,
                onValueChange = { novoUserName ->
                    telaDeLoginViewModel.atualizaUserNameDigitado(novoUserName) },
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
                    if (userNameDigitadoNoLogin.value in userNamesNoBancoDeDadosLocal) {
                        fazerRequisicaoENavegarParaProximaTela()
                    } else mostrarToast(
                        "Usuário ${userNameDigitadoNoLogin.value} não cadastrado",
                        context = context
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
                Text(
                    "Entrar",
                    color = Color.White,
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.width(24.dp))
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_login_24),
                    contentDescription = "Entrar",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Ainda não tem um usuário? ",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(start = 16.dp),
                    color = Color(0xFFffffff)
                )
                ClickableText(text = AnnotatedString("  Cadastre-se"),
                    style = TextStyle(fontSize = 16.sp, color = Color(0xFF2828ff)),
                    onClick = { navController.navigate("tela_de_cadastro_de_usuario") })
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaDeLoginPreviewConposable() {
    val navController = rememberNavController()
    TelaDeLogin(navController = navController, context = LocalContext.current)
}