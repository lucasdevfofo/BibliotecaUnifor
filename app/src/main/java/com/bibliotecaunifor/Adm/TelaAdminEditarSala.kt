package com.bibliotecaunifor.Adm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.R
import com.bibliotecaunifor.repository.SalaRepository
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import com.bibliotecaunifor.viewmodel.TelaAdminGerenciarSalasViewModel
import com.bibliotecaunifor.viewmodel.TelaAdminGerenciarSalasViewModelFactory

@Composable
fun TelaAdminEditarSala(
    navController: NavController,
    salaId: String,
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    var menuAberto by remember { mutableStateOf(false) }

    val viewModel: TelaAdminGerenciarSalasViewModel = viewModel(
        factory = TelaAdminGerenciarSalasViewModelFactory(SalaRepository())
    )

    val salas by viewModel.salas.collectAsState()
    val sala = salas.find { it.id == salaId }

    var nomeSala by remember { mutableStateOf(sala?.nome ?: "") }
    var capacidade by remember { mutableStateOf(sala?.capacidade?.toString() ?: "") }
    var disponivel by remember { mutableStateOf(sala?.disponivel ?: true) }

    val azulPrimario = Color(0xFF3F4F78)
    val cinzaClaro = Color(0xFFF5F5F5)

    LaunchedEffect(sala) {
        if (sala != null) {
            nomeSala = sala.nome
            capacidade = sala.capacidade.toString()
            disponivel = sala.disponivel
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            AdminTopBarCadastro(
                onVoltarClick = onVoltarClick,
                onNotificacoesClick = onNotificacoesClick,
                onMenuClick = { menuAberto = true }
            )

            Text(
                text = "Editar Sala",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 20.dp, top = 24.dp, bottom = 8.dp)
                    .align(Alignment.Start)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column {
                    Text(
                        text = "Nome da Sala *",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = nomeSala,
                        onValueChange = { nomeSala = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = cinzaClaro,
                            unfocusedContainerColor = cinzaClaro,
                            focusedIndicatorColor = azulPrimario,
                            unfocusedIndicatorColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )
                }

                Column {
                    Text(
                        text = "Capacidade *",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = capacidade,
                        onValueChange = {
                            if (it.all { char -> char.isDigit() }) {
                                capacidade = it
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = cinzaClaro,
                            unfocusedContainerColor = cinzaClaro,
                            focusedIndicatorColor = azulPrimario,
                            unfocusedIndicatorColor = Color.Gray
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Disponível",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = disponivel,
                        onCheckedChange = { disponivel = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = azulPrimario,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color.Gray
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (nomeSala.isNotBlank() && capacidade.isNotBlank()) {
                            sala?.let { salaExistente ->
                                val salaAtualizada = salaExistente.copy(
                                    nome = nomeSala,
                                    capacidade = capacidade.toIntOrNull() ?: 0,
                                    disponivel = disponivel
                                )
                                viewModel.atualizarSala(salaAtualizada)
                                navController.popBackStack()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = azulPrimario),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = nomeSala.isNotBlank() && capacidade.isNotBlank()
                ) {
                    Text(
                        text = "SALVAR ALTERAÇÕES",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Cancelar",
                    color = azulPrimario,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onVoltarClick() }
                        .padding(vertical = 16.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        if (menuAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f))
                    .clickable { menuAberto = false },
                contentAlignment = Alignment.TopEnd
            ) {
                MenuLateralAdmin(
                    modifier = Modifier.fillMaxHeight().fillMaxWidth(0.75f),
                    navController = navController,
                    onLinkClick = { menuAberto = false }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaAdminEditarSalaPreview() {
    BibliotecaUniforTheme {
        TelaAdminEditarSala(
            navController = rememberNavController(),
            salaId = "sala001",
            onVoltarClick = {},
            onNotificacoesClick = {},
            onMenuClick = {}
        )
    }
}