package com.bibliotecaunifor

sealed class Route(val path: String) {
    data object TelaInicial : Route("tela_inicial")
    data object Login : Route("tela_login")
    data object Cadastro : Route("tela_cadastro")

}

