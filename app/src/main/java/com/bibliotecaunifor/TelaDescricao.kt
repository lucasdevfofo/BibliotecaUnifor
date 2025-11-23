package com.bibliotecaunifor

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bibliotecaunifor.viewmodel.TelaDescricaoViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun TelaDescricaoLivro(
    navController: NavController,
    livroId: String, // <--- NOVO: Precisamos do ID aqui
    tituloLivro: String,
    descricao: String? = null,
    genero: String? = null,
    autor: String? = null,
    disponibilidade: String? = null,
    // <--- NOVO: Injetando o ViewModel
    viewModel: TelaDescricaoViewModel = viewModel()
) {
    var menuAberto by remember { mutableStateOf(false) }

    // Decodificando textos que vieram da rota
    val tituloDecoded = remember(tituloLivro) {
        URLDecoder.decode(tituloLivro, StandardCharsets.UTF_8.toString())
    }
    val descricaoDecoded = remember(descricao) {
        if (descricao != null) URLDecoder.decode(descricao, StandardCharsets.UTF_8.toString()) else null
    }

    // <--- LÓGICA DO BACKEND --->
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    // Verifica se está disponível (ignorando maiúsculas/minúsculas)
    val estaDisponivel = disponibilidade?.equals("Disponível", ignoreCase = true) == true

    // Reage ao resultado do aluguel (Sucesso ou Erro)
    LaunchedEffect(uiState) {
        when(uiState) {
            is TelaDescricaoViewModel.UiState.Success -> {
                Toast.makeText(context, "Livro alugado com sucesso!", Toast.LENGTH_LONG).show()
                navController.popBackStack() // Volta para o catálogo
            }
            is TelaDescricaoViewModel.UiState.Error -> {
                val erro = (uiState as TelaDescricaoViewModel.UiState.Error).message
                Toast.makeText(context, erro, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }
    // <-------------------------->

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- HEADER (MANTIDO IGUAL) ---
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
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .size(60.dp)
                            .pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.weight(0.63f))

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
                        modifier = Modifier.size(40.dp).pointerHoverIcon(PointerIcon.Hand)
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
                        modifier = Modifier.size(40.dp).pointerHoverIcon(PointerIcon.Hand)
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
                        Text(
                            text = "Reserve seu livro",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "Biblioteca Unifor",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- CONTEÚDO ---
            Text(
                text = tituloDecoded.uppercase(),
                color = Color(0xFF044EE7),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFFF3F7FF))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = descricaoDecoded ?: "Descrição não disponível.",
                        color = Color.Black,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("Gênero: ${genero ?: "N/A"}", color = Color.Black, fontSize = 14.sp)
                    Text("Autor: ${autor ?: "N/A"}", color = Color.Black, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- STATUS ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Disponibilidade:",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = if (estaDisponivel) "DISPONÍVEL" else "INDISPONÍVEL",
                    color = if (estaDisponivel) Color(0xFF00A86B) else Color.Red,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // --- BOTÃO ALUGAR ATUALIZADO ---
            Button(
                onClick = {
                    val tituloEncoded = java.net.URLEncoder.encode(tituloDecoded, "UTF-8")
                    navController.navigate("agendar_aluguel/$livroId/$tituloEncoded")
                },
                // Só ativa se estiver disponível E não estiver carregando
                enabled = estaDisponivel && uiState !is TelaDescricaoViewModel.UiState.Loading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E4C93),
                    disabledContainerColor = Color.Gray
                ),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .height(48.dp)
            ) {
                if (uiState is TelaDescricaoViewModel.UiState.Loading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        if (estaDisponivel) "ALUGAR" else "INDISPONÍVEL",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF555555)),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .height(48.dp)
            ) {
                Text("Voltar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        // --- MENU LATERAL (Mantido) ---
        if (menuAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .pointerHoverIcon(PointerIcon.Hand)
                    .clickable { menuAberto = false }
            )
            MenuLateral(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)),
                navController = navController
            )
        }
    }
}