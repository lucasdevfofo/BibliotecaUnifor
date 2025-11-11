package com.bibliotecaunifor.Usuario

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.R
import com.bibliotecaunifor.Route
import com.bibliotecaunifor.MenuLateral

data class NotificacaoUsuario(
    val id: String,
    val remetente: String,
    val mensagem: String,
    val isAdmin: Boolean = false
)

@Composable
fun TelaNotificacoesUsuario(
    navController: NavController,
    onVoltarClick: () -> Unit
) {
    var menuAberto by remember { mutableStateOf(false) }

    val notificacoes = remember {
        listOf(
            NotificacaoUsuario(
                id = "1",
                remetente = "ADMINISTRADOR",
                mensagem = "A renovação de livros por email foi descontinuada. Use o botão RENOVAR na tela de perfil.",
                isAdmin = true
            ),
            NotificacaoUsuario(
                id = "2",
                remetente = "PEDRO AUGUSTO",
                mensagem = "Boa noite! Biblioteca estará fechada hoje para manutenção."
            ),
            NotificacaoUsuario(
                id = "3",
                remetente = "JOSÉ ALBERTO",
                mensagem = "Novos livros de tecnologia chegaram hoje na biblioteca."
            ),
            NotificacaoUsuario(
                id = "4",
                remetente = "RODRIGO SILVA",
                mensagem = "Mensagem de notificação 1 – texto longo para testar rolagem."
            )
        )
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

                    IconButton(
                        onClick = onVoltarClick,
                        modifier = Modifier
                            .size(40.dp)
                            .pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo UNIFOR",
                        modifier = Modifier.size(40.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))


                    Spacer(modifier = Modifier.size(40.dp))
                }


                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 16.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(
                        onClick = {   },
                        modifier = Modifier
                            .size(40.dp)
                            .pointerHoverIcon(PointerIcon.Hand)
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
                        modifier = Modifier
                            .size(40.dp)
                            .pointerHoverIcon(PointerIcon.Hand)
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
                        Text("Suas notificações", color = Color.White, fontSize = 18.sp)
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
                text = "Notificações",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 20.dp),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                items(notificacoes) { notificacao ->
                    ItemNotificacaoUsuario(notificacao = notificacao)
                    Spacer(modifier = Modifier.height(12.dp))
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
                    contentDescription = "Home",
                    tint = Color.Black,
                    modifier = Modifier.clickable { /* Navegar para home */ }
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "Calendário",
                    tint = Color.Gray,
                    modifier = Modifier.clickable { navController.navigate(Route.HistoricoReservas.path) }
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_list),
                    contentDescription = "Lista",
                    tint = Color.Gray,
                    modifier = Modifier.clickable { navController.navigate(Route.ReservasRealizadas.path) }
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "Perfil",
                    tint = Color.Gray,
                    modifier = Modifier.clickable { navController.navigate(Route.PerfilAluno.path) }
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
    }
}

@Composable
fun ItemNotificacaoUsuario(notificacao: NotificacaoUsuario) {
    val corFundo = if (notificacao.isAdmin) Color(0xFFE7EEFF) else Color.Transparent
    val corTextoRemetente = if (notificacao.isAdmin) Color(0xFF044EE7) else Color.Black

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(corFundo, RoundedCornerShape(4.dp))
            .padding(vertical = 12.dp)
    ) {
        Column {
            Text(
                text = notificacao.remetente,
                color = corTextoRemetente,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = notificacao.mensagem,
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }


    Divider(
        color = Color.LightGray.copy(alpha = 0.5f),
        thickness = 1.dp,
        modifier = Modifier.padding(top = 4.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaNotificacoesUsuarioPreview() {
    TelaNotificacoesUsuario(
        navController = rememberNavController(),
        onVoltarClick = {}
    )
}