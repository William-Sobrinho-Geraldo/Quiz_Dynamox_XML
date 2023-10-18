package com.example.teste_dynamox.src.activities.viewModel

import androidx.lifecycle.ViewModel
import com.example.teste_dynamox.src.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TelaDeLoginViewModel(val repository: Repository) : ViewModel() {

   private var _userNameDigitado = MutableStateFlow("")  //INTERNO
   val userNameDigitado = _userNameDigitado.asStateFlow()

   fun atualizaUserNameDigitado(novoUserName : String ){
      _userNameDigitado.value = novoUserName
   }

}