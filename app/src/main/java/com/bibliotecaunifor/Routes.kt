package com.bibliotecaunifor

sealed class Route(val path: String) {
    data object TelaInicial : Route("tela_inicial")
    data object Login : Route("tela_login")
    data object Cadastro : Route("tela_cadastro")

    data object EsqueceuSenha : Route("esqueceu_senha")

    data object  EmailRedefinicao : Route("email-rede")
    data object SalasDisponiveis : Route("tela_salas_disponiveis")
    data object Notificacoes : Route("tela_notificacoes")

    data object ReservaSala : Route("reserva_sala/{salaNome}")

    data object ReservaConfirmada : Route("tela_reserva_confirmada")

    data object PerfilAluno : Route("tela_perfil_aluno")

    data object AlugarLivros : Route("alugar_livro/{livroNome}")

    data object CatalogoLivros : Route("tela_catalogo_livros")

    data object ReservasRealizadas : Route("tela_reservas_realizadas")

    data object EditarReserva : Route("tela_editar_reserva/{salaNome}")







}

