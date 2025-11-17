package com.bibliotecaunifor.model

data class AdminModel(
    val nomeCompleto: String = "",
    val matricula: String = "",
    val cpf: String = "",
    val telefone: String = "",
    val email: String = "",
    val tipo: String = "admin"
)
