package com.example.teste_dynamox.src.activities.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.teste_dynamox.src.databaseLocal.Users
import com.example.teste_dynamox.src.databaseLocal.jogosDosUsuaios
import com.example.teste_dynamox.src.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TelaDeCadastroDeUsuarioViewModel(
   private val repository: Repository,
) : ViewModel() {

   private val _userNameTelaDeCadastroDeUsuario = MutableStateFlow("")    //interno
   val userNameTelaDeCadastroDeUsuario = _userNameTelaDeCadastroDeUsuario.asStateFlow()

   fun atualizaUserName(novoUserName: String) {
      _userNameTelaDeCadastroDeUsuario.value = novoUserName
      Log.i("TAG", "atualizaUserName:  userNameTelaDeCadastroDeUsuario é : ${userNameTelaDeCadastroDeUsuario.value} ")
   }

   fun verificaSeEstaCadastrado(): Boolean {
      var retornoDoRepository = false
      CoroutineScope(Dispatchers.IO).launch {
         retornoDoRepository =
            repository.verificarSeUserNameEstaCadastrado(_userNameTelaDeCadastroDeUsuario.value)
      }
      return retornoDoRepository
   }

   fun inserirNovoUsuario(user: Users) {
      CoroutineScope(Dispatchers.IO).launch {
         repository.inserirNovoUsuarioRepository(user)
      }
   }

   fun inserirNovoJogo(user: Users) {
      CoroutineScope(Dispatchers.IO).launch {
         val userId = repository.buscaIdPeloUserNameRepository(user.userName)
         val jogo = jogosDosUsuaios(0, userId, 0, 0)
         repository.inserirJogoRepository(jogo)
      }
   }
}