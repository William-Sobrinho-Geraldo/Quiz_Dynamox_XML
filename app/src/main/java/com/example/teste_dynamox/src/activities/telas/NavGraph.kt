package com.example.teste_dynamox.src.activities.telas

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlin.reflect.typeOf

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController, startDestination = TelasRoute.TelaDeLogin.route
    ) {
        composable(route = "tela_de_login") {
            TelaDeLogin(navController = navController)
        }
        composable(
            route = "tela_de_questoes/{statement}",
            arguments = listOf(
                navArgument(name = "statement") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            // Obtenha os argumentos passados pela navegação usando rememberArgs()
//            val statement = backStackEntry.arguments?.getString("statement")
            TelaDeQuestoes(
//                statement = statement,
                navController = navController,
                context = LocalContext.current
            )
//            println("Arquivo NavGraph :  o statement é :  ${backStackEntry.arguments?.getString("statement")}")
        }
    }
}