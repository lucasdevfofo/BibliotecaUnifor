package com.bibliotecaunifor

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bibliotecaunifor.viewmodel.TelaReservaSalaViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TelaReservaSala(navController: NavController, salaId: String) {
    val viewModel: TelaReservaSalaViewModel = viewModel()
    val contexto = LocalContext.current
    val scrollState = rememberScrollState()
    var menuAberto by remember { mutableStateOf(false) }
    var finalidadeTexto by remember { mutableStateOf("") }
    var diaSelecionado by remember { mutableStateOf<String?>(null) }
    var horarioEntrada by remember { mutableStateOf<String?>(null) }
    var horarioSaida by remember { mutableStateOf<String?>(null) }
    var salaNome by remember { mutableStateOf("") }
    var capacidade by remember { mutableStateOf(0) }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        when (uiState) {
            is TelaReservaSalaViewModel.UiState.Success -> {
                Toast.makeText(contexto, "Reserva realizada com sucesso!", Toast.LENGTH_SHORT).show()
                navController.navigate(Route.ReservaConfirmada.path)
            }
            is TelaReservaSalaViewModel.UiState.Error -> {
                val error = (uiState as TelaReservaSalaViewModel.UiState.Error).message
                Toast.makeText(contexto, error, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    LaunchedEffect(salaId) {
        val db = Firebase.firestore
        try {
            val doc = db.collection("salas").document(salaId).get().await()
            if (doc.exists()) {
                salaNome = doc.getString("nome") ?: "Sala Desconhecida"
                capacidade = when {
                    doc.get("capacidade") is Long -> doc.getLong("capacidade")?.toInt() ?: 0
                    doc.get("capacidade") is String -> doc.getString("capacidade")?.toIntOrNull() ?: 0
                    else -> 0
                }
            }
        } catch (e: Exception) {
            salaNome = "Sala Desconhecida"
            capacidade = 0
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
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

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Você deseja reservar:",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(6.dp))
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$salaNome - Capacidade: $capacidade pessoas",
                    color = Color(0xFF044EE7),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }

            Text(
                text = "O que você vai fazer:",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp, top = 10.dp)
            )

            OutlinedTextField(
                value = finalidadeTexto,
                onValueChange = { finalidadeTexto = it },
                placeholder = { Text("Ex: Estudar Java, preparar apresentação...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2E4C93),
                    focusedLabelColor = Color(0xFF2E4C93),
                    cursorColor = Color(0xFF2E4C93)
                )
            )

            Text(
                text = "Dia",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp, top = 10.dp)
            )

            val dias = listOf("Segunda", "Terça", "Quarta", "Quinta", "Sexta")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dias.forEach { dia ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                if (dia == diaSelecionado) Color(0xFF2E4C93)
                                else Color(0xFFF8FAFF)
                            )
                            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(6.dp))
                            .clickable { diaSelecionado = dia },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = dia.take(3),
                            color = if (dia == diaSelecionado) Color.White else Color(0xFF044EE7),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }
            }

            val horarios = listOf(
                "07:00", "08:00", "09:00", "10:00", "11:00",
                "12:00", "13:00", "14:00", "15:00", "16:00",
                "17:00"
            )

            Text(
                text = "Horário de Entrada",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp, top = 10.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 16.dp)
            ) {
                itemsIndexed(horarios) { _, hora ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                if (hora == horarioEntrada) Color(0xFF2E4C93)
                                else Color(0xFFF8FAFF)
                            )
                            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(6.dp))
                            .clickable { horarioEntrada = hora },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = hora,
                            color = if (hora == horarioEntrada) Color.White else Color(0xFF044EE7),
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Horário de Saída",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp, top = 10.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 16.dp)
            ) {
                itemsIndexed(horarios) { _, hora ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                if (hora == horarioSaida) Color(0xFF2E4C93)
                                else Color(0xFFF8FAFF)
                            )
                            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(6.dp))
                            .clickable { horarioSaida = hora },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = hora,
                            color = if (hora == horarioSaida) Color.White else Color(0xFF044EE7),
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }

            Button(
                onClick = {
                    if (horarioEntrada != null && horarioSaida != null && diaSelecionado != null) {
                        viewModel.fazerReserva(
                            salaId = salaId,
                            salaNome = salaNome,
                            dia = diaSelecionado!!,
                            horarioInicio = horarioEntrada!!,
                            horarioFim = horarioSaida!!,
                            finalidade = finalidadeTexto
                        )
                    } else {
                        Toast.makeText(
                            contexto,
                            "Selecione dia, horário de entrada e saída!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E4C93)),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(48.dp),
                enabled = uiState !is TelaReservaSalaViewModel.UiState.Loading
            ) {
                if (uiState is TelaReservaSalaViewModel.UiState.Loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Reservar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
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
                    .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)),
                navController = navController
            )
        }
    }
}