package com.example.teste_dynamox.src.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.teste_dynamox.src.activities.telas.SetupNavGraph
import com.example.teste_dynamox.src.activities.telas.TelaDeLogin
import com.example.teste_dynamox.ui.theme.Teste_DYNAMOXTheme
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth
private lateinit var callbackManager: CallbackManager

class MainActivity : ComponentActivity(), MainActivityProvider {
   override fun onCreate(savedInstanceState: Bundle?) {
      callbackManager = CallbackManager.Factory.create()
      auth = Firebase.auth

      super.onCreate(savedInstanceState)
      setContent {
         Teste_DYNAMOXTheme {
            SetupNavGraph(navController = rememberNavController())
         }
      }
   }


   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
      callbackManager.onActivityResult(requestCode,resultCode,data)

      super.onActivityResult(requestCode, resultCode, data)
   }

   override fun getMainActivity(): MainActivity {
      return this
   }
}

//@Preview(showBackground = true)
//@Composable
//fun AppPreview() {
//   Teste_DYNAMOXTheme {
//      val navController = rememberNavController()
//      TelaDeLogin(
//         navController = navController,
//         context = LocalContext.current,
//      )
//   }
//}