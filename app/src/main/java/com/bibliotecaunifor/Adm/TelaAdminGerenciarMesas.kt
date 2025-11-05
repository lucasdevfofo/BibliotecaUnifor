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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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

data class MesaAdmin(
    val numero: String,
    val disponivel: Boolean = true
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAdminGerenciarMesas(
    salaNome: String,
    onVoltarClick: () -> Unit,
    onEditarMesaClick: (String) -> Unit,
    onExcluirMesaClick: (String) -> Unit,
    onCadastrarNovaMesaClick: (String) -> Unit
) {
    val azulPrimario = Color(0xFF3F4F78)
    val azulClaroUnifor = Color(0xFF044EE7)

    val mesas = remember(salaNome) {
        when (salaNome) {
            "SALA 01" -> listOf(
                MesaAdmin("MESA 01"),
                MesaAdmin("MESA 02"),
                MesaAdmin("MESA 03"),
                MesaAdmin("MESA 04")
            )
            "SALA 02" -> listOf(
                MesaAdmin("MESA 01"),
                MesaAdmin("MESA 02"),
                MesaAdmin("MESA 03")
            )
            "SALA 03" -> listOf(
                MesaAdmin("MESA 01"),
                MesaAdmin("MESA 02"),
                MesaAdmin("MESA 03"),
                MesaAdmin("MESA 04"),
                MesaAdmin("MESA 05")
            )
            else -> emptyList()
        }
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
                text = salaNome,
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
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(mesas) { mesa ->
                    MesaAdminItem(
                        mesa = mesa,
                        azulClaroUnifor = azulClaroUnifor,
                        onEditarClick = { onEditarMesaClick(mesa.numero) },
                        onExcluirClick = { onExcluirMesaClick(mesa.numero) }
                    )
                }
            }

            Button(
                onClick = { onCadastrarNovaMesaClick(salaNome) },
                colors = ButtonDefaults.buttonColors(containerColor = azulPrimario),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    "CADASTRAR NOVA MESA NESTA SALA",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MesaAdminItem(
    mesa: MesaAdmin,
    azulClaroUnifor: Color,
    onEditarClick: (String) -> Unit,
    onExcluirClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "• ",
                color = azulClaroUnifor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = mesa.numero,
                color = azulClaroUnifor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(
                onClick = { onEditarClick(mesa.numero) },
                modifier = Modifier.size(30.dp).pointerHoverIcon(PointerIcon.Hand)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar Mesa",
                    tint = Color.Black
                )
            }
            IconButton(
                onClick = { onExcluirClick(mesa.numero) },
                modifier = Modifier.size(30.dp).pointerHoverIcon(PointerIcon.Hand)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Excluir Mesa",
                    tint = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaAdminGerenciarMesasPreview() {
    BibliotecaUniforTheme {
        TelaAdminGerenciarMesas(
            salaNome = "SALA 01",
            onVoltarClick = {},
            onEditarMesaClick = {},
            onExcluirMesaClick = {},
            onCadastrarNovaMesaClick = {}
        )
    }
}