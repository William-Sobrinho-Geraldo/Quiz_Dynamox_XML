package com.example.teste_dynamox.src.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.teste_dynamox.src.activities.telas.SetupNavGraph
import com.example.teste_dynamox.src.activities.telas.TelaDeLogin
import com.example.teste_dynamox.src.activities.viewModel.CompartilhamentoViewModels
import com.example.teste_dynamox.src.activities.viewModel.TelaDeCadastroDeUsuarioViewModel
import com.example.teste_dynamox.src.activities.viewModel.TelaDeLoginViewModel
import com.example.teste_dynamox.src.api.AppRetrofit
import com.example.teste_dynamox.src.databaseLocal.AppDatabase
import com.example.teste_dynamox.src.repository.Repository
import com.example.teste_dynamox.ui.theme.Teste_DYNAMOXTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {


      super.onCreate(savedInstanceState)
      setContent {
         Teste_DYNAMOXTheme {
            SetupNavGraph(navController = rememberNavController())

         }
      }
   }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
   Teste_DYNAMOXTheme {
      val navController = rememberNavController()
      TelaDeLogin(navController = navController, context = LocalContext.current)
   }
}