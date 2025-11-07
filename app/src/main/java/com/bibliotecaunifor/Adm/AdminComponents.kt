package com.bibliotecaunifor.Adm

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.zIndex
import com.bibliotecaunifor.R
import com.bibliotecaunifor.Route

data class NavItemAdmin(
    val iconResId: Int,
    val label: String,
    val route: String,
    val onClick: () -> Unit
)

@Composable
fun AppBottomNavAdminPadrao(
    onHomeClick: () -> Unit,
    onHistoricoClick: () -> Unit,
    onListasClick: () -> Unit,
    onPerfilClick: () -> Unit,
    currentRoute: String
) {
    val branco = Color.White
    val cinzaEscuro = Color.Gray
    val azulSelecao = Color(0xFF004AF5)

    val navItems = listOf(
        NavItemAdmin(R.drawable.ic_home, "Salas", Route.TelaAdminGerenciarSalas.path, onHomeClick),
        NavItemAdmin(R.drawable.ic_calendar, "Reservas", Route.TelaAdminReservasRealizadas.path, onHistoricoClick),
        NavItemAdmin(R.drawable.ic_list, "Usuários", Route.TelaAdminGerenciarUsuarios.path, onListasClick),
        NavItemAdmin(R.drawable.ic_user, "Perfil", Route.TelaPerfilAdmin.path, onPerfilClick)
    )

    NavigationBar(
        modifier = Modifier.height(56.dp),
        containerColor = branco
    ) {
        navItems.forEach { item ->
            val isSelected = currentRoute.startsWith(item.route.substringBefore("/"))

            NavigationBarItem(
                selected = isSelected,
                onClick = item.onClick,
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconResId),
                        contentDescription = item.label,
                        tint = if (isSelected) azulSelecao else cinzaEscuro,
                        modifier = Modifier.size(28.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = azulSelecao,
                    unselectedIconColor = cinzaEscuro,
                    indicatorColor = branco
                )
            )
        }
    }
}

@Composable
fun AdminTopBar(
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onMenuClick: () -> Unit
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
}

@Composable
fun AdminTopBarUsuarios(
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    AdminTopBar(onVoltarClick, onNotificacoesClick, onMenuClick)
}

@Composable
fun AdminTopBarPerfil(
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White)
            .padding(horizontal = 4.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo UNIFOR",
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.Center)
        )

        IconButton(
            onClick = onVoltarClick,
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.CenterStart)
                .pointerHoverIcon(PointerIcon.Hand)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.Black
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNotificacoesClick,
                modifier = Modifier.size(48.dp).pointerHoverIcon(PointerIcon.Hand)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notificações",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(
                onClick = onMenuClick,
                modifier = Modifier.size(48.dp).pointerHoverIcon(PointerIcon.Hand)
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
}