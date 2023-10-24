package com.example.teste_dynamox.src.activities.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.teste_dynamox.src.activities.telas.optionss
import com.example.teste_dynamox.src.activities.telas.statementt
import com.example.teste_dynamox.src.databaseLocal.Users
import com.example.teste_dynamox.src.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "TelaDeQuestões"

class TelaDeLoginViewModel ( val repository: Repository ) : ViewModel() {
   private val _listaDeUsernames = MutableLiveData<List<String>>()
   val listaDeUsernames = _listaDeUsernames

   //VARIÁVEIS DAS PERGUNTAS
   private val _statement = MutableLiveData<String>(null)
   val statement = _statement.value
   private val _id = MutableLiveData("pica")
   val id = _id
   private val _options = MutableLiveData<List<String>>(mutableListOf("", "1"))
   val options = _options.value


   private val _userNameDigitado = MutableStateFlow("")  //INTERNO
   val userNameDigitado = _userNameDigitado.asStateFlow()

   private val _usuarioLogado = MutableStateFlow<Users>(Users(userName = ""))     //INTERNO
   val userNameLogado = _usuarioLogado.asStateFlow()

   fun fazerRequisicaoENavegarParaProximaTela(navController: NavController) {
      CoroutineScope(Dispatchers.IO).launch {
         try {
            //buscando dados das perguntas na API
            val respostaRequisicao = async { repository.getPerguntaRepository() }
            val response = respostaRequisicao.await()      //Aguardando a conclusão da requisição

            Log.i("TAG", "response é:   $response  ")
            if (response.isSuccessful) {
               withContext(Dispatchers.Main) {
                  val quizResponse = response.body()
                  statementt = quizResponse?.statement
                  optionss = quizResponse?.options
                  _id.value = quizResponse?.id

                  navController.navigate("tela_de_questoes/$statementt")
               }

            } else {
               println("A requisição falhou!")
            }

         } catch (e: Exception) {
            println("O erro encontrado foi: $e")
         }
      }
   }

   fun atualizaIdDaPergunta(idDaPergunta: String?) {
      _id.value = idDaPergunta
   }

   fun buscaListaDeUserNames() {
      CoroutineScope(Dispatchers.IO).launch {
         val usuarios = repository.buscaTodosUsuariosRepository()
         withContext(Dispatchers.Main) { _listaDeUsernames.value = usuarios.map { users -> users.userName } }
      }
   }

   fun buscaUsuarioLogadoPeloUserName(userNameLogado: String) {
      CoroutineScope(Dispatchers.IO).launch {
         withContext(Dispatchers.Main) {
            _usuarioLogado.value = repository.buscaUsuarioLogadoPeloUsername(userNameLogado)
         }
         Log.i(
            TAG,
            "buscaUsuarioLogadoPeloUserName: o usuario encontrado na lista de usuários  é :  $userNameLogado"
         )
      }
   }

   fun atualizaUserNameDigitado(novoUserName: String) {
      _userNameDigitado.value = novoUserName
   }

}