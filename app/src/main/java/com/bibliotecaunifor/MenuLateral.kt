package com.bibliotecaunifor

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MenuLateral(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(0.7f)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_menu_lateral),
                    contentDescription = "Imagem topo",
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .align(Alignment.Start)
                )

                val links = listOf(
                    "Perfil",
                    "Catálogo de Livros",
                    "Acessibilidade",
                    "Lista de Salas",
                    "Reservar Mesa"
                )

                links.forEach { link ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color.Black.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(0.dp)
                            )
                            .clickable {
                                when (link) {
                                    "Perfil" -> navController.navigate(Route.PerfilAluno.path)
                                    "Catálogo de Livros" -> navController.navigate(Route.CatalogoLivros.path)
                                    "Acessibilidade" -> navController.navigate(Route.Acessibilidade.path)
                                    "Lista de Salas" -> navController.navigate(Route.ReservasRealizadas.path)
                                    "Reservar Mesa" -> navController.navigate(Route.SalasDisponiveis.path)
                                }
                            }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = link,
                            fontSize = 18.sp,
                            color = Color.Black.copy(alpha = 0.8f),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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
                            popUpTo(0)
                        }
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
}