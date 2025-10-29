package com.bibliotecaunifor


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme


import com.bibliotecaunifor.TelaLogin
import com.bibliotecaunifor.EsqueceuSenhaScreen
// 🚩 CORREÇÃO 1: Adicionar a importação de TelaCadastro
import com.bibliotecaunifor.TelaCadastro


class TelaInicialActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BibliotecaUniforTheme {
                AppNavigation()
            }
        }
    }
}

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
                onEsqueceuSenhaClick = { navController.navigate(Route.EsqueceuSenha.path) }
            )
        }

        composable(Route.Cadastro.path) {
            TelaCadastro(navController = navController)
        }

        composable(Route.EsqueceuSenha.path) {
            EsqueceuSenhaScreen(
                onNavigateUp = { navController.popBackStack() },
                onEnviarClick = { email ->
                    navController.navigate(Route.EmailRedefinicao.path)
                }
            )
        }


    }
}


@Composable
fun TelaInicial(onLoginClick: () -> Unit,
                onCadastroClick: () -> Unit
) {
    val azulUnifor = Color(0xFF004AF5)
    val cinzaBotao = Color(0xFFD0D0D0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(azulUnifor)
            .padding(20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_unifor),
            contentDescription = "Unifor Mobile",
            modifier = Modifier
                .height(36.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Bem vindo ao aplicativo da\nBiblioteca da UNIFOR",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        Image(
            painter = painterResource(id = R.drawable.mascote_mesa),
            contentDescription = "Mascote Unifor",
            modifier = Modifier.height(150.dp).fillMaxWidth(),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onLoginClick,
            colors = ButtonDefaults.buttonColors(containerColor = cinzaBotao),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("Login", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onCadastroClick,
            colors = ButtonDefaults.buttonColors(containerColor = cinzaBotao),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Cadastre-se", color = Color.Black)
        }

    }
}
