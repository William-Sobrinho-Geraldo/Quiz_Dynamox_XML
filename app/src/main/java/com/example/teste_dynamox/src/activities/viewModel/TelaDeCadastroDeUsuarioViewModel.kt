package com.example.teste_dynamox.src.activities.viewModel

import androidx.lifecycle.ViewModel
import com.example.teste_dynamox.src.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TelaDeCadastroDeUsuarioViewModel(
  // private val repository: Repository,
) : ViewModel() {

   private val _userNameTelaDeCadastroDeUsuario = MutableStateFlow<String>("")    //interno
   val userNameTelaDeCadastroDeUsuario = _userNameTelaDeCadastroDeUsuario.asStateFlow()

   fun atualizaUserName(novoUserName: String) {
      _userNameTelaDeCadastroDeUsuario.value = novoUserName
   }

   fun insert() {
      println("Sou lindo")
   }

//      private val repository: Repository = Repository(
//         AppDatabase.getDatabase(LocalContext.current).userDao(),
//         AppDatabase.getDatabase(LocalContext.current).jogosDao(),
//         AppRetrofit.ServicesApi
//      )

}