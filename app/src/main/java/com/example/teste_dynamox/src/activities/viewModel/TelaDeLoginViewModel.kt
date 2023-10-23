package com.example.teste_dynamox.src.activities.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.teste_dynamox.src.activities.telas.idd
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

private const val TAG = "telaDeloginViewModel"

class TelaDeLoginViewModel(val repository: Repository) : ViewModel() {
   private val _listaDeUsernames = MutableLiveData<List<String>>()
   val listaDeUsernames = _listaDeUsernames

   //VARIÁVEIS DAS PERGUNTAS
   private val _statement = MutableLiveData<String>(null)
   val statement = _statement.value
   private val _id = MutableLiveData<String>("")
   val id = _id.value
   private val _options = MutableLiveData<List<String>>(mutableListOf("", "1"))
   val options = _options.value
   private var _requisicaoApiCompleta = false
   val requisicaoApiCompleta = _requisicaoApiCompleta


   private val _userNameDigitado = MutableStateFlow("")  //INTERNO
   val userNameDigitado = _userNameDigitado.asStateFlow()

   private val _usuarioLogado = MutableStateFlow<Users>(Users(userName = ""))     //INTERNO
   val userNameLogado = _usuarioLogado.asStateFlow()

   fun fazerRequisicaoENavegarParaProximaTela(navController: NavController) {
      CoroutineScope(Dispatchers.IO).launch {
         try {
            //buscando dados das perguntas - API
            val respostaRequisicao = async { repository.getPerguntaRepository()  }
            val response = respostaRequisicao.await()

            Log.i("TAG", "response é:   $response  ")
            if (response.isSuccessful) {
               val quizResponse = response.body()
               com.example.teste_dynamox.src.activities.telas.statementt = quizResponse?.statement
               optionss = quizResponse?.options
               Log.i(TAG, "fazerRequisicaoENavegarParaProximaTela:  optionss é $optionss")
               Log.i(TAG, "fazerRequisicaoENavegarParaProximaTela:  statementt é $statementt")
               Log.i(TAG, "fazerRequisicaoENavegarParaProximaTela:  idd é $idd")
               com.example.teste_dynamox.src.activities.telas.idd = quizResponse?.id


               withContext(Dispatchers.Main) {
                  navController.navigate("tela_de_questoes/$statementt")
               }

               Log.i(TAG, "fazerRequisicaoENavegarParaProximaTela: requisicaoAPI é $requisicaoApiCompleta")
               Log.i(TAG, "fazerRequisicaoENavegarParaProximaTela: _requisicaoAPI é $_requisicaoApiCompleta")

            } else {  println("A requisição falhou!")  }


         } catch (e: Exception) {
            println("O erro encontrado foi: $e")
         }
      }
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