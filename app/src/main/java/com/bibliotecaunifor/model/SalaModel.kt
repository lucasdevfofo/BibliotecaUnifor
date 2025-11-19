package com.bibliotecaunifor.model

data class Sala(
    val id: String = "",
    val nome: String = "",
    val capacidade: Int = 0,
    val localizacao: String = "",
    val recursos: List<String> = emptyList(),
    val disponivel: Boolean = true
)