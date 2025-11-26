package com.bibliotecaunifor.Adm

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.R
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import com.bibliotecaunifor.Route
import com.bibliotecaunifor.repository.SalaRepository
import com.bibliotecaunifor.viewmodel.TelaAdminGerenciarSalasViewModel
import com.bibliotecaunifor.viewmodel.TelaAdminGerenciarSalasViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAdminGerenciarSalas(
    navController: NavController,
    onVoltarClick: () -> Unit = {},
    onNotificacoesClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onGerenciarSalaClick: (String) -> Unit = { _ -> },
    onNavHomeClick: () -> Unit = {},
    onNavHistoricoClick: () -> Unit = {},
    onNavListasClick: () -> Unit = {},
    onNavPerfilClick: () -> Unit = {},
    currentRoute: String = ""
) {
    val azulPrimario = Color(0xFF3F4F78)
    val azulClaroUnifor = Color(0xFF044EE7)
    val amareloDisponivel = Color(0xFFD3A82C)

    val menuLateralAberto = remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var salaParaExcluir by remember { mutableStateOf<String?>(null) }

    val viewModel: TelaAdminGerenciarSalasViewModel = viewModel(
        factory = TelaAdminGerenciarSalasViewModelFactory(SalaRepository())
    )
    val salas by viewModel.salas.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(error) {
        error?.let {
            println("Erro: $it")
        }
    }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = {
                showConfirmDialog = false
                salaParaExcluir = null
            },
            title = { Text("Confirmar Exclusão") },
            text = { Text("Tem certeza que deseja excluir esta sala?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        salaParaExcluir?.let { salaId ->
                            viewModel.excluirSala(salaId)
                        }
                        showConfirmDialog = false
                        salaParaExcluir = null
                    }
                ) {
                    Text("Excluir", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showConfirmDialog = false
                        salaParaExcluir = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                AppBottomNavAdminPadrao(
                    onHomeClick = onNavHomeClick,
                    onHistoricoClick = onNavHistoricoClick,
                    onListasClick = onNavListasClick,
                    onPerfilClick = onNavPerfilClick,
                    currentRoute = currentRoute
                )
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues),
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

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color.White)
                            .align(Alignment.TopCenter)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo UNIFOR",
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.TopCenter)
                            .offset(y = 8.dp)
                            .zIndex(2f)
                    )

                    Row(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 16.dp, top = 8.dp)
                            .zIndex(2f),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconButton(
                            onClick = onNotificacoesClick,
                            modifier = Modifier.size(40.dp).pointerHoverIcon(PointerIcon.Hand)
                        ) {
                            Icon(Icons.Outlined.Notifications, "Notificações", tint = Color.Black, modifier = Modifier.size(28.dp))
                        }

                        IconButton(
                            onClick = { menuLateralAberto.value = true },
                            modifier = Modifier.size(40.dp).pointerHoverIcon(PointerIcon.Hand)
                        ) {
                            Icon(Icons.Outlined.Menu, "Menu", tint = Color.Black, modifier = Modifier.size(30.dp))
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
                            Text("Gerenciar Salas", color = Color.White, fontSize = 18.sp)
                            Text(
                                "Biblioteca Unifor",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Text(
                    text = "Salas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 20.dp, top = 16.dp, bottom = 12.dp).align(Alignment.Start),
                    color = Color.Black
                )

                if (loading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = azulPrimario)
                    }
                } else if (salas.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Nenhuma sala encontrada",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(salas) { sala ->
                            SalaAdminItem(
                                sala = sala,
                                azulClaroUnifor = azulClaroUnifor,
                                amareloDisponivel = amareloDisponivel,
                                onClick = { onGerenciarSalaClick(sala.nome) },
                                onEditarClick = { salaParaEditar ->
                                    navController.navigate("editar_sala/${salaParaEditar.id}")
                                },
                                onExcluirClick = { salaId ->
                                    salaParaExcluir = salaId
                                    showConfirmDialog = true
                                }
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Route.TelaAdminCadastrarSala.path)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = azulPrimario
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 4.dp
                        )
                    ) {
                        Text(
                            text = "CADASTRAR NOVA MESA/SALA",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }

        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Route.TelaChatbotAdmin.path)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .size(50.dp),
                containerColor = azulPrimario,
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.unibo),
                    contentDescription = "Chatbot Unibô",
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        if (menuLateralAberto.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f))
                    .clickable { menuLateralAberto.value = false }
            ) {
                MenuLateralAdmin(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .fillMaxHeight()
                        .fillMaxWidth(0.75f),
                    navController = navController,
                    onLinkClick = { menuLateralAberto.value = false }
                )
            }
        }
    }
}

@Composable
fun SalaAdminItem(
    sala: com.bibliotecaunifor.model.Sala,
    azulClaroUnifor: Color,
    amareloDisponivel: Color,
    onClick: () -> Unit,
    onEditarClick: (com.bibliotecaunifor.model.Sala) -> Unit,
    onExcluirClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = sala.nome,
                color = azulClaroUnifor,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Capacidade: ${sala.capacidade} pessoas",
                    color = azulClaroUnifor,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "•",
                    color = amareloDisponivel,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (sala.disponivel) "Disponível" else "Indisponível",
                    color = if (sala.disponivel) amareloDisponivel else Color.Red,
                    fontSize = 14.sp
                )
            }

        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = { onEditarClick(sala) },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = Color.Black
                )
            }
            IconButton(
                onClick = { onExcluirClick(sala.id) },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Excluir",
                    tint = Color.Black
                )
            }
        }
    }
    Divider(
        color = Color.LightGray.copy(alpha = 0.5f),
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun TelaAdminGerenciarSalasPreview() {
    TelaAdminGerenciarSalas(
        navController = rememberNavController()
    )
}

@Preview(showBackground = true)
@Composable
fun SalaAdminItemPreview() {
    val salaExemplo = com.bibliotecaunifor.model.Sala(
        id = "sala001",
        nome = "Sala 01",
        capacidade = 24,
        disponivel = true
    )

    SalaAdminItem(
        sala = salaExemplo,
        azulClaroUnifor = Color(0xFF044EE7),
        amareloDisponivel = Color(0xFFD3A82C),
        onClick = {},
        onEditarClick = {},
        onExcluirClick = {}
    )
}