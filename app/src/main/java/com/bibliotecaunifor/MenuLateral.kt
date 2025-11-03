package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
            .padding(0.dp)
            .fillMaxHeight()
            .fillMaxWidth(0.6f)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // 🔹 Imagem do topo
            Image(
                painter = painterResource(id = R.drawable.logo_menu_lateral),
                contentDescription = "Imagem topo",
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .align(Alignment.Start)
            )

            // 🔹 Links do menu
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
                                "Lista de Salas" -> navController.navigate(Route.SalasDisponiveis.path)
                                // "Acessibilidade" e "Reservar Mesa" serão configurados depois
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

                Spacer(modifier = Modifier.height(0.dp))
            }
        }
    }
}
