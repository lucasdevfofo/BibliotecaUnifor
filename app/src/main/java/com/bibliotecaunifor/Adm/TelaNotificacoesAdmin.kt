package com.bibliotecaunifor.Adm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

data class Notificacao(
    val id: String,
    val remetente: String,
    val mensagem: String,
    val lida: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaNotificacoesAdmin(
    navController: NavController,
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onEnviarComunicadoClick: () -> Unit
) {
    var menuAberto by remember { mutableStateOf(false) }

    val notificacoes = remember {
        listOf(
            Notificacao(
                id = "1",
                remetente = "PEDRO AUGUSTO",
                mensagem = "Boa noite Pessoal! Biblioteca estará fechada hoje.",
                lida = false
            ),
            Notificacao(
                id = "2",
                remetente = "JOSÉ ALBERTO",
                mensagem = "Boa noite Pessoal! Hoje chegou novos livros na biblioteca.",
                lida = true
            ),
            Notificacao(
                id = "3",
                remetente = "MARIA SILVA",
                mensagem = "Lembrando que o prazo para renovação é até sexta-feira.",
                lida = false
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AdminTopBar(
                    onVoltarClick = onVoltarClick,
                    onNotificacoesClick = onNotificacoesClick,
                    onMenuClick = {
                        menuAberto = true
                    }
                )

                Text(
                    text = "NOTIFICAÇÕES",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .align(Alignment.Start)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(notificacoes.size) { index ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            if (!notificacoes[index].lida) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(Color.Red, shape = CircleShape)
                                        .align(Alignment.CenterVertically)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                            } else {
                                Spacer(modifier = Modifier.width(20.dp))
                            }

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = notificacoes[index].remetente,
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = notificacoes[index].mensagem,
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }
                        }
                        Divider(color = Color.LightGray, thickness = 1.dp)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "ENVIAR COMUNICADO",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = onEnviarComunicadoClick,
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color.Black,
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            text = "+",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        if (menuAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable {
                        menuAberto = false
                    }
            )

            MenuLateralAdmin(
                navController = navController,
                onLinkClick = {
                    menuAberto = false
                },
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaNotificacoesPreview() {
    TelaNotificacoesAdmin(
        navController = rememberNavController(),
        onVoltarClick = {},
        onNotificacoesClick = {},
        onEnviarComunicadoClick = {}
    )
}