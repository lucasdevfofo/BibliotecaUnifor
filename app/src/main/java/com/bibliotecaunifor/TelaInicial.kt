package com.bibliotecaunifor

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
                onEntrarClick = { navController.navigate(Route.SalasDisponiveis.path) } // 👈 aqui está a navegação
            )

        }
        composable(Route.Notificacoes.path) {
            TelaNotificacoes(navController)
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

        // 🚀 NOVA ROTA — Tela Home / Salas Disponíveis
        composable(Route.SalasDisponiveis.path) {
            TelaSalasDisponiveis(
                navController = navController, // ✅ parâmetro adicionado
                onVoltarClick = { navController.popBackStack() },
                onSalaClick = { sala ->
                    // ✅ Abre a tela de reserva com o nome da sala
                    navController.navigate("reserva_sala/$sala")
                })
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
        composable(Route.AlugarLivros.path) {
            AlugarLivros(navController = navController)
        }


    }
}

// 🚀 Tela Inicial (sem mudanças)
@Composable
fun TelaInicial(
    onLoginClick: () -> Unit,
    onCadastroClick: () -> Unit
) {
    val azulUnifor = Color(0xFF004AF5)
    val cinzaBotao = Color(0xFFD0D0D0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(azulUnifor)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // 🔹 Logo ajustada (igual à TelaLogin)
        Image(
            painter = painterResource(id = R.drawable.logo_tela_inicial_e_cadastro),
            contentDescription = "Logo Unifor",
            modifier = Modifier
                .height(70.dp)
                .width(220.dp)
                .align(Alignment.Start),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 Texto de boas-vindas alinhado à esquerda
        Text(
            text = "Bem vindo ao aplicativo da\nBiblioteca da UNIFOR",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            lineHeight = 28.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Mascote centralizado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.mascote_mesa),
                contentDescription = "Mascote Unifor",
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth(0.8f),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 🔹 Botões “Login” e “Cadastre-se”
        Button(
            onClick = onLoginClick,
            colors = ButtonDefaults.buttonColors(containerColor = cinzaBotao),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Login", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onCadastroClick,
            colors = ButtonDefaults.buttonColors(containerColor = cinzaBotao),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Cadastre-se", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

