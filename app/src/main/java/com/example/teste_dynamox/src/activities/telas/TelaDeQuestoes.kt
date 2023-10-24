package com.example.teste_dynamox.src.activities.telas

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.teste_dynamox.R
import com.example.teste_dynamox.src.activities.viewModel.TelaDeLoginViewModel
import com.example.teste_dynamox.src.api.AnswerRequest
import com.example.teste_dynamox.src.api.AppRetrofit
import com.example.teste_dynamox.src.api.ServerResponse
import com.example.teste_dynamox.src.databaseLocal.AppDatabase
import com.example.teste_dynamox.src.repository.Repository
import com.example.teste_dynamox.src.util.mostrarToast
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import org.koin.androidx.compose.koinViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "TelaDeQuestões"

var numeroDaPergunta = 1
var contadorRespostasCertas: Long = 0

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDeQuestoes(
   navController: NavController,
   context: Context,
   telaDeLoginViewModel: TelaDeLoginViewModel
) {
   val usuarioLogadoQuestoes = telaDeLoginViewModel.userNameLogado.collectAsState().value
   val idDaPerguntaQuestoes = telaDeLoginViewModel.id.value

   Log.i("TeladeQuestoes", "Na   TelaDeQuestoes: o usuário logado é  $usuarioLogadoQuestoes")

   val modifierCard: Modifier = Modifier
      .fillMaxWidth()
      .height(50.dp)
      .clip(RoundedCornerShape(16.dp))
      .padding(horizontal = 16.dp)

   val repository = Repository(
      AppDatabase.getDatabase(LocalContext.current).userDao(),
      AppDatabase.getDatabase(LocalContext.current).jogosDao(),
      AppRetrofit.ServicesApi
   )
   var respostaCerta by remember { mutableStateOf(10) }
   var respostaErrada by remember { mutableStateOf(10) }
   var alternativaEscolhida: Int? by remember { mutableStateOf(null) }
   var cardEnabled by remember { mutableStateOf(true) }
   var requisicaoCompleta by remember { mutableStateOf(false) }


   //Navegar para tela de questões após concluir a requisição
   LaunchedEffect(requisicaoCompleta) {
      if (requisicaoCompleta) {
         navController.navigate("tela_de_questoes/{statement}")
      }
   }

   fun atualizarPagina() {
      CoroutineScope(Dispatchers.IO).launch {
         try {
            val response = repository.getPerguntaRepository()
//            if (response.isSuccessful) {
//               withContext(Dispatchers.Main) {
//                  val quizResponse = response.body()
//                  statementt = quizResponse?.statement
//                  optionss = quizResponse?.options
//                  telaDeLoginViewModel.atualizaIdDaPergunta(quizResponse?.id)
//                  //idDaPerguntaQuestoes = quizResponse?.id
//               }
//            }
         } catch (e: Exception) {
            println("O erro foi $e")
         }
         requisicaoCompleta = true
      }
   }

   fun conferirResposta(url: String?, userAnswer: String) {
      GlobalScope.launch(Dispatchers.IO) {
         val answerRequest = AnswerRequest(answer = userAnswer)
         val requestBody =
            RequestBody.create("application/json".toMediaType(), Gson().toJson(answerRequest))
         println("requestBody é:  $requestBody")
         val call = repository.checkAnswerRepository(url, answerRequest)

         call.enqueue(object : Callback<ServerResponse> {
            override fun onResponse(
               call: Call<ServerResponse>,
               response: Response<ServerResponse>
            ) {
               if (response.isSuccessful) {
                  val serverResponse = response.body()
                  println("A resposta do servidor foi: $serverResponse")

                  if (serverResponse == ServerResponse(result = true)) {
                     cardEnabled = false
                     contadorRespostasCertas++
                     when (alternativaEscolhida) {
                        0 -> respostaCerta = 0
                        1 -> respostaCerta = 1
                        2 -> respostaCerta = 2
                        3 -> respostaCerta = 3
                        4 -> respostaCerta = 4
                     }
                  } else {
                     when (alternativaEscolhida) {
                        0 -> respostaErrada = 0
                        1 -> respostaErrada = 1
                        2 -> respostaErrada = 2
                        3 -> respostaErrada = 3
                        4 -> respostaErrada = 4
                     }
                     cardEnabled = false
                     respostaCerta = 10
                  }
               } else {
                  println("Resposta do servidor foi: Failure")
               }
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
               t.printStackTrace()
            }
         })
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
         )
   ) {
      Spacer(modifier = Modifier.height(40.dp))

      Text(
         text = "    Questão $numeroDaPergunta de 10: $statementt",
         fontSize = 24.sp,
         color = Color(0xFFffffff),
         fontWeight = FontWeight.Bold,
         modifier = Modifier.padding(horizontal = 16.dp)
      )
      Spacer(modifier = Modifier.height(40.dp))

      for (index in 0 until 5) {
         val option = optionss?.getOrNull(index)
         val backgroundColor = when (index) {
            respostaCerta -> {
               CardDefaults.cardColors(containerColor = Color.Green)
            }

            respostaErrada -> {
               CardDefaults.cardColors(containerColor = Color.Red)
            }

            else -> {
               CardDefaults.cardColors(containerColor = Color.DarkGray)
            }
         }

         Card(
            //enabled = cardEnabled,
            colors = backgroundColor,
            modifier = modifierCard,
            onClick = {
               if (cardEnabled && option != null) {
                  Log.i(TAG, "TelaDeQuestoes: chamei a função conferirResposta")
                  Log.i(TAG, "TelaDeQuestoes: idDaPergunta é $idDaPerguntaQuestoes")
                  conferirResposta(
                     url = "https://quiz-api-bwi5hjqyaq-uc.a.run.app/answer?questionId=$idDaPerguntaQuestoes",
                     userAnswer = option
                  )
                  alternativaEscolhida = index
               }
            }
         ) {
            Text(
               text = "${'A' + index}) $option",
               color = Color.White,
               fontWeight = FontWeight.Bold,
               modifier = Modifier.padding(16.dp)
            )
         }
         Spacer(modifier = Modifier.height(10.dp))
      }
      Spacer(modifier = Modifier.height(65.dp))

      Button(
         onClick = {
            if (numeroDaPergunta < 10) {
               if (alternativaEscolhida == null) {
                  mostrarToast(
                     "Escolha uma alternativa antes de ir para próxima questão",
                     context = context
                  )
               } else {
                  atualizarPagina()
                  numeroDaPergunta++
               }
            } else {
               navController.navigate("tela_de_resultado")
            }

         }, modifier = Modifier
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
         Text(text = "Próxima questão", fontSize = 20.sp)
         Spacer(modifier = Modifier.width(24.dp))
         Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_keyboard_double_arrow_right_24),
            contentDescription = "Entrar",
            modifier = Modifier.size(24.dp),
            tint = Color.White
         )
      }
   }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaDeQuestoesPreview() {
   TelaDeQuestoes(navController = rememberNavController(), context = LocalContext.current, koinViewModel())
}

