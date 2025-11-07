package com.bibliotecaunifor.Adm

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
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.R
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import com.bibliotecaunifor.Route
import kotlinx.coroutines.launch
import androidx.navigation.NavController

data class MesaReserva(val numero: String, val nomeAluno: String)
data class SalaReserva(val nome: String, val horario: String, val mesas: List<MesaReserva>)

val salasExemplo = listOf(
    SalaReserva("SALA 01", "HORÁRIO: 15:00 - 18:00", listOf(
        MesaReserva("Mesa 01", "ISABELLE FEITOSA"),
        MesaReserva("Mesa 02", "GABRIEL SIMAO"),
        MesaReserva("Mesa 03", "LEO MARTINS"),
        MesaReserva("Mesa 04", "ISADORA SOUZA")
    )),
    SalaReserva("SALA 02", "HORÁRIO: 09:00 - 12:00", listOf(
        MesaReserva("Mesa 05", "ANA CLARA SILVA"),
        MesaReserva("Mesa 06", "BRUNO COSTA"),
        MesaReserva("Mesa 07", "CAROLINA SANTOS")
    )),
    SalaReserva("SALA 03", "HORÁRIO: 18:00 - 21:00", listOf(
        MesaReserva("Mesa 08", "DANIEL PEREIRA"),
        MesaReserva("Mesa 09", "EDUARDA ROCHA")
    )),
    SalaReserva("SALA 04", "HORÁRIO: 13:00 - 16:00", listOf(
        MesaReserva("Mesa 10", "FERNANDA ALMEIDA"),
        MesaReserva("Mesa 11", "GUILHERME OLIVEIRA"),
        MesaReserva("Mesa 12", "HELENA FARIAS"),
        MesaReserva("Mesa 13", "IGOR RAMOS")
    )),
    SalaReserva("SALA 05", "HORÁRIO: 10:00 - 13:00", emptyList())
)


@Composable
fun AdminTopBarReservas(
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onMenuClick: () -> Unit,
    tituloGrande: String,
    subtituloPequeno: String
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
                onClick = onMenuClick,
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
                Text(subtituloPequeno, color = Color.White, fontSize = 18.sp)
                Text(
                    tituloGrande,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun TelaAdminReservasRealizadas(
    navController: NavController,
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onMenuClick: () -> Unit,
    onDownloadRelatorioClick: () -> Unit,
    onNavSalasClick: () -> Unit,
    onNavReservasClick: () -> Unit,
    onNavUsuariosClick: () -> Unit,
    onNavPerfilClick: () -> Unit,
    currentRoute: String
) {
    var menuLateralAberto by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                AdminTopBarReservas(
                    onVoltarClick = onVoltarClick,
                    onNotificacoesClick = onNotificacoesClick,
                    onMenuClick = { menuLateralAberto = true },
                    subtituloPequeno = "Reserve sua sala",
                    tituloGrande = "Reservas Realizadas"
                )
            },
            bottomBar = {
                AppBottomNavAdminPadrao(
                    onHomeClick = onNavSalasClick,
                    onHistoricoClick = onNavReservasClick,
                    onListasClick = onNavUsuariosClick,
                    onPerfilClick = onNavPerfilClick,
                    currentRoute = currentRoute
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                items(salasExemplo) { sala ->
                    ReservaSalaCard(sala = sala)
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        BotaoBaixarRelatorio(
                            onClick = {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Baixando relatório",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                                onDownloadRelatorioClick()
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

        if (menuLateralAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(4f)
                    .clickable { menuLateralAberto = false }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                )

                MenuLateralAdmin(
                    navController = navController,
                    onLinkClick = { menuLateralAberto = false },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .fillMaxHeight()
                        .fillMaxWidth(0.75f)
                )
            }
        }
    }
}

@Composable
fun ReservaSalaCard(sala: SalaReserva) {
    val isExpanded = remember { mutableStateOf(sala.nome == "SALA 01") }
    val azulClaro = Color(0xFF004AF5).copy(alpha = 0.1f)
    val azulForte = Color(0xFF004AF5)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isExpanded.value) azulClaro else Color.White)
                .clickable { isExpanded.value = !isExpanded.value }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isExpanded.value,
                onClick = { isExpanded.value = !isExpanded.value },
                colors = RadioButtonDefaults.colors(selectedColor = azulForte),
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(sala.nome, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = if (isExpanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Expandir/Recolher",
                tint = Color.Gray
            )
        }

        AnimatedVisibility(visible = isExpanded.value) {
            Column(modifier = Modifier.padding(start = 40.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)) {
                if (sala.horario.isNotEmpty()) {
                    Text(sala.horario, color = Color.Gray, fontSize = 14.sp, modifier = Modifier.padding(bottom = 8.dp))
                }
                if (sala.mesas.isEmpty()) {
                    Text("Nenhuma reserva para esta sala no momento.", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.padding(bottom = 8.dp))
                } else {
                    sala.mesas.forEach { mesa ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("${mesa.numero} - ${mesa.nomeAluno}", color = azulForte, fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
    }
}

@Composable
fun BotaoBaixarRelatorio(onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3A3A3A)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.Download, contentDescription = "Download", tint = Color.White, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Text("BAIXAR RELATORIO MÊS AGOSTO", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun TelaAdminReservasRealizadasPreview() {
    val mockCurrentRoute = Route.TelaAdminReservasRealizadas.path

    @Composable fun AppBottomNavAdminPadrao(onHomeClick: () -> Unit, onHistoricoClick: () -> Unit, onListasClick: () -> Unit, onPerfilClick: () -> Unit, currentRoute: String) {
        NavigationBar(containerColor = Color.White) {
            NavigationBarItem(selected = currentRoute == Route.TelaAdminGerenciarSalas.path, onClick = onHomeClick, icon = { Icon(Icons.Filled.Home, "Salas") })
            NavigationBarItem(selected = currentRoute == Route.TelaAdminReservasRealizadas.path, onClick = onHistoricoClick, icon = { Icon(Icons.Filled.DateRange, "Reservas") })
            NavigationBarItem(selected = currentRoute == Route.TelaAdminGerenciarUsuarios.path, onClick = onListasClick, icon = { Icon(Icons.Filled.List, "Usuários") })
            NavigationBarItem(selected = currentRoute == Route.PerfilAluno.path, onClick = onPerfilClick, icon = { Icon(Icons.Filled.Person, "Perfil") })
        }
    }

    val mockNavController = rememberNavController()

    BibliotecaUniforTheme {
        TelaAdminReservasRealizadas(
            navController = mockNavController,
            onVoltarClick = {},
            onNotificacoesClick = {},
            onMenuClick = {},
            onDownloadRelatorioClick = {},
            onNavSalasClick = {},
            onNavReservasClick = {},
            onNavUsuariosClick = {},
            onNavPerfilClick = {},
            currentRoute = mockCurrentRoute
        )
    }
}