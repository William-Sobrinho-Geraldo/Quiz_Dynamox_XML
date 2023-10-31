package com.example.teste_dynamox.src.activities.activitiesXML

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teste_dynamox.R
import com.example.teste_dynamox.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
   private lateinit var binding: ActivityLoginBinding
   override fun onCreate(savedInstanceState: Bundle?) {


      super.onCreate(savedInstanceState)
      binding = ActivityLoginBinding.inflate(layoutInflater)
      setContentView(binding.root)

   }
}