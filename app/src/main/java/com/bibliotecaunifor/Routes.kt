package com.bibliotecaunifor

sealed class Route(val path: String) {
    // 🚀 Corrigido para 'object' (pode ser data object, mas object é suficiente)
    object TelaInicial : Route("tela_inicial")
    object Login : Route("tela_login")
    object Cadastro : Route("tela_cadastro")
    object EsqueceuSenha : Route("esqueceu_senha")
    // 🚀 Ajustado o path para ser consistente
    object EmailRedefinicao : Route("email_redefinicao")
}