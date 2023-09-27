package com.example.teste_dynamox.src.activities.di

import com.example.teste_dynamox.src.activities.viewModel.TelaDeCadastroDeUsuarioViewModel
import com.example.teste_dynamox.src.api.AppRetrofit
import com.example.teste_dynamox.src.databaseLocal.AppDatabase
import com.example.teste_dynamox.src.repository.Repository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

   single { AppDatabase.getDatabase(androidContext()).userDao() }
   single { AppDatabase.getDatabase(androidContext()).jogosDao() }
   single { AppRetrofit.ServicesApi }

   single {
      Repository(
         usersDao = get(),
         jogosDao = get(),
         ServicesApi = get(),
      )
   }

   viewModel { TelaDeCadastroDeUsuarioViewModel(repository = get()) }
}