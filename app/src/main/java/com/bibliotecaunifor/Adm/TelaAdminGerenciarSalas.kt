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
import androidx.compose.material.icons.filled.Home
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.R
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import com.bibliotecaunifor.Route

data class SalaAdmin(
    val nome: String,
    val totalMesas: Int,
    val mesasDisponiveis: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAdminGerenciarSalas(
    navController: NavController,
    onVoltarClick: () -> Unit,
    onCadastrarNovaSalaClick: () -> Unit,
    onGerenciarSalaClick: (String) -> Unit,
    onNavHomeClick: () -> Unit,
    onNavHistoricoClick: () -> Unit,
    onNavListasClick: () -> Unit,
    onNavPerfilClick: () -> Unit,
    currentRoute: String
) {
    val azulPrimario = Color(0xFF3F4F78)
    val azulClaroUnifor = Color(0xFF044EE7)
    val amareloDisponivel = Color(0xFFD3A82C)

    val menuLateralAberto = remember { mutableStateOf(false) }

    val salas = listOf(
        SalaAdmin("SALA 01", 23, 18),
        SalaAdmin("SALA 02", 23, 12),
        SalaAdmin("SALA 03", 23, 20),
        SalaAdmin("SALA 04", 23, 18),
        SalaAdmin("SALA 05", 28, 28)
    )

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
                            onClick = {   },
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
                            onClick = { onGerenciarSalaClick(sala.nome) }
                        )
                    }
                }

                Button(
                    onClick = onCadastrarNovaSalaClick,
                    colors = ButtonDefaults.buttonColors(containerColor = azulPrimario),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Text(
                        "CADASTRAR NOVA MESA/SALA",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
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
                    .clickable { menuLateralAberto.value = false },
                contentAlignment = Alignment.TopEnd
            ) {
                MenuLateralAdmin(
                    modifier = Modifier.fillMaxHeight().fillMaxWidth(0.75f),
                    navController = navController,
                    onLinkClick = { menuLateralAberto.value = false }
                )
            }
        }
    }
}

@Composable
fun SalaAdminItem(sala: SalaAdmin, azulClaroUnifor: Color, amareloDisponivel: Color, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp, horizontal = 4.dp)
    ) {
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
                text = "${sala.totalMesas} MESAS",
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
                text = "${sala.mesasDisponiveis} MESAS DISPONÍVEIS",
                color = amareloDisponivel,
                fontSize = 14.sp
            )
        }
    }
    Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp, modifier = Modifier.padding(top = 4.dp))
}


@Preview(showBackground = true)
@Composable
fun TelaAdminGerenciarSalasPreview() {

    @Composable fun AppBottomNavAdminPadrao(onHomeClick: () -> Unit, onHistoricoClick: () -> Unit, onListasClick: () -> Unit, onPerfilClick: () -> Unit, currentRoute: String) {
        NavigationBar(containerColor = Color.White) {
            NavigationBarItem(selected = currentRoute == Route.TelaAdminGerenciarSalas.path, onClick = onHomeClick, icon = { Icon(Icons.Filled.Home, "Salas") })
            NavigationBarItem(selected = currentRoute == Route.TelaAdminReservasRealizadas.path, onClick = onHistoricoClick, icon = { Icon(Icons.Filled.Home, "Reservas") })
            NavigationBarItem(selected = currentRoute == Route.TelaAdminGerenciarUsuarios.path, onClick = onListasClick, icon = { Icon(Icons.Filled.Home, "Usuários") })
            NavigationBarItem(selected = currentRoute == Route.PerfilAluno.path, onClick = onPerfilClick, icon = { Icon(Icons.Filled.Home, "Perfil") })
        }
    }


    @Composable fun MenuLateralAdmin(modifier: Modifier, navController: NavController, onLinkClick: () -> Unit) {
        Box(modifier.background(Color.Black.copy(alpha = 0.5f)).clickable(onClick = onLinkClick)) {
            Text("MENU ADMIN", color = Color.White, modifier = Modifier.padding(30.dp))
        }
    }

    BibliotecaUniforTheme {
        TelaAdminGerenciarSalas(
            navController = rememberNavController(),
            onVoltarClick = {},
            onCadastrarNovaSalaClick = {},
            onGerenciarSalaClick = {},
            onNavHomeClick = {},
            onNavHistoricoClick = {},
            onNavListasClick = {},
            onNavPerfilClick = {},
            currentRoute = Route.TelaAdminGerenciarSalas.path
        )
    }

}