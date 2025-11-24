package com.bibliotecaunifor.model

data class ConfiguracaoModel(
    val id: String = "regras_sistema",
    val limiteLivrosPorUsuario: Int = 2,
    val diasMaximoEmprestimo: Int = 7,
    val limiteSalasPorUsuario: Int = 2,
    val permiteRenovacao: Boolean = true,
    val diasRenovacao: Int = 7,
    val dataAtualizacao: String = ""
)