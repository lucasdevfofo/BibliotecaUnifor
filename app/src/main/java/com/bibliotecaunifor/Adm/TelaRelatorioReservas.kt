package com.bibliotecaunifor.Adm

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Print
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.bibliotecaunifor.R
import com.bibliotecaunifor.model.Reserva
import com.bibliotecaunifor.viewmodel.ReservaAdminViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaRelatorioReservas(
    navController: NavController,
    onVoltarClick: () -> Unit
) {
    val viewModel: ReservaAdminViewModel = viewModel()
    val reservas by viewModel.reservas.collectAsState()

    val dataAtual = SimpleDateFormat("MMMM 'de' yyyy", Locale("pt", "BR")).format(Date())
    val mesAtual = SimpleDateFormat("MM/yyyy", Locale.getDefault()).format(Date())

    val reservasDoMes = reservas.filter { reserva ->
        reserva.data.endsWith(mesAtual.substring(3))
    }

    val totalReservas = reservasDoMes.size
    val reservasConfirmadas = reservasDoMes.count { it.status == "confirmada" }
    val reservasPendentes = reservasDoMes.count { it.status == "pendente" }
    val reservasCanceladas = reservasDoMes.count { it.status == "cancelada" }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                AdminTopBarRelatorio(
                    onVoltarClick = onVoltarClick,
                    tituloGrande = "Relatório de Reservas",
                    subtituloPequeno = dataAtual
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                "RESUMO DO MÊS",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF3F4F78),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                InfoRelatorio("Total", totalReservas.toString(), Color(0xFF3F4F78))
                                InfoRelatorio("Confirmadas", reservasConfirmadas.toString(), Color(0xFF4CAF50))
                                InfoRelatorio("Pendentes", reservasPendentes.toString(), Color(0xFFFF9800))
                                InfoRelatorio("Canceladas", reservasCanceladas.toString(), Color(0xFFF44336))
                            }
                        }
                    }
                }

                item {
                    Text(
                        "DETALHES DAS RESERVAS",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF3F4F78),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                if (reservasDoMes.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Nenhuma reserva encontrada para este mês",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                    }
                } else {
                    items(reservasDoMes) { reserva ->
                        ItemRelatorio(reserva = reserva)
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        BotaoAcaoRelatorio(
                            texto = "IMPRIMIR",
                            icone = Icons.Default.Print,
                            onClick = { }
                        )
                        BotaoAcaoRelatorio(
                            texto = "EXPORTAR PDF",
                            icone = Icons.Default.Download,
                            onClick = { }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun InfoRelatorio(titulo: String, valor: String, cor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            titulo,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Text(
            valor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = cor
        )
    }
}

@Composable
fun ItemRelatorio(reserva: Reserva) {
    val statusColor = when (reserva.status) {
        "confirmada" -> Color(0xFF4CAF50)
        "pendente" -> Color(0xFFFF9800)
        "cancelada" -> Color(0xFFF44336)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = reserva.usuarioNome,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Matrícula: ${reserva.usuarioMatricula}",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Text(
                        text = "Sala: ${reserva.salaNome}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                Text(
                    text = reserva.status.replaceFirstChar { it.uppercase() },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusColor
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Data: ${reserva.data}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "${reserva.horarioInicio} - ${reserva.horarioFim}",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun BotaoAcaoRelatorio(texto: String, icone: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3F4F78)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .width(140.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(icone, contentDescription = texto, tint = Color.White, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
            Text(texto, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

@Composable
fun AdminTopBarRelatorio(
    onVoltarClick: () -> Unit,
    tituloGrande: String,
    subtituloPequeno: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.livros),
            contentDescription = "Imagem de fundo biblioteca",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White)
                .align(Alignment.TopCenter)
        )

        IconButton(
            onClick = onVoltarClick,
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.TopStart)
                .zIndex(2f)
                .pointerHoverIcon(PointerIcon.Hand)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar", tint = Color.Black, modifier = Modifier.size(30.dp))
        }

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo UNIFOR",
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.TopCenter)
                .offset(y = 8.dp)
                .zIndex(2f)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(104.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(subtituloPequeno, color = Color.White, fontSize = 16.sp)
                Text(
                    tituloGrande,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}