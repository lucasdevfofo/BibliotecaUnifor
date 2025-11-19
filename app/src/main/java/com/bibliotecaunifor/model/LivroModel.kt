package com.bibliotecaunifor.model

data class Livro(
    val id: String = "",
    val titulo: String = "",
    val descricao: String = "",
    val genero: String = "",
    val autor: String = "",
    val disponibilidade: String = "",
    val isbn: String = "",
    val anoPublicacao: Int = 0,
    val editora: String = "",
    val paginas: Int = 0,
    val imagemUrl: String = "",
    val dataCadastro: String = ""
)