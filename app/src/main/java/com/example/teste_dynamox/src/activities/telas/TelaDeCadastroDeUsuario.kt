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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.teste_dynamox.src.activities.viewModel.TelaDeCadastroDeUsuarioViewModel
import com.example.teste_dynamox.src.databaseLocal.Users
import com.example.teste_dynamox.src.util.mostrarToast
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDeCadastroDeUsuario(navController: NavController, context: Context) {
   val telaDeCadastroViewModel: TelaDeCadastroDeUsuarioViewModel = koinViewModel()
   val userNameDaTelaDeCadastroDeUsuario by telaDeCadastroViewModel.userNameTelaDeCadastroDeUsuario.collectAsState()
   val isApiRequestCompleted by remember { mutableStateOf(false) }

   fun cadastrarUsuario() {
      telaDeCadastroViewModel.inserirNovoUsuario(Users(userName = userNameDaTelaDeCadastroDeUsuario))
      telaDeCadastroViewModel.inserirNovoJogo(Users(userName = userNameDaTelaDeCadastroDeUsuario))
   }

   fun verificarUserName() {
      if (telaDeCadastroViewModel.verificaSeEstaCadastrado().value!!) {
         mostrarToast(
            "Usuário $userNameDaTelaDeCadastroDeUsuario já cadastrado!",
            context = context,
         )
      } else {
         cadastrarUsuario()
         mostrarToast(
            "Usuario $userNameDaTelaDeCadastroDeUsuario cadastrado com sucesso",
            context = context,
         )
      }
   }

   //VERIFICA SE COMPLETOU A REQUISIÇÃO E NAVEGA PARA OUTRA TELA
   LaunchedEffect(isApiRequestCompleted) {
      if (isApiRequestCompleted) navController.navigate("tela_de_questoes/$statement")
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
            value = telaDeCadastroViewModel.userNameTelaDeCadastroDeUsuario.collectAsState().value,
            onValueChange = { novoValorDigitado: String ->
               telaDeCadastroViewModel.atualizaUserName(novoValorDigitado)
            },
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
            onClick = { verificarUserName() },
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
      context = LocalContext.current,
   )
}
