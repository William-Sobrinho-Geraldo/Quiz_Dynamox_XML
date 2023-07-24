package com.example.teste_dynamox.src.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teste_dynamox.src.activities.telas.SetupNavGraph
import com.example.teste_dynamox.src.activities.telas.TelaDeLogin
import com.example.teste_dynamox.ui.theme.Teste_DYNAMOXTheme

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Teste_DYNAMOXTheme {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    Teste_DYNAMOXTheme {
        val navController = rememberNavController()
        TelaDeLogin(navController = navController)
    }
}