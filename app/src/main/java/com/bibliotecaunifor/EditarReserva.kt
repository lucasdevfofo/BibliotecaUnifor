package com.bibliotecaunifor

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun EditarReserva(navController: NavController, salaNome: String) {
    val contexto = LocalContext.current
    var menuAberto by remember { mutableStateOf(false) }
    var horarioEntrada by remember { mutableStateOf<String?>(null) }
    var horarioSaida by remember { mutableStateOf<String?>(null) }

    val horarios = listOf(
        "07:00", "08:00", "09:00", "10:00", "11:00",
        "12:00", "13:00", "14:00", "15:00", "16:00",
        "17:00", "18:00", "19:00", "20:00"
    )

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
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
                            text = "Editar Reserva",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        Text(
                            text = salaNome,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Horário:",
                fontSize = 14.sp,
                color = Color(0xFF044EE7),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 20.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Horário de Entrada",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp)
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
                    .padding(start = 16.dp)
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
                    if (horarioEntrada != null && horarioSaida != null) {
                        Toast.makeText(contexto, "Reserva atualizada com sucesso!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(contexto, "Selecione os horários!", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E4C93)),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .height(48.dp)
            ) {
                Text("Confirmar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(50.dp))
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