package com.bibliotecaunifor.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bibliotecaunifor.repository.AuthRepository

class LoginViewModel : ViewModel() {

    private val repo = AuthRepository()

    var loading by mutableStateOf(false)
    var sucesso by mutableStateOf(false)
    var erro by mutableStateOf<String?>(null)

    fun loginComMatricula(matricula: String, senha: String) {
        loading = true
        erro = null

        repo.loginPorMatricula(matricula, senha) { ok, msg ->
            loading = false
            if (ok) {
                sucesso = true
            } else {
                erro = msg
            }
        }
    }
}
