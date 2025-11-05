package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController

@Composable
fun TelaHomeAdmin(navController: NavController) {
    val azulUnifor = Color(0xFF004AF5)
    val cinzaBotao = Color(0xFF3A3A3A)
    val branco = Color.White
    val menuAberto = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            // 🔹 Header igual às outras telas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.livros),
                    contentDescription = "Imagem de fundo",
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(branco)
                        .align(Alignment.TopCenter),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(12.dp))

                    // Logo
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo UNIFOR",
                        modifier = Modifier.size(42.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Ícones de topo
                    IconButton(
                        onClick = { navController.navigate(Route.Notificacoes.path) }
                    ) {
                        Icon(
                            Icons.Outlined.Notifications,
                            contentDescription = "Notificações",
                            tint = Color.Black
                        )
                    }

                    IconButton(
                        onClick = { menuAberto.value = !menuAberto.value }
                    ) {
                        Icon(
                            Icons.Outlined.Menu,
                            contentDescription = "Menu lateral",
                            tint = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                }

                // Texto central
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Painel Administrativo",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Gerencie as salas e mesas da biblioteca",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp
                    )
                }
            }
        },
        bottomBar = {
            // 🔹 Botão fixo de cadastrar
            Button(
                onClick = { /* abrir tela de cadastro de sala/mesa futuramente */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = cinzaBotao)
            ) {
                Text(
                    text = "CADASTRAR NOVA MESA / SALA",
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Bem-vindo, Administrador!",
                    fontSize = 22.sp,
                    color = azulUnifor,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Aqui você poderá gerenciar as reservas, as mesas, e o catálogo de livros disponíveis na biblioteca.",
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(40.dp))

                // 🔹 Aqui depois você pode listar mesas/salas cadastradas
                Text(
                    text = "Listagem de mesas e salas aparecerá aqui...",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            // 🔹 Menu lateral (reutilizando o mesmo componente)
            if (menuAberto.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                        .zIndex(0.5f)
                        .clickable { menuAberto.value = false }
                )

                MenuLateral(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .zIndex(1f)
                        .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)),
                    navController = navController
                )
            }
        }
    }
}
