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
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TelaHistoricoReservas(navController: NavController) {
    var menuAberto by remember { mutableStateOf(false) }
    var expandidoSala by remember { mutableStateOf<String?>(null) }

    val viewModel: TelaHistoricoReservasViewModel = viewModel()

    val listaSalas by viewModel.historicoReservas.collectAsState()
    val listaLivros by viewModel.historicoLivros.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.carregarHistoricoReservas()
    }

    fun formatarData(timestamp: Timestamp): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        return sdf.format(timestamp.toDate())
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
            // --- HEADER FIXO (Topo) ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
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
                        contentDescription = "Logo",
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

            // --- CONTEÚDO ROLÁVEL (Meio) ---
            // Usamos Box com weight(1f) para ocupar todo o espaço entre o topo e o fundo
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when {
                    viewModel.loading -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(color = Color(0xFF044EE7))
                            Text("Carregando histórico...", color = Color.Gray, modifier = Modifier.padding(top=8.dp))
                        }
                    }

                    viewModel.erro != null -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("Erro: ${viewModel.erro}", color = Color.Red)
                        }
                    }

                    listaSalas.isEmpty() && listaLivros.isEmpty() -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("Nenhum histórico encontrado", fontSize = 18.sp, color = Color.Gray)
                        }
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
                        ) {
                            // --- LIVROS ---
                            if (listaLivros.isNotEmpty()) {
                                item {
                                    Text(
                                        text = "Livros Alugados",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF044EE7),
                                        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                                    )
                                }
                                items(listaLivros) { aluguel ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 4.dp, vertical = 4.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = if (aluguel.status == "ativo") Color(0xFFE7EEFF) else Color(0xFFEEEEEE)
                                        )
                                    ) {
                                        Column(modifier = Modifier.padding(16.dp)) {
                                            Text(
                                                text = aluguel.tituloLivro.uppercase(),
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp,
                                                color = Color(0xFF044EE7)
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text("Retirada: ${formatarData(aluguel.dataAluguel)}", fontSize = 14.sp)
                                            Text("Devolução: ${formatarData(aluguel.dataDevolucaoPrevista)}", fontSize = 14.sp)

                                            Spacer(modifier = Modifier.height(8.dp))

                                            Surface(
                                                color = if (aluguel.status == "ativo") Color(0xFFE7FFE7) else Color(0xFFEEEEEE),
                                                shape = RoundedCornerShape(4.dp)
                                            ) {
                                                Text(
                                                    text = if (aluguel.status == "ativo") "EM ANDAMENTO" else "CANCELADO",
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                                    color = if (aluguel.status == "ativo") Color(0xFF2E7D32) else Color.Gray,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }
                                }
                                item { Spacer(modifier = Modifier.height(20.dp)) }
                            }

                            // --- SALAS ---
                            if (listaSalas.isNotEmpty()) {
                                item {
                                    Text(
                                        text = "Histórico de Reservas",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF044EE7),
                                        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                                    )
                                }
                                items(listaSalas) { reserva ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 4.dp, vertical = 4.dp)
                                            .clickable {
                                                expandidoSala = if (expandidoSala == reserva.id) null else reserva.id
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
                                                        imageVector = if (expandidoSala == reserva.id)
                                                            Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                                        contentDescription = "Expandir",
                                                        tint = Color(0xFF044EE7)
                                                    )
                                                }
                                            }

                                            AnimatedVisibility(visible = expandidoSala == reserva.id) {
                                                Column(modifier = Modifier.padding(top = 8.dp)) {
                                                    Text("Horário: ${reserva.horarioInicio} - ${reserva.horarioFim}", color = Color.Black)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // --- BOTTOM BAR FIXA (Fundo) ---
            // Removi o Spacer(weight(1f)) que causava o bug
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
                    modifier = Modifier.clickable { }
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