package com.example.teste_dynamox.src.util

import android.content.Context
import android.widget.Toast
fun mostrarToast(mensagem : String, context : Context){
    Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show()
}
