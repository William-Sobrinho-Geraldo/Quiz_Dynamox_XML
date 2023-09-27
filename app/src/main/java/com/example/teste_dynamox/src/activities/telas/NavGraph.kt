package com.example.teste_dynamox.src.activities.telas

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.teste_dynamox.src.activities.viewModel.CompartilhamentoViewModels
import com.example.teste_dynamox.src.activities.viewModel.TelaDeCadastroDeUsuarioViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.reflect.typeOf

@Composable
fun SetupNavGraph(
   navController: NavHostController,
) {
   NavHost(
      navController = navController, startDestination = TelasRoute.TelaDeLogin.route
   ) {
      composable(route = "tela_de_login") {
         TelaDeLogin(navController = navController, context = LocalContext.current)
      }
      composable(
         route = "tela_de_questoes/{statement}",
         arguments = listOf(
            navArgument(name = "statement") {
               type = NavType.StringType
            }
         )
      ) {
         TelaDeQuestoes(navController = navController, context = LocalContext.current)
      }
      composable(route = "tela_de_resultado") {
         TelaDeResultado(navController = navController)
      }
      composable(route = "tela_de_cadastro_de_usuario") {
         TelaDeCadastroDeUsuario(
            navController = navController,
            context = LocalContext.current,
         )
      }
      composable(route = "tela_historico_do_usuario") {
         TelaHistoricoDoUsuario(navController = navController)
      }
   }
}