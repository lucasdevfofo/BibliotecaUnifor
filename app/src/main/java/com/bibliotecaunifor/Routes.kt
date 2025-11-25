// Routes.kt
package com.bibliotecaunifor

sealed class Route(val path: String) {
    // ROTAS DE AUTENTICAÇÃO E INICIAL
    object TelaInicial : Route("tela_inicial")
    object Login : Route("tela_login")
    object Cadastro : Route("tela_cadastro")
    object EsqueceuSenha : Route("esqueceu_senha")
    object EmailRedefinicao : Route("email_redefinicao")

    // ROTAS DO ADMIN
    object TelaCadastroAdm : Route("tela_cadastro_admin")
    object TelaInicialAdmin : Route("tela_inicial_admin")
    object TelaAdminGerenciarSalas : Route("tela_admin_gerenciar_salas")
    object TelaAdminGerenciarMesas : Route("tela_admin_gerenciar_mesas/{salaNome}")
    object TelaAdminEditarLivro : Route("tela_admin_editar_livro")
    object TelaAdminGerenciarUsuarios : Route("tela_admin_gerenciar_usuarios")
    object TelaAdminEditarUsuario : Route("tela_admin_editar_usuario/{nomeUsuario}")
    object TelaAdminCadastrarUsuario : Route("tela_admin_cadastrar_usuario")
    object TelaAdminReservasRealizadas : Route("tela_admin_reservas_realizadas")
    object TelaRelatorioReservas : Route("relatorio_reservas") // NOVA ROTA
    object TelaAdminEditarPerfil : Route("tela_admin_editar_perfil")
    object TelaPerfilAdmin : Route("tela_admin_perfil")
    object TelaCatalogoLivrosAdmin : Route("catalogo_livros_admin")
    object TelaAdicionarLivroAdmin : Route("adicionar_livro_admin")
    object Comunicados : Route("comunicados")
    object ComunicadoEnviado : Route("comunicado_enviado")
    object RegrasDoSistema : Route("regras_do_sistema")
    object TelaAdminCadastrarSala : Route("tela_admin_cadastrar_sala")

    // ROTAS DO USUÁRIO/ALUNO
    object SalasDisponiveis : Route("tela_salas_disponiveis")
    object ReservaConfirmada : Route("tela_reserva_confirmada")
    object PerfilAluno : Route("tela_perfil_aluno")
    object EditarUsuario : Route("tela_editar_usuario")
    object ReservasRealizadas : Route("tela_reservas_realizadas")
    object HistoricoReservas : Route("tela_historico_reservas")
    object Notificacoes : Route("tela_notificacoes")
    object MenuLateral : Route("menu_lateral")
    object TelaChatbotAdmin : Route("tela_chatbot")
    object TelaNotificacoesAdmin : Route(path = "tela_not_admin")

    // NOVAS ROTAS DO CHATBOT
    object TelaChatbotUsuario : Route("tela_chatbot_usuario")

    // ROTAS DE LIVROS E CATÁLOGO
    object CatalogoLivros : Route("tela_catalogo_livros")
    object TelaRenovarLivro : Route("tela_renovar_livro")
    object TelaDescricaoLivro : Route("descricao_livro")

    // Rotas com parâmetros - FUNÇÕES AUXILIARES
    companion object {
        fun reservaSala(salaNome: String) = "reserva_sala/$salaNome"
        fun adminGerenciarMesas(salaNome: String) = "tela_admin_gerenciar_mesas/$salaNome"
        fun alugarLivro(livroNome: String) = "alugar_livro/$livroNome"
        fun editarReserva(salaNome: String) = "tela_editar_reserva/$salaNome"
        fun adminEditarUsuario(nomeUsuario: String) = "tela_admin_editar_usuario/$nomeUsuario"
        fun descricaoLivro(titulo: String, descricao: String = "", genero: String = "", autor: String = "", disponibilidade: String = "") =
            "descricao_livro/$titulo/$descricao/$genero/$autor/$disponibilidade"
        fun comunicados(titulo: String, mensagem: String) = "comunicados/$titulo/$mensagem"
    }
}