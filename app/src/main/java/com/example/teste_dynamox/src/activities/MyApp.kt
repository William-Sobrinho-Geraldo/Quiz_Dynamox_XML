package com.example.teste_dynamox.src.activities

import android.app.Application
import com.example.teste_dynamox.src.activities.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application () {

   override fun onCreate() {
      super.onCreate()

      startKoin{
         androidLogger()
         androidContext(this@MyApp)
         modules(mainModule)
      }
   }
}