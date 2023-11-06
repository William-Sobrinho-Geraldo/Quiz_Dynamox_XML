package com.example.teste_dynamox.src.activities.activitiesXML

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.teste_dynamox.databinding.ActivityLoginBinding
import com.example.teste_dynamox.src.activities.viewModel.TelaDeLoginViewModel
import com.example.teste_dynamox.src.util.mostrarToast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {
   private lateinit var binding: ActivityLoginBinding
   private val telaDeLoginViewModel: TelaDeLoginViewModel by viewModel()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityLoginBinding.inflate(layoutInflater)
      setContentView(binding.root)
      //  OK     val telaDeLoginViewModel = koinViewModel<TelaDeLoginViewModel>()
      //      val userNameDigitadoNoLogin = telaDeLoginViewModel.userNameDigitado.collectAsState()
      //      val listaDeUserNamesNoBancoDeDados = telaDeLoginViewModel.listaDeUsernames.value
      //      val ocorreuErro = telaDeLoginViewModel.ocorreuErro.observeAsState().value
      //      val erroTimeOut = telaDeLoginViewModel.timeOut.observeAsState().value
      //      val navegarParaTelaDeQuestoes =
      //         telaDeLoginViewModel.navegarParaTelaDeQuestoes.collectAsStateWithLifecycle().value

      // 1º popula  lista de usernames   ->   FEITO
      // 2º quando clicar, vc pega o texto q foi clicado  e atualizaUsernameDigitado  ->   FEITO
      // 3º Verifica se tá na lista,   ->  FEITO
      // 4º se estiver faz a requisição com retrofit


      telaDeLoginViewModel.buscaListaDeUserNames()   //1º - POPULANDO LISTA DE USERNAMES




      binding.btnEntrar.setOnClickListener {
         val textoDigitado = binding.loginEditText.text.toString()
         telaDeLoginViewModel.atualizaUserNameDigitado(textoDigitado)
         Log.i(TAG, "variável mudando é textoDigitado  :  $textoDigitado")
         if (textoDigitado.isBlank()) mostrarToast("Digite um usuário", this)

         telaDeLoginViewModel.listaDeUsernames.observe(this) { listaDeUsernamesDoViewmodel ->
            if (listaDeUsernamesDoViewmodel.contains(textoDigitado)) {

               telaDeLoginViewModel.fazerRequisicaoENavegarParaProximaTela()    //FAZENDO REQUISIÇÃO

               Log.i(TAG, "onCreate:  USUARIO FOI ENCONTRADO")
               Log.i(TAG, "onCreate: listaDeUsernamesDoViewmodel é : $listaDeUsernamesDoViewmodel ")
            } else {
               mostrarToast("Usuario não encontrado, cadastre-se", this)
            }
         }


      }  //Listener
   } //onCreate
}  // Activity





