package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TelaReservaConfirmada(navController: NavController) {
    var menuAberto by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- Cabeçalho ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.livros),
                    contentDescription = "Imagem de fundo biblioteca",
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color.White)
                        .align(Alignment.TopCenter),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.navigate(Route.SalasDisponiveis.path) },
                        modifier = Modifier
                            .size(60.dp)
                            .pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.weight(0.63f))

                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo UNIFOR",
                        modifier = Modifier.size(40.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 16.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(
                        onClick = { navController.navigate(Route.Notificacoes.path) },
                        modifier = Modifier.size(40.dp).pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notificações",
                            tint = Color.Black,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    IconButton(
                        onClick = { menuAberto = !menuAberto },
                        modifier = Modifier.size(40.dp).pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            contentDescription = "Menu",
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(124.dp)
                        .align(Alignment.BottomCenter),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Reserve sua sala",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "Biblioteca Unifor",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // --- Ícone central ---
            Image(
                painter = painterResource(id = R.drawable.imagem_principal),
                contentDescription = "Reserva confirmada",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // --- Texto ---
            Text(
                text = "Reservado",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Lembre-se de chegar no horário agendado.",
                color = Color.Black,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            // --- Botão Voltar ---
            Button(
                onClick = { navController.navigate(Route.SalasDisponiveis.path) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E4C93)),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(48.dp)
            ) {
                Text("Voltar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.weight(1f))

            // --- Barra inferior ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painterResource(id = R.drawable.ic_home), "Home", tint = Color.Black)
                Icon(painterResource(id = R.drawable.ic_calendar), "Reservas", tint = Color.Gray)
                Icon(painterResource(id = R.drawable.ic_list), "Listas", tint = Color.Gray)
                Icon(painterResource(id = R.drawable.ic_user), "Perfil", tint = Color.Gray)
            }
        }

        if (menuAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .pointerHoverIcon(PointerIcon.Hand)
                    .clickable { menuAberto = false }
            )
            MenuLateral(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp))
            )
        }
    }
}
