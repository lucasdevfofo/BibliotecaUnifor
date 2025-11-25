package com.bibliotecaunifor

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bibliotecaunifor.Adm.*
import com.bibliotecaunifor.Usuario.TelaNotificacoesUsuario
import com.bibliotecaunifor.cadastro.TelaCadastro
import com.bibliotecaunifor.cadastro.TelaCadastroAdm
import com.bibliotecaunifor.model.UsuarioModel
import com.bibliotecaunifor.perfilAluno.TelaEditarUsuario
import com.bibliotecaunifor.perfilAluno.TelaPerfilAluno
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import com.bibliotecaunifor.viewmodel.UsuarioAdminViewModel

class TelaInicialActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BibliotecaUniforTheme {
                AppNavigation()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.TelaInicial.path) {
        composable(Route.TelaInicial.path) {
            TelaInicial(
                onLoginClick = { navController.navigate(Route.Login.path) },
                onCadastroClick = { navController.navigate(Route.Cadastro.path) }
            )
        }

        composable(Route.Login.path) {
            TelaLogin(
                onNavigateUp = { navController.popBackStack() },
                onCadastroClick = { isAdmin ->
                    if (isAdmin) navController.navigate(Route.TelaCadastroAdm.path)
                    else navController.navigate(Route.Cadastro.path)
                },
                onEsqueceuSenhaClick = { navController.navigate(Route.EsqueceuSenha.path) },
                onEntrarClick = { isAdmin ->
                    if (isAdmin) navController.navigate(Route.TelaAdminGerenciarSalas.path)
                    else navController.navigate(Route.SalasDisponiveis.path)
                }
            )
        }

        composable(Route.Cadastro.path) {
            TelaCadastro(
                onNavigateUp = { navController.popBackStack() },
                onIrParaAdminClick = { navController.navigate(Route.TelaCadastroAdm.path) },
                onNavigateSuccess = {
                    navController.popBackStack(Route.Login.path, inclusive = false)
                }
            )
        }

        composable(Route.EsqueceuSenha.path) {
            EsqueceuSenhaScreen(
                onNavigateUp = { navController.popBackStack() },
                onEnviarClick = { email ->
                    navController.navigate(Route.EmailRedefinicao.path)
                }
            )
        }

        composable(Route.EmailRedefinicao.path) {
            EmailRedefinicaoScreen(
                onNavigateBackToLogin = {
                    navController.popBackStack(Route.Login.path, inclusive = false)
                }
            )
        }

        composable(Route.TelaCadastroAdm.path) {
            TelaCadastroAdm(
                onNavigateUp = { navController.popBackStack() },
                onIrParaUsuarioClick = { navController.navigate(Route.Cadastro.path) },
                onNavigateSuccess = {
                    navController.popBackStack(Route.Login.path, inclusive = false)
                }
            )
        }

        composable(Route.TelaAdminGerenciarSalas.path) {
            TelaAdminGerenciarSalas(
                navController = navController,
                onVoltarClick = { navController.popBackStack() },
                onNotificacoesClick = { navController.navigate(Route.TelaNotificacoesAdmin.path) },
                onMenuClick = { },
                onGerenciarSalaClick = { salaNome ->
                    navController.navigate(Route.adminGerenciarMesas(salaNome))
                },
                onNavHomeClick = {},
                onNavHistoricoClick = { navController.navigate(Route.TelaAdminReservasRealizadas.path) },
                onNavListasClick = { navController.navigate(Route.TelaAdminGerenciarUsuarios.path) },
                onNavPerfilClick = { navController.navigate(Route.TelaPerfilAdmin.path) },
                currentRoute = Route.TelaAdminGerenciarSalas.path
            )
        }

        composable(
            route = Route.TelaAdminGerenciarMesas.path,
            arguments = listOf(navArgument("salaNome") { type = NavType.StringType })
        ) { backStackEntry ->
            val salaNome = backStackEntry.arguments?.getString("salaNome") ?: ""
            TelaAdminGerenciarMesas(
                salaNome = salaNome,
                onVoltarClick = { navController.popBackStack() },
                onEditarMesaClick = { mesaNome -> },
                onExcluirMesaClick = { mesaNome -> },
                onCadastrarNovaMesaClick = { mesaNome -> }
            )
        }

        composable(
            route = "${Route.TelaAdminEditarLivro.path}/{livroId}",
            arguments = listOf(navArgument("livroId") { type = NavType.StringType })
        ) { backStackEntry ->
            val livroId = backStackEntry.arguments?.getString("livroId") ?: ""
            TelaAdminEditarLivro(
                navController = navController,
                livroId = livroId
            )
        }

        composable(Route.TelaAdminGerenciarUsuarios.path) {
            val viewModel: UsuarioAdminViewModel = viewModel()

            TelaAdminGerenciarUsuarios(
                navController = navController,
                onVoltarClick = { navController.popBackStack() },
                onNavSalasClick = { navController.navigate(Route.TelaAdminGerenciarSalas.path) },
                onNavReservasClick = { navController.navigate(Route.TelaAdminReservasRealizadas.path) },
                onNavUsuariosClick = {},
                onNavPerfilClick = { navController.navigate(Route.TelaPerfilAdmin.path) },
                onCadastrarUsuarioClick = { navController.navigate(Route.TelaAdminCadastrarUsuario.path) },
                onEditarUsuarioClick = { uid, usuario ->
                    navController.navigate("tela_admin_editar_usuario/$uid")
                },
                onExcluirUsuarioClick = { uid, usuario ->
                    viewModel.excluirUsuario(uid)
                },
                currentRoute = Route.TelaAdminGerenciarUsuarios.path
            )
        }

        composable(Route.TelaAdminCadastrarSala.path) {
            TelaAdminCadastrarSala(
                navController = navController,
                onVoltarClick = { navController.popBackStack() },
                onNotificacoesClick = {
                    navController.navigate(Route.TelaNotificacoesAdmin.path)
                },
                onMenuClick = {  },
                onNavHomeClick = {
                    navController.navigate(Route.TelaAdminGerenciarSalas.path)
                },
                onNavHistoricoClick = {
                    navController.navigate(Route.TelaAdminReservasRealizadas.path)
                },
                onNavListasClick = {
                    navController.navigate(Route.TelaAdminGerenciarUsuarios.path)
                },
                onNavPerfilClick = {
                    navController.navigate(Route.TelaPerfilAdmin.path)
                },
                currentRoute = Route.TelaAdminCadastrarSala.path
            )
        }

        composable(Route.TelaAdminCadastrarUsuario.path) {
            TelaAdminCadastrarUsuario(
                onVoltarClick = { navController.popBackStack() },
                onCadastroSucesso = { navController.popBackStack() }
            )
        }

        composable(
            route = "tela_admin_editar_usuario/{uid}",
            arguments = listOf(navArgument("uid") { type = NavType.StringType })
        ) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid") ?: ""
            TelaAdminEditarUsuario(
                uid = uid,
                onVoltarClick = { navController.popBackStack() },
                onEdicaoSucesso = { navController.popBackStack() }
            )
        }

        composable(Route.TelaAdminReservasRealizadas.path) {
            TelaAdminReservasRealizadas(
                navController = navController,
                onVoltarClick = { navController.popBackStack() },
                onNotificacoesClick = { navController.navigate(Route.TelaNotificacoesAdmin.path) },
                onMenuClick = {},
                onDownloadRelatorioClick = { navController.navigate(Route.TelaRelatorioReservas.path) },
                onNavSalasClick = { navController.navigate(Route.TelaAdminGerenciarSalas.path) },
                onNavReservasClick = {},
                onNavUsuariosClick = { navController.navigate(Route.TelaAdminGerenciarUsuarios.path) },
                onNavPerfilClick = { navController.navigate(Route.TelaPerfilAdmin.path) },
                currentRoute = Route.TelaAdminReservasRealizadas.path
            )
        }
        composable(Route.TelaRelatorioReservas.path) {
            TelaRelatorioReservas(
                navController = navController,
                onVoltarClick = { navController.popBackStack() }
            )
        }

        composable(Route.TelaAdminEditarPerfil.path) {
            TelaAdminEditarPerfil(
                navController = navController,
                onVoltarClick = { navController.popBackStack() },
                onNotificacoesClick = { navController.navigate(Route.TelaNotificacoesAdmin.path) },
                onMenuClick = {}
            )
        }

        composable(Route.TelaPerfilAdmin.path) {
            TelaPerfilAdmin(
                navController = navController,
                onVoltarClick = { navController.popBackStack() },
                onNotificacoesClick = { navController.navigate(Route.TelaNotificacoesAdmin.path) },
                onMenuClick = {},
                onEditarClick = { navController.navigate(Route.TelaAdminEditarPerfil.path) },
                onNavSalasClick = { navController.navigate(Route.TelaAdminGerenciarSalas.path) },
                onNavReservasClick = { navController.navigate(Route.TelaAdminReservasRealizadas.path) },
                onNavUsuariosClick = { navController.navigate(Route.TelaAdminGerenciarUsuarios.path) },
                onNavPerfilClick = {},
                currentRoute = Route.TelaPerfilAdmin.path
            )
        }



        composable(Route.TelaCatalogoLivrosAdmin.path) {
            TelaCatalogoLivrosAdmin(
                navController = navController,
                onVoltarClick = { navController.popBackStack() },
                onNotificacoesClick = { navController.navigate(Route.TelaNotificacoesAdmin.path) },
                onMenuClick = {},
                onNavSalasClick = { navController.navigate(Route.TelaAdminGerenciarSalas.path) },
                onNavReservasClick = { navController.navigate(Route.TelaAdminReservasRealizadas.path) },
                onNavUsuariosClick = { navController.navigate(Route.TelaAdminGerenciarUsuarios.path) },
                onNavPerfilClick = { navController.navigate(Route.TelaPerfilAdmin.path) },
                currentRoute = Route.TelaCatalogoLivrosAdmin.path
            )
        }

        composable(Route.TelaAdicionarLivroAdmin.path) {
            TelaAdicionarLivroAdmin(navController = navController)
        }

        composable(Route.Comunicados.path) {
            ComunicadosScreen(
                navController = navController,
                onSendMessageSuccess = {
                    // Quando enviar com sucesso, vai para a tela de confirmação
                    navController.navigate(Route.ComunicadoEnviado.path)
                }
            )
        }

        composable(Route.ComunicadoEnviado.path) {
            ComunicadoEnviadoScreen(
                onNavigateBackToLogin = {
                    navController.popBackStack(Route.TelaNotificacoesAdmin.path, inclusive = false)
                }
            )
        }

        composable(Route.RegrasDoSistema.path) {
            RegrasDoSistema(
                navController = navController,
                onVoltarClick = { navController.popBackStack() },
                onNotificacoesClick = { navController.navigate(Route.TelaNotificacoesAdmin.path) },
                onNavSalasClick = { navController.navigate(Route.TelaAdminGerenciarSalas.path) },
                onNavReservasClick = { navController.navigate(Route.TelaAdminReservasRealizadas.path) },
                onNavUsuariosClick = { navController.navigate(Route.TelaAdminGerenciarUsuarios.path) },
                onNavPerfilClick = { navController.navigate(Route.TelaPerfilAdmin.path) },
                currentRoute = Route.RegrasDoSistema.path
            )
        }

        composable(Route.SalasDisponiveis.path) {
            TelaSalasDisponiveis(
                navController = navController,
                onVoltarClick = { navController.popBackStack() },
                onSalaClick = { sala ->
                    navController.navigate(Route.reservaSala(sala))
                }
            )
        }

        composable(
            route = "reserva_sala/{salaId}",
            arguments = listOf(navArgument("salaId") { type = NavType.StringType })
        ) { backStackEntry ->
            val salaId = backStackEntry.arguments?.getString("salaId") ?: ""
            TelaReservaSala(navController = navController, salaId = salaId)
        }

        composable(Route.ReservaConfirmada.path) {
            TelaReservaConfirmada(navController = navController)
        }

        composable(Route.PerfilAluno.path) {
            TelaPerfilAluno(navController = navController)
        }

        composable(Route.EditarUsuario.path) {
            TelaEditarUsuario(navController = navController)
        }

        composable(Route.CatalogoLivros.path) {
            TelaCatalogoLivros(navController = navController)
        }

        composable(Route.ReservasRealizadas.path) {
            TelaReservasRealizadas(navController = navController)
        }

        composable(Route.HistoricoReservas.path) {
            TelaHistoricoReservas(navController = navController)
        }

        composable(
            route = "renovar_livro/{id}/{titulo}/{dataAtual}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("titulo") { type = NavType.StringType },
                navArgument("dataAtual") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            // Decodifica caso venha com caracteres especiais
            val titulo = Uri.decode(backStackEntry.arguments?.getString("titulo") ?: "")
            val dataAtual = backStackEntry.arguments?.getLong("dataAtual") ?: 0L

            TelaRenovarLivro(
                navController = navController,
                aluguelId = id,
                tituloLivro = titulo,
                dataDevolucaoAtualMillis = dataAtual
            )
        }
        // ------------------------------------------

        composable(Route.Notificacoes.path) {
            TelaNotificacoesUsuario(
                navController = navController,
                onVoltarClick = { navController.popBackStack() }
            )
        }

        composable(Route.MenuLateral.path) {
            MenuLateral(navController = navController)
        }


        composable(
            route = "alugar_livro/{livroNome}",
            arguments = listOf(navArgument("livroNome") { type = NavType.StringType })
        ) { backStackEntry ->
            val livroNome = backStackEntry.arguments?.getString("livroNome") ?: ""
            AlugarLivros(navController = navController, livroNome = livroNome)
        }

        composable(
            route = "tela_editar_reserva/{reservaId}",
            arguments = listOf(navArgument("reservaId") { type = NavType.StringType })
        ) { backStackEntry ->
            val reservaId = backStackEntry.arguments?.getString("reservaId") ?: ""
            EditarReserva(navController = navController, reservaId = reservaId)
        }

        composable(
            // 1. Adicionei o {id} logo no começo da rota
            route = "descricao_livro/{id}/{titulo}/{descricao}/{genero}/{autor}/{disponibilidade}",
            arguments = listOf(
                // 2. Adicionei o argumento do ID
                navArgument("id") { type = NavType.StringType },
                navArgument("titulo") { type = NavType.StringType },
                navArgument("descricao") { type = NavType.StringType },
                navArgument("genero") { type = NavType.StringType },
                navArgument("autor") { type = NavType.StringType },
                navArgument("disponibilidade") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            // 3. Recupera o ID (Geralmente ID do Firebase não precisa de decode, mas se quiser pode usar)
            val id = backStackEntry.arguments?.getString("id") ?: ""

            val titulo = Uri.decode(backStackEntry.arguments?.getString("titulo") ?: "")
            val descricao = Uri.decode(backStackEntry.arguments?.getString("descricao") ?: "")
            val genero = Uri.decode(backStackEntry.arguments?.getString("genero") ?: "")
            val autor = Uri.decode(backStackEntry.arguments?.getString("autor") ?: "")
            val disponibilidade = Uri.decode(backStackEntry.arguments?.getString("disponibilidade") ?: "")

            TelaDescricaoLivro(
                navController = navController,
                livroId = id, // <--- 4. Passando o ID para a tela!
                tituloLivro = titulo,
                descricao = descricao,
                genero = genero,
                autor = autor,
                disponibilidade = disponibilidade
            )
        }

        composable(
            route = "comunicados/{titulo}/{mensagem}",
            arguments = listOf(
                navArgument("titulo") { type = NavType.StringType },
                navArgument("mensagem") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
            val mensagem = backStackEntry.arguments?.getString("mensagem") ?: ""
            TelaComunicados(
                navController = navController,
                tituloNotificacao = titulo,
                mensagemCorpo = mensagem
            )
        }

        composable(Route.TelaChatbotAdmin.path) {
            TelaChatBotAdmin(
                onVoltarClick = { navController.popBackStack() }
            )
        }
        composable(Route.TelaChatbotUsuario.path) {
            TelaChatBotUsuario(
                navController = navController,
                onVoltarClick = { navController.popBackStack() }
            )
        }

        composable(Route.TelaNotificacoesAdmin.path) {
            TelaNotificacoesAdmin(
                navController = navController,
                onVoltarClick = { navController.popBackStack() },
                onNotificacoesClick = { },
                onEnviarComunicadoClick = { navController.navigate(Route.Comunicados.path) }
            )
        }
        composable(
            route = "agendar_aluguel/{id}/{titulo}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("titulo") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""

            TelaAgendarAluguel(
                navController = navController,
                livroId = id,
                tituloLivro = titulo
            )
        }
    }
}

@Composable
fun TelaInicial(
    onLoginClick: () -> Unit,
    onCadastroClick: () -> Unit
) {
    val azulUnifor = Color(0xFF004AF5)
    val cinzaBotao = Color(0xFFD0D0D0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(azulUnifor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_tela_inicial_e_cadastro),
                contentDescription = "Logo Unifor",
                modifier = Modifier
                    .width(260.dp)
                    .padding(top = 40.dp, bottom = 16.dp),
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = "Bem vindo ao aplicativo da\nBiblioteca da UNIFOR",
                fontSize = 24.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.mascote_mesa),
                contentDescription = "Mascote Unifor",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 16.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onLoginClick,
                colors = ButtonDefaults.buttonColors(containerColor = cinzaBotao),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text("Login", color = Color.Black, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onCadastroClick,
                colors = ButtonDefaults.buttonColors(containerColor = cinzaBotao),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text("Cadastre-se", color = Color.Black, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}