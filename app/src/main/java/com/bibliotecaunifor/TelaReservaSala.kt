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
import androidx.navigation.NavController

@Composable
fun TelaReservaSala(navController: NavController, salaNome: String) {
    val scrollState = rememberScrollState()
    val contexto = LocalContext.current
    var menuAberto by remember { mutableStateOf(false) }

    // 🔹 Agora a finalidade é apenas texto digitado
    var finalidadeTexto by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
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

            // --- Sala selecionada ---
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
                    text = "$salaNome - 23 vagas disponíveis",
                    color = Color(0xFF044EE7),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }

            // --- Finalidade (campo de texto livre) ---
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

            // --- Dia ---
            Text(
                text = "Dia",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp, top = 10.dp)
            )

            val dias = listOf("Segunda", "Terça", "Quarta", "Quinta", "Sexta")
            var diaSelecionado by remember { mutableStateOf<String?>(null) }

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

            // --- Horários ---
            val horarios = listOf(
                "07:00", "08:00", "09:00", "10:00", "11:00",
                "12:00", "13:00", "14:00", "15:00", "16:00",
                "17:00", "18:00", "19:00", "20:00"
            )

            var horarioEntrada by remember { mutableStateOf<String?>(null) }
            var horarioSaida by remember { mutableStateOf<String?>(null) }

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

            // --- Botão Reservar ---
            Button(
                onClick = {
                    if (horarioEntrada != null && horarioSaida != null) {
                        val finalidadeMsg = if (finalidadeTexto.isNotBlank())
                            " para ${finalidadeTexto.trim()}"
                        else ""
                        navController.navigate(Route.ReservaConfirmada.path)

                    } else {
                        Toast.makeText(
                            contexto,
                            "Selecione horário de entrada e saída!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E4C93)),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(48.dp)
            ) {
                Text("Reservar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
                    .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp))
            )
        }
    }
}
