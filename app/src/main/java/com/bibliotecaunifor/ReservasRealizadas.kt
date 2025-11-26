package com.bibliotecaunifor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TelaReservasRealizadas(navController: NavController) {
    var menuAberto by remember { mutableStateOf(false) }
    var reservas by remember { mutableStateOf<List<Reserva>>(emptyList()) }
    var reservaExpandida by remember { mutableStateOf<String?>(null) }
    val contexto = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(Unit) {
        carregarReservasUsuario(db) { reservasCarregadas ->
            reservas = reservasCarregadas
        }
    }

    fun cancelarReserva(id: String) {
        db.collection("reservas").document(id)
            .update("status", "cancelada")
            .addOnSuccessListener {
                reservas = reservas.map { reserva ->
                    if (reserva.id == id) reserva.copy(status = "cancelada") else reserva
                }
                Toast.makeText(contexto, "Reserva cancelada com sucesso!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(contexto, "Erro ao cancelar reserva", Toast.LENGTH_SHORT).show()
            }
    }

    val reservasPendentes = reservas.filter { it.status == "pendente" }

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
                    .height(100.dp)
                    .background(Color.White)
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

            Text(
                text = "Reservas realizadas",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF044EE7),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (reservas.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Nenhuma reserva encontrada",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Suas reservas aparecerão aqui",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            } else {
                if (reservasPendentes.isNotEmpty()) {
                    Text(
                        text = "Reservas Ativas",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF044EE7),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                    )
                    reservasPendentes.forEach { reserva ->
                        CardReserva(
                            reserva = reserva,
                            reservaExpandida = reservaExpandida,
                            onExpandir = { id -> reservaExpandida = if (reservaExpandida == id) null else id },
                            onCancelar = { id -> cancelarReserva(id) },
                            navController = navController
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
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
                modifier = Modifier.clickable { navController.navigate(Route.SalasDisponiveis.path) }
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
                tint = Color.Black
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = "Perfil",
                tint = Color.Gray,
                modifier = Modifier.clickable { navController.navigate(Route.PerfilAluno.path) }
            )
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
fun CardReserva(
    reserva: Reserva,
    reservaExpandida: String?,
    onExpandir: (String?) -> Unit,
    onCancelar: (String) -> Unit,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .border(1.dp, Color(0xFF044EE7), RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onExpandir(if (reservaExpandida == reserva.id) null else reserva.id)
                }
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = reserva.salaNome,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF044EE7)
                )
                Icon(
                    imageVector = if (reservaExpandida == reserva.id)
                        Icons.Filled.KeyboardArrowUp
                    else
                        Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Expandir",
                    tint = Color(0xFF044EE7)
                )
            }

            AnimatedVisibility(
                visible = reservaExpandida == reserva.id,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    Text(
                        text = "Horário: ${reserva.horarioInicio} às ${reserva.horarioFim}",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Data: ${reserva.data}",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Status: ${reserva.status.replaceFirstChar { it.uppercase() }}",
                        fontSize = 16.sp,
                        color = when (reserva.status) {
                            "pendente" -> Color(0xFF044EE7)
                            "concluída" -> Color(0xFF2E7D32)
                            "cancelada" -> Color(0xFFE74C3C)
                            else -> Color.Gray
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (reserva.status == "pendente") {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate("tela_editar_reserva/${reserva.id}")
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F4F78)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Editar", color = Color.White)
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Button(
                                onClick = {
                                    onCancelar(reserva.id)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE74C3C)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Cancelar", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun verificarStatusReserva(data: String, horarioFim: String, statusAtual: String): String {
    return if (statusAtual == "cancelada") {
        "cancelada"
    } else {
        try {
            val formatoDataHora = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale("pt", "BR"))

            val dataHoraFimString = "$data $horarioFim"
            val dataHoraFim = formatoDataHora.parse(dataHoraFimString)

            val agora = Date()

            if (agora.after(dataHoraFim)) {
                "concluída"
            } else {
                "pendente"
            }
        } catch (e: Exception) {
            statusAtual
        }
    }
}

fun carregarReservasUsuario(db: FirebaseFirestore, onResult: (List<Reserva>) -> Unit) {
    val usuarioId = Firebase.auth.currentUser?.uid ?: "user123"

    db.collection("reservas")
        .whereEqualTo("usuarioId", usuarioId)
        .get()
        .addOnSuccessListener { documents ->
            val reservasList = documents.documents.mapNotNull { document ->
                val data = document.getString("data") ?: ""
                val horarioFim = document.getString("horarioFim") ?: ""
                val statusAtual = document.getString("status") ?: "pendente"

                val statusAtualizado = verificarStatusReserva(data, horarioFim, statusAtual)

                if (statusAtualizado != statusAtual && statusAtualizado == "concluída") {
                    document.reference.update("status", "concluída")
                }

                Reserva(
                    id = document.id,
                    salaNome = document.getString("salaNome") ?: "",
                    horarioInicio = document.getString("horarioInicio") ?: "",
                    horarioFim = horarioFim,
                    data = data,
                    status = statusAtualizado,
                    usuarioNome = document.getString("usuarioNome") ?: "",
                    usuarioMatricula = document.getString("usuarioMatricula") ?: "",
                    salaId = document.getString("salaId") ?: ""
                )
            }
            onResult(reservasList)
        }
        .addOnFailureListener {
            onResult(emptyList())
        }
}

data class Reserva(
    val id: String,
    val salaNome: String,
    val horarioInicio: String,
    val horarioFim: String,
    val data: String,
    val status: String,
    val usuarioNome: String,
    val usuarioMatricula: String,
    val salaId: String
)