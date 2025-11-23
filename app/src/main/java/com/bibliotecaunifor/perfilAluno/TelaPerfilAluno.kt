package com.bibliotecaunifor.perfilAluno

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bibliotecaunifor.MenuLateral
import com.bibliotecaunifor.R
import com.bibliotecaunifor.Route
import com.bibliotecaunifor.viewmodel.TelaPerfilAlunoViewModel
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TelaPerfilAluno(
    navController: NavController,
    viewModel: TelaPerfilAlunoViewModel = viewModel()
) {
    val azulUnifor = Color(0xFF004AF5)
    val cinzaBorda = Color(0xFFE0E0E0)
    val cinzaClaro = Color(0xFFF5F7FF)
    val roxoBotao = Color(0xFF3F4F78)

    var menuAberto by remember { mutableStateOf(false) }
    val scroll = rememberScrollState()

    // Observando os estados do ViewModel
    val usuario by viewModel.usuarioState.collectAsState()
    val ultimasReservas by viewModel.ultimasReservasState.collectAsState()

    // Observando a lista de livros
    val livrosAlugados by viewModel.livrosAlugadosState.collectAsState()

    val loading by viewModel.loadingState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.carregarDados()
    }

    // Função auxiliar para formatar a data
    fun formatarData(timestamp: Timestamp): String {
        val sdf = SimpleDateFormat("dd/MM", Locale("pt", "BR"))
        return sdf.format(timestamp.toDate())
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scroll),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- HEADER ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .height(100.dp)
            ) {
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
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- INFO USUÁRIO ---
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(cinzaClaro)
                    .border(1.dp, cinzaBorda, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(72.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = usuario?.nomeCompleto ?: "Carregando...",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Text(
                text = usuario?.matricula ?: "...",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Divider(
                modifier = Modifier.padding(top = 12.dp).width(240.dp),
                color = cinzaBorda
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = usuario?.curso ?: "...",
                fontSize = 14.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { navController.navigate(Route.EditarUsuario.path) },
                colors = ButtonDefaults.buttonColors(containerColor = roxoBotao),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(44.dp)
                    .fillMaxWidth()
            ) {
                Text("EDITAR PERFIL", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(14.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("SALAS DISPONÍVEIS PARA O USUÁRIO:", fontSize = 13.sp, color = Color.Black)
                Text("2/2", fontSize = 12.sp, color = Color.Black)
                Spacer(Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { 1f },
                    color = azulUnifor,
                    trackColor = cinzaBorda,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(50))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- ÚLTIMAS RESERVAS ---
            SectionCard(
                titulo = "ÚLTIMAS RESERVAS",
                content = {
                    if (loading && ultimasReservas.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = azulUnifor)
                        }
                    } else if (ultimasReservas.isEmpty()) {
                        Text(
                            text = "Nenhuma reserva recente",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    } else {
                        ultimasReservas.forEachIndexed { i, reserva ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.RadioButtonUnchecked,
                                    contentDescription = null,
                                    tint = azulUnifor,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        reserva.salaNome,
                                        color = azulUnifor,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        "${reserva.data} • ${reserva.horarioInicio} - ${reserva.horarioFim}",
                                        color = Color.Gray,
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        "Status: ${reserva.status.replaceFirstChar { it.uppercase() }}",
                                        color = when (reserva.status) {
                                            "concluída" -> Color(0xFF2E7D32)
                                            "cancelada" -> Color(0xFFC62828)
                                            else -> azulUnifor
                                        },
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                            if (i < ultimasReservas.lastIndex) Divider(color = cinzaBorda)
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // --- LIVROS ALUGADOS ---
            SectionCard(
                titulo = "LIVROS ALUGADOS",
                content = {
                    if (loading && livrosAlugados.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = azulUnifor)
                        }
                    } else if (livrosAlugados.isEmpty()) {
                        Text(
                            text = "Você não tem livros alugados.",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    } else {
                        // Loop na lista real
                        livrosAlugados.forEachIndexed { i, aluguel ->
                            val dataInicio = formatarData(aluguel.dataAluguel)
                            val dataFim = formatarData(aluguel.dataDevolucaoPrevista)
                            val textoExibicao = "${aluguel.tituloLivro.uppercase()} $dataInicio - $dataFim"

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, cinzaBorda, RoundedCornerShape(8.dp))
                                        .background(Color.White)
                                        .padding(horizontal = 12.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = textoExibicao,
                                        color = azulUnifor,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontSize = 13.sp
                                    )
                                }

                                Spacer(Modifier.width(6.dp))

                                // --- BOTÃO CANCELRA ---
                                Button(
                                    onClick = { viewModel.devolverLivro(aluguel) },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828)), // Vermelho
                                    shape = RoundedCornerShape(6.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp),
                                    modifier = Modifier.height(36.dp)
                                ) {
                                    Text("CANCELAR", color = Color.White, fontSize = 10.sp)
                                }

                                Spacer(Modifier.width(4.dp))

                                // --- BOTÃO RENOVAR ---
                                Button(
                                    onClick = {
                                        // 1. Prepara os dados
                                        val tituloEncoded = java.net.URLEncoder.encode(aluguel.tituloLivro, "UTF-8")
                                        val dataMillis = aluguel.dataDevolucaoPrevista.toDate().time

                                        // 2. Navega para a rota dinâmica
                                        navController.navigate("renovar_livro/${aluguel.id}/$tituloEncoded/$dataMillis")
                                    },
                                    enabled = !aluguel.renovado,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = roxoBotao,
                                        disabledContainerColor = Color.Gray
                                    ),
                                    shape = RoundedCornerShape(6.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp),
                                    modifier = Modifier.height(36.dp)
                                ) {
                                    Text(if (aluguel.renovado) "JÁ RENOVADO" else "RENOVAR", color = Color.White, fontSize = 10.sp)
                                }
                            }
                            if (i < livrosAlugados.lastIndex) Divider(color = cinzaBorda)
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(80.dp))
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
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
                tint = Color.Gray,
                modifier = Modifier.clickable { navController.navigate(Route.HistoricoReservas.path) }
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_list),
                contentDescription = "Listas",
                tint = Color.Gray,
                modifier = Modifier.clickable { navController.navigate(Route.ReservasRealizadas.path) }
            )
            Icon(painter = painterResource(id = R.drawable.ic_user), contentDescription = "Perfil", tint = Color.Black)
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

@Composable
private fun SectionCard(
    titulo: String,
    content: @Composable ColumnScope.() -> Unit
) {
    val cinzaBorda = Color(0xFFE0E0E0)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, cinzaBorda, RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = titulo,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Spacer(Modifier.height(8.dp))
        content()
    }
}