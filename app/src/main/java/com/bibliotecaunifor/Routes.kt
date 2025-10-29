package com.bibliotecaunifor

sealed class Route(val path: String) {
    data object TelaInicial : Route("tela_inicial")
    data object Login : Route("tela_login")
    data object Cadastro : Route("tela_cadastro")

    data object EsqueceuSenha : Route("esqueceu_senha")

    data object  EmailRedefinicao : Route("email-redefinicao")

    data object  MenuLateral : Route("menu-lateral")

    data object  ReservasRealizadas : Route("reservar-realizadas")

    data object  Notificacoes : Route("notificacoes")
}

