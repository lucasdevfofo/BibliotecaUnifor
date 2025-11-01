package com.bibliotecaunifor

sealed class Route(val path: String) {
    data object TelaInicial : Route("tela_inicial")
    data object Login : Route("tela_login")
    data object Cadastro : Route("tela_cadastro")

    data object EsqueceuSenha : Route("esqueceu_senha")

    data object  EmailRedefinicao : Route("email-rede")
    data object SalasDisponiveis : Route("tela_salas_disponiveis")
    data object Notificacoes : Route("tela_notificacoes")


}

