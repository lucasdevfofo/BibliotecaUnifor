package com.bibliotecaunifor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bibliotecaunifor.viewmodel.TelaHistoricoReservasViewModel

@Composable
fun TelaHistoricoReservas(navController: NavController) {
    var menuAberto by remember { mutableStateOf(false) }
    var expandido by remember { mutableStateOf<String?>(null) }
    val viewModel: TelaHistoricoReservasViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.carregarHistoricoReservas()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.size(60.dp).pointerHoverIcon(PointerIcon.Hand)
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
                        Text("Histórico de Reservas", color = Color.White, fontSize = 18.sp)
                        Text(
                            "Biblioteca Unifor",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            when {
                viewModel.loading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            color = Color(0xFF044EE7)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Carregando histórico...",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                }

                viewModel.erro != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Erro: ${viewModel.erro}",
                            fontSize = 16.sp,
                            color = Color.Red
                        )
                    }
                }

                viewModel.historicoReservas.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Nenhum histórico encontrado",
                            fontSize = 18.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Suas reservas aparecerão aqui",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }

                else -> {
                    Text(
                        text = "Todas as Reservas",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF044EE7),
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 20.dp, bottom = 8.dp)
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    ) {
                        items(viewModel.historicoReservas) { reserva ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp, vertical = 4.dp)
                                    .clickable {
                                        expandido = if (expandido == reserva.id) null else reserva.id
                                    },
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = when (reserva.status) {
                                        "concluída" -> Color(0xFFE8F5E8)
                                        "cancelada" -> Color(0xFFFFEBEE)
                                        else -> Color(0xFFE7EEFF)
                                    }
                                )
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column {
                                            Text(
                                                text = reserva.salaNome,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp,
                                                color = Color(0xFF044EE7)
                                            )
                                            Text(
                                                text = "Data: ${reserva.data}",
                                                fontSize = 14.sp,
                                                color = Color.Black
                                            )
                                        }
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = reserva.status.replaceFirstChar { it.uppercase() },
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = when (reserva.status) {
                                                    "concluída" -> Color(0xFF2E7D32)
                                                    "cancelada" -> Color(0xFFC62828)
                                                    else -> Color(0xFF044EE7)
                                                }
                                            )
                                            Icon(
                                                imageVector = if (expandido == reserva.id)
                                                    Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                                contentDescription = "Expandir",
                                                tint = Color(0xFF044EE7)
                                            )
                                        }
                                    }

                                    AnimatedVisibility(visible = expandido == reserva.id) {
                                        Column(modifier = Modifier.padding(top = 8.dp)) {
                                            Text("Horário: ${reserva.horarioInicio} - ${reserva.horarioFim}", color = Color.Black)
                                            if (reserva.usuarioNome.isNotEmpty()) {
                                                Text("Usuário: ${reserva.usuarioNome}", color = Color.Black)
                                            }
                                            if (reserva.usuarioMatricula.isNotEmpty()) {
                                                Text("Matrícula: ${reserva.usuarioMatricula}", color = Color.Black)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Home",
                    tint = Color.Gray,
                    modifier = Modifier.clickable {
                        navController.navigate(Route.SalasDisponiveis.path) {
                            popUpTo(Route.SalasDisponiveis.path) { inclusive = true }
                        }
                    }
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "Histórico",
                    tint = Color.Black,
                    modifier = Modifier.clickable { navController.navigate(Route.HistoricoReservas.path) }
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_list),
                    contentDescription = "Listas",
                    tint = Color.Gray,
                    modifier = Modifier.clickable { navController.navigate(Route.ReservasRealizadas.path) }
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "Perfil",
                    tint = Color.Gray,
                    modifier = Modifier.clickable { navController.navigate(Route.PerfilAluno.path) }
                )
            }
        }

        if (menuAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable { menuAberto = false }
            )
            MenuLateral(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)),
                navController = navController
            )
        }
    }
}