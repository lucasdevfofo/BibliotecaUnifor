package com.bibliotecaunifor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.bibliotecaunifor.model.AdminModel
import com.bibliotecaunifor.model.UsuarioModel
import com.bibliotecaunifor.repository.AuthRepository

class CadastroViewModel : ViewModel() {

    private val repo = AuthRepository()

    var loading by mutableStateOf(false)
    var sucesso by mutableStateOf(false)
    var erro by mutableStateOf<String?>(null)

    // ADMIN
    fun cadastrarAdmin(
        nome: String,
        matricula: String,
        cpf: String,
        telefone: String,
        email: String,
        senha: String
    ) {
        loading = true
        erro = null

        val admin = AdminModel(
            nomeCompleto = nome,
            matricula = matricula,
            cpf = cpf,
            telefone = telefone,
            email = email
        )

        repo.cadastrarAdmin(admin, senha) { ok, msg ->
            loading = false
            if (ok) sucesso = true else erro = msg
        }
    }

    // USUARIO
    fun cadastrarUsuario(
        nome: String,
        matricula: String,
        curso: String,
        cpf: String,
        telefone: String,
        email: String,
        senha: String
    ) {
        loading = true
        erro = null

        val user = UsuarioModel(
            nomeCompleto = nome,
            matricula = matricula,
            curso = curso,
            cpf = cpf,
            telefone = telefone,
            email = email
        )

        repo.cadastrarUsuario(user, senha) { ok, msg ->
            loading = false
            if (ok) sucesso = true else erro = msg
        }
    }
}
