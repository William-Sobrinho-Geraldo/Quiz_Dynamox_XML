package com.example.teste_dynamox.src.activities.viewModel

import androidx.lifecycle.ViewModel
import com.example.teste_dynamox.src.databaseLocal.Users
import com.example.teste_dynamox.src.databaseLocal.jogosDosUsuaios
import com.example.teste_dynamox.src.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TelaDeCadastroDeUsuarioViewModel(
   private val repository: Repository,
) : ViewModel() {

   private val _userNameTelaDeCadastroDeUsuario = MutableStateFlow("")    //interno
   val userNameTelaDeCadastroDeUsuario = _userNameTelaDeCadastroDeUsuario.asStateFlow()

   fun atualizaUserName(novoUserName: String) {
      _userNameTelaDeCadastroDeUsuario.value = novoUserName
   }

   suspend fun verificaSeEstaCadastrado(): Boolean? {
      return repository.verificarSeUserNameEstaCadastrado(_userNameTelaDeCadastroDeUsuario.value)
   }

   suspend fun inserirNovoUsuario(user: Users) {

      return repository.inserirNovoUsuarioRepository(user)
   }

   suspend fun inserirNovoJogo(user: Users){
      val userId = repository.buscaIdPeloUserNameRepository(user.userName)
      val jogo = jogosDosUsuaios(0, userId, 0, 0)
      return repository.inserirJogoRepository(jogo)
   }
}