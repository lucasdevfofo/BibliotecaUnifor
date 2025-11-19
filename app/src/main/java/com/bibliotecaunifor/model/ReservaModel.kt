package com.bibliotecaunifor.model

data class Reserva(
    val id: String = "",
    val usuarioId: String = "",
    val usuarioNome: String = "",
    val usuarioMatricula: String = "",
    val salaId: String = "",
    val salaNome: String = "",
    val data: String = "",
    val horarioInicio: String = "",
    val horarioFim: String = "",
    val status: String = "pendente",
    val dataCriacao: String = "",
    val dataAtualizacao: String = ""
)