package com.bibliotecaunifor.model

data class Sala(
    val id: String = "",
    val nome: String = "",
    val capacidade: Int = 0,
    val tipo: String = "",
    val disponivel: Boolean = true
)