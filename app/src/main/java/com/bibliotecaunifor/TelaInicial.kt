package com.bibliotecaunifor

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import androidx.navigation.NavType

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

    NavHost(
        navController = navController,
        startDestination = Route.TelaInicial.path
    ) {
        composable(Route.TelaInicial.path) {
            TelaInicial(
                onLoginClick = { navController.navigate(Route.Login.path) },
                onCadastroClick = { navController.navigate(Route.Cadastro.path) }
            )
        }

        composable(Route.Login.path) {
            TelaLogin(
                onNavigateUp = { navController.popBackStack() },
                onCadastroClick = { navController.navigate(Route.Cadastro.path) },
                onEsqueceuSenhaClick = { navController.navigate(Route.EsqueceuSenha.path) },
                onEntrarClick = { navController.navigate(Route.SalasDisponiveis.path) }
            )

        }
        composable(Route.Notificacoes.path) {
            TelaNotificacoes(navController)
        }

        composable(Route.MenuLateral.path) {
            MenuLateral(navController = navController)
        }

        composable(Route.Acessibilidade.path) {
            TelaAcessibilidade(navController = navController)
        }


        composable(Route.Cadastro.path) {
            TelaCadastro(
                onNavigateUp = { navController.popBackStack() },
                onCadastrarClick = {
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

        composable(Route.SalasDisponiveis.path) {
            TelaSalasDisponiveis(
                navController = navController,
                onVoltarClick = { navController.popBackStack() },
                onSalaClick = { sala ->
                    navController.navigate("reserva_sala/$sala")
                })
        }
        composable(
            Route.Comunicados.path,
            arguments = listOf(
                navArgument("titulo") { type = NavType.StringType },
                navArgument("mensagem") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo")
            val mensagem = backStackEntry.arguments?.getString("mensagem")
            TelaComunicados(navController, titulo, mensagem)
        }
        composable(
            route = "reserva_sala/{salaNome}",
            arguments = listOf(navArgument("salaNome") { defaultValue = "Sala" })
        ) { backStackEntry ->
            val salaNome = backStackEntry.arguments?.getString("salaNome") ?: "Sala"
            TelaReservaSala(navController = navController, salaNome = salaNome)
        }

        composable(Route.ReservaConfirmada.path) {
            TelaReservaConfirmada(navController = navController)
        }
        composable(Route.PerfilAluno.path) {
            TelaPerfilAluno(navController)
        }
        composable(Route.EditarUsuario.path) {
            TelaEditarUsuario(navController)
        }

        composable(Route.CatalogoLivros.path) {
            TelaCatalogoLivros(navController)
        }
        composable("alugar_livro/{livroNome}") { backStackEntry ->
            val livroNome = backStackEntry.arguments?.getString("livroNome") ?: ""
            AlugarLivros(navController, livroNome)
        }
        composable(Route.ReservasRealizadas.path) {
            TelaReservasRealizadas(navController)
        }
        composable(Route.EditarReserva.path) { backStackEntry ->
            val salaNome = backStackEntry.arguments?.getString("salaNome") ?: "Sala 01"
            EditarReserva(navController = navController, salaNome = salaNome)
        }
        composable(
            route = "descricaoLivro/{titulo}/{descricao}/{genero}/{autor}/{disponibilidade}",
        ) { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
            val descricao = backStackEntry.arguments?.getString("descricao")
            val genero = backStackEntry.arguments?.getString("genero")
            val autor = backStackEntry.arguments?.getString("autor")
            val disponibilidade = backStackEntry.arguments?.getString("disponibilidade")

            TelaDescricaoLivro(
                navController,
                tituloLivro = titulo,
                descricao = descricao,
                genero = genero,
                autor = autor,
                disponibilidade = disponibilidade
            )
        }
        composable(Route.HistoricoReservas.path) {
            TelaHistoricoReservas(navController)
        }
        composable(Route.TelaRenovarLivro.path) {
            TelaRenovarLivro(navController)
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
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
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
                Text(
                    text = "Login",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
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
                Text(
                    text = "Cadastre-se",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}
