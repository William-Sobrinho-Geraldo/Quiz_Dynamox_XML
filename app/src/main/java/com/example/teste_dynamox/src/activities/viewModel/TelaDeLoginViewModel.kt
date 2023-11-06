package com.example.teste_dynamox.src.activities.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.teste_dynamox.src.activities.telas.optionss
import com.example.teste_dynamox.src.activities.telas.statementt
import com.example.teste_dynamox.src.api.QuizModel
import com.example.teste_dynamox.src.databaseLocal.Users
import com.example.teste_dynamox.src.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

private const val TAG = "TelaDeLoginViewModel"

class TelaDeLoginViewModel(
   val repository: Repository,
   //   private val context: Context,
) : ViewModel() {
   private val _listaDeUsernames = MutableLiveData<List<String>>(emptyList())
   val listaDeUsernames = _listaDeUsernames

   // VARIÁVEIS DA    fun fazerRequisicaoENavegarParaProximaTela ()
   private val _navegarParaTelaDeQuestoes = MutableStateFlow(false)
   val navegarParaTelaDeQuestoes = _navegarParaTelaDeQuestoes
   private val _ocorreuErro = MutableLiveData(false)
   val ocorreuErro = _ocorreuErro
   private val _timeOut = MutableLiveData(false)
   val timeOut = _timeOut


   //VARIÁVEIS DAS PERGUNTAS
   private val _statement = MutableLiveData<String>(null)
   val statement = _statement
   private val _id = MutableLiveData("default")
   val id = _id
   private val _options = MutableLiveData<List<String>>(mutableListOf("", "1"))
   val options = _options


   //VARIÁVEIS DE userName
   private val _userNameDigitado = MutableLiveData("")
   val userNameDigitado = _userNameDigitado
   private val _usuarioLogado = MutableStateFlow<Users>(Users(userName = ""))
   val userNameLogado = _usuarioLogado.asStateFlow()

   fun fazerRequisicaoENavegarParaProximaTela() {
      try {    //buscar dados das perguntas na API
         val call = repository.getPerguntaRepository()

         call.enqueue(object : Callback<QuizModel> {
            override fun onResponse(call: Call<QuizModel>, response: Response<QuizModel>) {
               if (response.isSuccessful) {    //Atualiza as variáveis das perguntas

                  val quizResponse = response.body()
                  statementt = quizResponse?.statement
                  optionss = quizResponse?.options
                  _id.value = quizResponse?.id
                  _navegarParaTelaDeQuestoes.value = true          //informa para a view Trocar de tela
                  _timeOut.value = false
                  _ocorreuErro.value = false
               } else {     //mostra erro
                  Log.i(TAG, "onResponse:  A requisição Falhou!")
               }
            }

            override fun onFailure(call: Call<QuizModel>, t: Throwable) {
               _navegarParaTelaDeQuestoes.value = false

               if (t is SocketTimeoutException) {
                  _timeOut.value =
                     true                                        //informa para a view que tivemos timeOutException
                  _navegarParaTelaDeQuestoes.value = false
                  Log.i(TAG, "SOCKETTIMEOUTEXCEPTION aconteceu e _timeOut vale : ${_timeOut.value}")
               } else {
                  _ocorreuErro.value = true                      //informa para a view que ocorreu um erro
                  Log.i(TAG, "ERRO GENÉRICO  aconteceu e _ocorreuErro vale : ${_ocorreuErro.value}")
               }
            }
         })

      } catch (e: Exception) {
         Log.i(TAG, "caiu no catch: fazerRequisicaoENavegarParaProximaTela:  Erro encontrado é : $e")
      }
   }

   fun atualizaIdDaPergunta(idDaPergunta: String?) {
      _id.value = idDaPergunta
   }

   fun buscaListaDeUserNames() {
      Log.i(TAG, "buscaListaDeUserNames: VIEWMODEL a lista de userNames é ${_listaDeUsernames.value}")


      CoroutineScope(Dispatchers.IO).launch {
         val usuarios = repository.buscaTodosUsuariosRepository()
         withContext(Dispatchers.Main) {
            _listaDeUsernames.value = usuarios.map { users -> users.userName }
            Log.i(TAG, "buscaListaDeUserNames: VIEWMODEL a lista de usuários é $usuarios")
            Log.i(TAG, "buscaListaDeUserNames: VIEWMODEL _listaDeUsernames é ${_listaDeUsernames.value}")
         }
      }
      Log.i(
         TAG,
         "buscaListaDeUserNames: VIEWMODEL DEPOIS DE CHAMAR O REPOSITORY  a lista de userNames é ${_listaDeUsernames.value}"
      )
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