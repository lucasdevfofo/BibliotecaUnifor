package com.bibliotecaunifor

sealed class Route(val path: String) {
    // --- Rotas de Autenticação / Início ---
    data object TelaInicial : Route("tela_inicial")
    data object Login : Route("tela_login")
    data object Cadastro : Route("tela_cadastro")
    data object EsqueceuSenha : Route("esqueceu_senha")
    data object EmailRedefinicao : Route("email_redefinicao")

    // --- Rotas Comuns / Aluno ---
    data object SalasDisponiveis : Route("tela_salas_disponiveis")
    data object Notificacoes : Route("tela_notificacoes")
    data object MenuLateral : Route("menu_lateral")
    data object ReservaSala : Route("reserva_sala/{salaNome}")
    data object ReservaConfirmada : Route("tela_reserva_confirmada")
    data object PerfilAluno : Route("tela_perfil_aluno")
    data object AlugarLivros : Route("alugar_livro/{livroNome}")
    data object Comunicados : Route("comunicados/{titulo}/{mensagem}")
    data object CatalogoLivros : Route("tela_catalogo_livros")
    data object ReservasRealizadas : Route("tela_reservas_realizadas")
    data object EditarUsuario : Route("tela_editar_usuario")
    data object EditarReserva : Route("tela_editar_reserva/{salaNome}")
    data object DescricaoLivro : Route("descricao_livro/{tituloLivro}")
    data object Acessibilidade : Route("acessibilidade")
    data object ComunicadoEnviado : Route("comunicado_enviado")
    data object TelaRenovarLivro : Route("tela_renovar_livro")
    data object RegrasDoSistema : Route("regras_do_sistema")
    data object RenovarLivros : Route("renovar_livros")
    // --- Rotas Comuns / Aluno ---
    data object HistoricoReservas : Route("tela_historico_reservas")


    // --- NOVAS ROTAS DO ADMINISTRADOR ---
    data object TelaAdminGerenciarSalas : Route("tela_admin_gerenciar_salas")
    data object TelaAdminGerenciarMesas : Route("tela_admin_gerenciar_mesas/{salaNome}")
    data object TelaCadastroAdm : Route("tela_cadastro_admin")
}