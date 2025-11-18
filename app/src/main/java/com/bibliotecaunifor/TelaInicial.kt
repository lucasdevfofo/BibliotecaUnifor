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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bibliotecaunifor.Adm.*
import com.bibliotecaunifor.Usuario.TelaNotificacoesUsuario
import com.bibliotecaunifor.cadastro.TelaCadastro
import com.bibliotecaunifor.cadastro.TelaCadastroAdm
import com.bibliotecaunifor.perfilAluno.TelaEditarUsuario
import com.bibliotecaunifor.perfilAluno.TelaPerfilAluno
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
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
                    // Depois de cadastrar, volta ao login
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
            route = "tela_admin_editar_livro/{tituloLivro}",
            arguments = listOf(navArgument("tituloLivro") { type = NavType.StringType })

        ) { backStackEntry ->
            val tituloLivro = backStackEntry.arguments?.getString("tituloLivro") ?: ""
            TelaAdminEditarLivro(
                navController = navController,
                tituloLivro = tituloLivro,
                onVoltarClick = { navController.popBackStack() },
                onNotificacoesClick = { navController.navigate(Route.TelaNotificacoesAdmin.path) },
                onMenuClick = {},
                onSalvarEdicaoClick = { _, _, _, _, _, _ ->
                    navController.popBackStack()
                }
            )
        }

        composable(Route.TelaAdminGerenciarUsuarios.path) {
            TelaAdminGerenciarUsuarios(
                navController = navController,
                onVoltarClick = { navController.popBackStack() },
                onNavSalasClick = { navController.navigate(Route.TelaAdminGerenciarSalas.path) },
                onNavReservasClick = { navController.navigate(Route.TelaAdminReservasRealizadas.path) },
                onNavUsuariosClick = {},
                onNavPerfilClick = { navController.navigate(Route.TelaPerfilAdmin.path) },
                onCadastrarUsuarioClick = { navController.navigate(Route.TelaAdminCadastrarUsuario.path) },
                onEditarUsuarioClick = { usuario ->
                    val nomeCodificado = URLEncoder.encode(usuario.nome, StandardCharsets.UTF_8.toString())
                    navController.navigate(Route.adminEditarUsuario(nomeCodificado))
                },
                onExcluirUsuarioClick = {},
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
                onNotificacoesClick = { navController.navigate(Route.TelaNotificacoesAdmin.path) },
                onMenuClick = {  },
                onConfirmarCadastro = { _, _, _, _, _, _ ->
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Route.TelaAdminEditarUsuario.path,
            arguments = listOf(navArgument("nomeUsuario") { type = NavType.StringType })
        ) { backStackEntry ->
            val nomeUsuario = backStackEntry.arguments?.getString("nomeUsuario") ?: ""
            TelaAdminEditarUsuario(
                nomeUsuario = nomeUsuario,
                onVoltarClick = { navController.popBackStack() },
                onNotificacoesClick = { navController.navigate(Route.TelaNotificacoesAdmin.path) },
                onMenuClick = {  },
                onConfirmarEdicao = { _, _, _, _, _, _ ->
                    navController.popBackStack()
                }
            )
        }

        composable(Route.TelaAdminReservasRealizadas.path) {
            TelaAdminReservasRealizadas(
                navController = navController,
                onVoltarClick = { navController.popBackStack() },
                onNotificacoesClick = { navController.navigate(Route.TelaNotificacoesAdmin.path) },
                onMenuClick = {},
                onDownloadRelatorioClick = { println("Baixando relatório...") },
                onNavSalasClick = { navController.navigate(Route.TelaAdminGerenciarSalas.path) },
                onNavReservasClick = {},
                onNavUsuariosClick = { navController.navigate(Route.TelaAdminGerenciarUsuarios.path) },
                onNavPerfilClick = { navController.navigate(Route.TelaPerfilAdmin.path) },
                currentRoute = Route.TelaAdminReservasRealizadas.path
            )
        }

        composable(Route.TelaAdminEditarPerfil.path) {
            TelaAdminEditarPerfil(
                navController = navController,
                onVoltarClick = { navController.popBackStack() },
                onNotificacoesClick = { navController.navigate(Route.TelaNotificacoesAdmin.path) },
                onMenuClick = {},
                onConfirmarEdicao = { _, _, _, _, _ ->
                    navController.popBackStack()
                }
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

        composable(Route.TelaAcessibilidadeAdmin.path) {
            TelaAcessibilidadeAdmin(
                navController = navController,
                onVoltarClick = { navController.popBackStack() },
                onNotificacoesClick = { navController.navigate(Route.TelaNotificacoesAdmin.path) },
                onMenuClick = {},
                onNavSalasClick = { navController.navigate(Route.TelaAdminGerenciarSalas.path) },
                onNavReservasClick = { navController.navigate(Route.TelaAdminReservasRealizadas.path) },
                onNavUsuariosClick = { navController.navigate(Route.TelaAdminGerenciarUsuarios.path) },
                onNavPerfilClick = { navController.navigate(Route.TelaPerfilAdmin.path) },
                currentRoute = Route.TelaAcessibilidadeAdmin.path
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
                currentRoute = Route.TelaCatalogoLivrosAdmin.path,
                onAdicionarLivroClick = { navController.navigate(Route.TelaAdicionarLivroAdmin.path) },
                onEditarLivroClick = { livro ->
                    val tituloCodificado = java.net.URLEncoder.encode(livro.titulo, "UTF-8")
                    navController.navigate("tela_admin_editar_livro/$tituloCodificado")
                },
                onExcluirLivroClick = { livro ->
                    println("Excluindo livro: ${livro.titulo}")
                }
            )
        }

        composable(Route.TelaAdicionarLivroAdmin.path) {
            TelaAdicionarLivroAdmin(navController = navController)
        }

        composable(Route.Comunicados.path) {
            ComunicadosScreen(
                onSendMessage = {
                    println("Comunicado enviado!")
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
            route = "reserva_sala/{salaNome}",
            arguments = listOf(navArgument("salaNome") { type = NavType.StringType })
        ) { backStackEntry ->
            val salaNome = backStackEntry.arguments?.getString("salaNome") ?: ""
            TelaReservaSala(navController = navController, salaNome = salaNome)
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

        composable(Route.TelaRenovarLivro.path) {
            TelaRenovarLivro(navController = navController)
        }

        composable(Route.Notificacoes.path) {
            TelaNotificacoesUsuario(
                navController = navController,
                onVoltarClick = { navController.popBackStack() }
            )
        }

        composable(Route.MenuLateral.path) {
            MenuLateral(navController = navController)
        }

        composable(Route.Acessibilidade.path) {
            TelaAcessibilidadeAluno(
                navController = navController,
                onNotificacoesClick = { navController.navigate(Route.Notificacoes.path) },
                onVoltarClick = { navController.popBackStack() },
                onMenuClick = {},
                onNavHomeClick = { navController.navigate(Route.SalasDisponiveis.path) },
                onNavReservasClick = { navController.navigate(Route.ReservasRealizadas.path) },
                onNavCatalogoClick = { navController.navigate(Route.CatalogoLivros.path) },
                onNavPerfilClick = { navController.navigate(Route.PerfilAluno.path) },
                currentRoute = Route.Acessibilidade.path
            )
        }

        composable(
            route = "alugar_livro/{livroNome}",
            arguments = listOf(navArgument("livroNome") { type = NavType.StringType })
        ) { backStackEntry ->
            val livroNome = backStackEntry.arguments?.getString("livroNome") ?: ""
            AlugarLivros(navController = navController, livroNome = livroNome)
        }

        composable(
            route = "tela_editar_reserva/{salaNome}",
            arguments = listOf(navArgument("salaNome") { type = NavType.StringType })
        ) { backStackEntry ->
            val salaNome = backStackEntry.arguments?.getString("salaNome") ?: ""
            EditarReserva(navController = navController, salaNome = salaNome)
        }

        composable(
            route = "descricao_livro/{titulo}/{descricao}/{genero}/{autor}/{disponibilidade}",
            arguments = listOf(
                navArgument("titulo") { type = NavType.StringType },
                navArgument("descricao") { type = NavType.StringType },
                navArgument("genero") { type = NavType.StringType },
                navArgument("autor") { type = NavType.StringType },
                navArgument("disponibilidade") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val titulo = Uri.decode(backStackEntry.arguments?.getString("titulo") ?: "")
            val descricao = Uri.decode(backStackEntry.arguments?.getString("descricao") ?: "")
            val genero = Uri.decode(backStackEntry.arguments?.getString("genero") ?: "")
            val autor = Uri.decode(backStackEntry.arguments?.getString("autor") ?: "")
            val disponibilidade = Uri.decode(backStackEntry.arguments?.getString("disponibilidade") ?: "")

            TelaDescricaoLivro(
                navController = navController,
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
            TelaChatbot(
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