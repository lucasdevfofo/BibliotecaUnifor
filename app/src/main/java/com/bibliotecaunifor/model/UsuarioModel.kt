package com.bibliotecaunifor.model

data class UsuarioModel(
    val nomeCompleto: String = "",
    val matricula: String = "",
    val curso: String = "",
    val cpf: String = "",
    val telefone: String = "",
    val email: String = "",
    val tipo: String = "usuario"
)
