package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

@Composable
fun TelaSalasDisponiveis(
    navController: NavController,
    onVoltarClick: () -> Unit,
    onSalaClick: (String) -> Unit
) {
    var menuAberto by remember { mutableStateOf(false) }
    var chatAberto by remember { mutableStateOf(false) }

    LaunchedEffect(menuAberto) {
        if (menuAberto) chatAberto = false
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.livros),
                    contentDescription = null,
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
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
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
                        modifier = Modifier
                            .size(40.dp)
                            .pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    IconButton(
                        onClick = { menuAberto = !menuAberto },
                        modifier = Modifier
                            .size(40.dp)
                            .pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            contentDescription = null,
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
                        Text("Reserve sua sala", color = Color.White, fontSize = 18.sp)
                        Text(
                            "Biblioteca Unifor",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Salas Disponíveis",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 20.dp),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            val salas = listOf(
                "SALA 01", "SALA 02", "SALA 03", "SALA 04",
                "SALA 05", "SALA 06", "SALA 07", "SALA 08"
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                itemsIndexed(salas) { index, sala ->
                    val bgColor = if (index % 2 == 0) Color(0xFFE7EEFF) else Color.Transparent
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(bgColor, RoundedCornerShape(4.dp))
                            .padding(vertical = 12.dp)
                            .clickable { navController.navigate("reserva_sala/$sala") }
                    ) {
                        Text(
                            text = sala,
                            color = Color(0xFF044EE7),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            Spacer(modifier = Modifier.weight(1f))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = null,
                    tint = Color.Black
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.clickable {
                        navController.navigate(Route.HistoricoReservas.path)
                    }
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_list),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.clickable {
                        navController.navigate(Route.ReservasRealizadas.path)
                    }
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.clickable {
                        navController.navigate(Route.PerfilAluno.path)
                    }
                )
            }
        }

        if (menuAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .zIndex(0.5f)
                    .clickable(
                        onClick = { menuAberto = false },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
            )

            MenuLateral(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .zIndex(1f)
                    .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)),
                navController = navController
            )
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {

                    navController.navigate(Route.TelaChatbotUsuario.path)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .size(50.dp),
                containerColor = Color(0xFF3F4F78),
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.unibo),
                    contentDescription = "Chatbot Unibô",
                    modifier = Modifier.size(30.dp)
                )
            }
        }


    }
}



@Preview(showBackground = true)
@Composable
fun TelaSalasDisponiveisPreview() {

    if (androidx.compose.ui.platform.LocalInspectionMode.current) {

        val fakeNavController = rememberNavController()

        BibliotecaUniforTheme {
            TelaSalasDisponiveis(
                navController = fakeNavController,
                onVoltarClick = {},
                onSalaClick = {}
            )
        }
    }
}
