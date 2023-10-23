package com.example.teste_dynamox.src.activities.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teste_dynamox.src.databaseLocal.Users
import com.example.teste_dynamox.src.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "telaDeloginViewModel"

class TelaDeLoginViewModel(val repository: Repository) : ViewModel() {
   private val _listaDeUsernames = MutableLiveData<List<String>>()
   val listaDeUsernames = _listaDeUsernames

   //VARIÁVEIS DAS PERGUNTAS
   private val _statement = MutableLiveData<String>()
   val statement = _statement.value


   private val _userNameDigitado = MutableStateFlow("")  //INTERNO
   val userNameDigitado = _userNameDigitado.asStateFlow()

   private val _usuarioLogado = MutableStateFlow<Users>(Users(userName = ""))     //INTERNO
   val userNameLogado = _usuarioLogado.asStateFlow()


   fun buscaListaDeUserNames () {
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
            "buscaUsuarioLogadoPeloUserName: o usuario encontrado na lista de usuários  é :  ${userNameLogado}"
         )
      }
   }

   fun atualizaUserNameDigitado(novoUserName: String) {
      _userNameDigitado.value = novoUserName
   }

}