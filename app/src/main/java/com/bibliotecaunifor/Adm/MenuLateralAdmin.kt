package com.bibliotecaunifor.Adm

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.R
import com.bibliotecaunifor.Route
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import androidx.compose.ui.zIndex

@Composable
fun MenuLateralAdmin(
    modifier: Modifier = Modifier,
    navController: NavController,
    onLinkClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(0.75f)
            .background(Color.White)
            .zIndex(5f),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(Route.TelaAdminGerenciarSalas.path) {
                            popUpTo(0) { inclusive = true }
                        }
                        onLinkClick() // CHAMA A FUNÇÃO PARA FECHAR O MENU
                    }
                    .padding(start = 5.dp, top = 5.dp, bottom = 5.dp)
                    .align(Alignment.Start)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_menu_lateral),
                    contentDescription = "Logo Unifor",
                    modifier = Modifier
                        .size(170.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterStart)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            val linksAdmin = listOf(
                "PERFIL" to Route.TelaPerfilAdmin.path,
                "CATÁLOGO DE LIVROS" to Route.TelaCatalogoLivrosAdmin.path,
                "ACECESSIBILIDADE" to Route.TelaAcessibilidadeAdmin.path,
                "REGRAS DO SISTEMA" to Route.RegrasDoSistema.path,
                "GERENCIAR USUÁRIOS" to Route.TelaAdminGerenciarUsuarios.path,
                "RESERVAS REALIZADAS" to Route.TelaAdminReservasRealizadas.path
            )

            linksAdmin.forEach { (label, route) ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.LightGray.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(0.dp)
                        )
                        .clickable {
                            navController.navigate(route) {
                                popUpTo(Route.TelaAdminGerenciarSalas.path) { inclusive = false }
                                launchSingleTop = true
                            }
                            onLinkClick()
                        }
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = label,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black.copy(alpha = 0.8f),
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Divider(
                color = Color.Black.copy(alpha = 0.15f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Button(
                onClick = {
                    navController.navigate(Route.Login.path) {
                        popUpTo(0) { inclusive = true }
                    }
                    onLinkClick()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "Sair",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MenuLateralAdminPreview() {
    BibliotecaUniforTheme {
        Box(modifier = Modifier.fillMaxSize().background(Color.LightGray)) {
            Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.2f)))

            MenuLateralAdmin(
                navController = rememberNavController(),
                onLinkClick = {},
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}