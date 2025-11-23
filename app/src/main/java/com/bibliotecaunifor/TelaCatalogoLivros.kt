package com.bibliotecaunifor

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import android.net.Uri
import com.bibliotecaunifor.viewmodel.TelaCatalogoLivrosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCatalogoLivros(navController: NavController) {
    var menuAberto by remember { mutableStateOf(false) }
    var pesquisa by remember { mutableStateOf("") }
    val viewModel: TelaCatalogoLivrosViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.carregarLivros()
    }

    LaunchedEffect(pesquisa) {
        viewModel.pesquisarLivros(pesquisa)
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
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.size(60.dp).pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
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
                        Text("Catálogo de Livros", color = Color.White, fontSize = 18.sp)
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "RESERVE UM LIVRO",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = pesquisa,
                    onValueChange = { pesquisa = it },
                    placeholder = { Text("Pesquise um livro...") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                        focusedIndicatorColor = Color(0xFF044EE7),
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            when {
                viewModel.loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(48.dp),
                                color = Color(0xFF044EE7)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "Carregando livros...",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }

                viewModel.erro != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "Erro: ${viewModel.erro}",
                                fontSize = 16.sp,
                                color = Color.Red
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.carregarLivros() },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044EE7))
                            ) {
                                Text("Tentar Novamente")
                            }
                        }
                    }
                }

                viewModel.livrosFiltrados.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                if (pesquisa.isBlank()) "Nenhum livro cadastrado" else "Nenhum livro encontrado",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                            if (pesquisa.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "Tente buscar por outro termo",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 20.dp)
                    ) {
                        items(viewModel.livrosFiltrados) { livro ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFE7EEFF), RoundedCornerShape(4.dp))
                                    .padding(vertical = 12.dp)
                                    .clickable {
                                        // 1. CODIFICA O ID TAMBÉM
                                        val idEncoded = Uri.encode(livro.id) // <--- NOVO: Precisamos do ID!

                                        val tituloEncoded = Uri.encode(livro.titulo)
                                        val descricaoEncoded = Uri.encode(livro.descricao)
                                        val generoEncoded = Uri.encode(livro.genero)
                                        val autorEncoded = Uri.encode(livro.autor)
                                        val disponivelEncoded = Uri.encode(livro.disponibilidade)

                                        // 2. ADICIONA O ID NA STRING DA ROTA (Sugerido colocar no começo)
                                        navController.navigate(
                                            "descricao_livro/$idEncoded/$tituloEncoded/$descricaoEncoded/$generoEncoded/$autorEncoded/$disponivelEncoded"
                                        )
                                    }
                            ){
                                Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                                    Text(
                                        text = livro.titulo,
                                        color = Color(0xFF044EE7),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "por ${livro.autor}",
                                        color = Color(0xFF044EE7).copy(alpha = 0.7f),
                                        fontSize = 12.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }
            }
        }

        if (menuAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
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