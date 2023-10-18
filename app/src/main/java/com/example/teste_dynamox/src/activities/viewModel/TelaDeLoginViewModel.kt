package com.example.teste_dynamox.src.activities.viewModel

import android.util.Log
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.teste_dynamox.src.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TelaDeLoginViewModel(val repository: Repository) : ViewModel() {

   private val _userNameDigitado = MutableStateFlow("")  //INTERNO
   val userNameDigitado = _userNameDigitado.asStateFlow()

   private val _usuarioLogado = MutableStateFlow("eu")     //INTERNO
   val usuarioLogado = _usuarioLogado.asStateFlow()

   fun atualizaUsuarioLogado(usuarioQueFezLogin: String) {
      _usuarioLogado.value = usuarioQueFezLogin
      Log.i("telaDeLoginViewmodel", "função atualizaUsuarioLogado: e o novo usuarioLogado é ${usuarioLogado.value}")
   }


   fun atualizaUserNameDigitado(novoUserName: String) {
      _userNameDigitado.value = novoUserName
      Log.i(
         "telaDeLoginViewmodel",
         "função atualizaUserNameDigitado: e o novo valor é ${userNameDigitado.value}"
      )
   }

}