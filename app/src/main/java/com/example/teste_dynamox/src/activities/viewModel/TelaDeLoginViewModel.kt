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
   val listaDeUsernames: MutableLiveData<List<String>> = MutableLiveData<List<String>>()
   val usuarioLogado: MutableLiveData<Users> = MutableLiveData<Users>()

   private val _userNameDigitado = MutableStateFlow("")  //INTERNO
   val userNameDigitado = _userNameDigitado.asStateFlow()

   private val _usuarioLogado = MutableStateFlow("eu")     //INTERNO
   val userNameLogado = _usuarioLogado.asStateFlow()


   fun buscaListaDeUsers() {
      CoroutineScope(Dispatchers.IO).launch {
         val usuarios = repository.buscaTodosUsuariosRepository()
         withContext(Dispatchers.Main) { listaDeUsernames.value = usuarios.map { users -> users.userName } }
      }
   }

   fun buscaUsuarioLogadoPeloUserName(userNameLogado: String) {
      CoroutineScope(Dispatchers.IO).launch {
         withContext(Dispatchers.Main) {
            usuarioLogado.value = repository.buscaUsuarioLogadoPeloUsername(userNameLogado)
         }
         Log.i(TAG, "buscaUsuarioLogadoPeloUserName: o usuarioLogado é :  ${usuarioLogado.value}")
      }
   }

   fun atualizaUsuarioLogado(usuarioQueFezLogin: String) {
      _usuarioLogado.value = usuarioQueFezLogin
      Log.i(TAG, "atualizaUsuarioLogado:  usuarioLogado é o : ${userNameLogado.value}")
   }

   fun atualizaUserNameDigitado(novoUserName: String) {
      _userNameDigitado.value = novoUserName
   }

}