package com.example.teste_dynamox.src.activities.di

import android.content.Context
import com.example.teste_dynamox.src.activities.viewModel.CompartilhamentoViewModels
import com.example.teste_dynamox.src.activities.viewModel.TelaDeCadastroDeUsuarioViewModel
import com.example.teste_dynamox.src.api.AppRetrofit
import com.example.teste_dynamox.src.databaseLocal.AppDatabase
import com.example.teste_dynamox.src.repository.Repository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

//   single { (context: Context) ->
//      Repository(
//         AppDatabase.getDatabase(context).userDao(),
//         AppDatabase.getDatabase(context).jogosDao(),
//         AppRetrofit.ServicesApi
//      )
//   }

   viewModel { TelaDeCadastroDeUsuarioViewModel() }
}