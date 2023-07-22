package com.example.teste_dynamox.src.activities.telas

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun SetupNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController, startDestination = TelasRoute.TelaDeLogin.route
    ) {
        composable(route = "tela_de_login") {
            TelaDeLogin(navController = navController)
        }
        composable(
            route = "tela_de_questoes/{name}",
            arguments = listOf(navArgument(name = "name") {
                type = NavType.StringType
            }
            )
        ) {backStackEntry ->
            // Obtenha os argumentos passados pela navegação usando rememberArgs()
//            val question = backStackEntry.arguments?.getString("question")
//            val options = backStackEntry.arguments?.getStringArrayList("options")
//            println("as questões são: $question e as opções são:  $options")
            TelaDeQuestoes(statement = backStackEntry.arguments?.getString("name"), options = emptyList())
            println("Arquivo NavGraph :  o statement é :  ${backStackEntry.arguments?.getString("name")}")
        }
    }
}