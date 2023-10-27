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
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.teste_dynamox.R
import com.example.teste_dynamox.src.activities.viewModel.TelaDeLoginViewModel
import com.example.teste_dynamox.src.util.mostrarToast
import org.koin.androidx.compose.koinViewModel

private const val TAG = "TelaDeLogin"

var statementt: String? = null
var optionss: MutableList<String>? = mutableListOf("", "1")
//var idd: String? = ""


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDeLogin(navController: NavController, context: Context) {
   val telaDeLoginViewModel = koinViewModel<TelaDeLoginViewModel>()
   val userNameDigitadoNoLogin = telaDeLoginViewModel.userNameDigitado.collectAsState()
   val listaDeUserNamesNoBancoDeDados = telaDeLoginViewModel.listaDeUsernames.value
   val ocorreuErro = telaDeLoginViewModel.ocorreuErro.observeAsState().value
   val erroTimeOut = telaDeLoginViewModel.timeOut.observeAsState().value
   val navegarParaTelaDeQuestoes = telaDeLoginViewModel.navegarParaTelaDeQuestoes.collectAsStateWithLifecycle().value


   erroTimeOut?.let {
      if (erroTimeOut) mostrarToast(
         "Dificuldades de comunicação com servidor",
         context = context
      )
   }

   ocorreuErro?.let {
      if (ocorreuErro) mostrarToast(
         "Erro inesperado",
         context = context
      )
   }

   telaDeLoginViewModel.buscaListaDeUserNames()    //POPULANDO A LISTA DE USERNAMES

   LaunchedEffect(navegarParaTelaDeQuestoes) {
      if (navegarParaTelaDeQuestoes) navController.navigate("tela_de_questoes/$statementt")
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
               telaDeLoginViewModel.atualizaUserNameDigitado(novoUserName)
            },
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
               listaDeUserNamesNoBancoDeDados?.let {
                  Log.i(
                     TAG,
                     "TelaDeLogin:  ListaDeUserNamesNoBancoDeDados é   $listaDeUserNamesNoBancoDeDados"
                  )
                  if (listaDeUserNamesNoBancoDeDados.contains(userNameDigitadoNoLogin.value)) {
                     telaDeLoginViewModel.fazerRequisicaoENavegarParaProximaTela()
                     telaDeLoginViewModel.buscaUsuarioLogadoPeloUserName(userNameDigitadoNoLogin.value)
                  } else {
                     mostrarToast(
                        "Usuário ${userNameDigitadoNoLogin.value} não cadastrado",
                        context = context
                     )
                  }
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