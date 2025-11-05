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
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.bibliotecaunifor.R
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

data class SalaAdmin(
    val nome: String,
    val totalMesas: Int,
    val mesasDisponiveis: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAdminGerenciarSalas(
    onVoltarClick: () -> Unit,
    onCadastrarNovaSalaClick: () -> Unit,
    onGerenciarSalaClick: (String) -> Unit
) {
    val azulPrimario = Color(0xFF3F4F78)
    val azulClaroUnifor = Color(0xFF044EE7)
    val amareloDisponivel = Color(0xFFD3A82C)

    val salas = listOf(
        SalaAdmin("SALA 01", 23, 18),
        SalaAdmin("SALA 02", 23, 12),
        SalaAdmin("SALA 03", 23, 20),
        SalaAdmin("SALA 04", 23, 18),
        SalaAdmin("SALA 05", 28, 28)
    )

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
                    contentDescription = "Imagem de fundo biblioteca",
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )

                // 1. BARRA BRANCA DE FUNDO
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color.White)
                        .align(Alignment.TopCenter)
                )

                // 2. BOTÃO DE VOLTAR (Canto Esquerdo)
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

                // 3. LOGO CENTRALIZADA (Centro Horizontal e Topo)
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo UNIFOR",
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopCenter) // Alinha no centro X da Box pai
                        .offset(y = 8.dp) // Offset para centralizar visualmente na altura 56dp (56/2 - 40/2 = 8dp)
                        .zIndex(2f)
                )

                // 4. BOTÕES DE NOTIFICAÇÃO E MENU (Canto Direito)
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 16.dp, top = 8.dp)
                        .zIndex(2f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(
                        onClick = {  },
                        modifier = Modifier.size(40.dp).pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(Icons.Outlined.Notifications, "Notificações", tint = Color.Black, modifier = Modifier.size(28.dp))
                    }

                    IconButton(
                        onClick = {  },
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
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    "CADASTRAR NOVA MESA/SALA",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            AppBottomNavAdminPadrao()
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

@Composable
fun AppBottomNavAdminPadrao() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painterResource(id = R.drawable.ic_home), "Home", tint = Color.Black)
        Icon(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = "Histórico",
            tint = Color.Black,
            modifier = Modifier.clickable {  }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_list),
            contentDescription = "Listas",
            tint = Color.Gray,
            modifier = Modifier.clickable {  }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_user),
            contentDescription = "Perfil",
            tint = Color.Black,
            modifier = Modifier.clickable {  }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TelaAdminGerenciarSalasPreview() {
    BibliotecaUniforTheme {
        TelaAdminGerenciarSalas(
            onVoltarClick = {},
            onCadastrarNovaSalaClick = {},
            onGerenciarSalaClick = {}
        )
    }
}