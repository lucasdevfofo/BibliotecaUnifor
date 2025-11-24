package com.bibliotecaunifor.Adm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.Route
import com.bibliotecaunifor.model.Livro
import com.bibliotecaunifor.viewmodel.TelaAdminLivrosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCatalogoLivrosAdmin(
    navController: NavController,
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onMenuClick: () -> Unit,
    onNavSalasClick: () -> Unit,
    onNavReservasClick: () -> Unit,
    onNavUsuariosClick: () -> Unit,
    onNavPerfilClick: () -> Unit,
    currentRoute: String
) {
    val viewModel: TelaAdminLivrosViewModel = viewModel()
    var menuLateralAberto by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var livroParaExcluir by remember { mutableStateOf<Livro?>(null) }

    val cinzaFundo = Color(0xFFF0F0F0)
    val roxoBotao = Color(0xFF004AF5)

    val livros by viewModel.livros.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val mensagem by viewModel.mensagem.collectAsState()

    val filteredLivros = if (searchText.isBlank()) {
        livros
    } else {
        livros.filter { it.titulo.contains(searchText, ignoreCase = true) }
    }

    LaunchedEffect(Unit) {
        viewModel.carregarLivros()
    }

    LaunchedEffect(mensagem) {
        if (mensagem?.contains("sucesso") == true) {
            viewModel.carregarLivros()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                AdminTopBar(
                    onVoltarClick = onVoltarClick,
                    onNotificacoesClick = onNotificacoesClick,
                    onMenuClick = { menuLateralAberto = true }
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "CATÁLOGO DE LIVROS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = { Text("Pesquise um livro...", fontSize = 14.sp) },
                        trailingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Pesquisar")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = cinzaFundo,
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.LightGray
                        )
                    )
                }

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 20.dp)
                    ) {
                        items(filteredLivros) { livro ->
                            LivroAdminItem(
                                livro = livro,
                                onEditarClick = {
                                    navController.navigate("${Route.TelaAdminEditarLivro.path}/${livro.id}")
                                },
                                onExcluirClick = {
                                    livroParaExcluir = livro
                                    showDeleteDialog = true
                                },
                                corEditar = roxoBotao
                            )
                        }

                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(onClick = { navController.navigate(Route.TelaAdicionarLivroAdmin.path) })
                                    .padding(vertical = 8.dp)
                                    .height(40.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "ADICIONAR LIVRO",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Gray,
                                    modifier = Modifier.weight(1f).padding(start = 6.dp)
                                )
                                Icon(
                                    Icons.Default.AddCircle,
                                    contentDescription = "Adicionar Livro",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Excluir Livro") },
                text = { Text("Tem certeza que deseja excluir o livro \"${livroParaExcluir?.titulo}\"?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            livroParaExcluir?.let { livro ->
                                viewModel.excluirLivro(livro.id)
                                showDeleteDialog = false
                                livroParaExcluir = null
                            }
                        }
                    ) {
                        Text("Excluir", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDeleteDialog = false
                            livroParaExcluir = null
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }

        if (menuLateralAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f))
                    .clickable { menuLateralAberto = false },
                contentAlignment = Alignment.TopEnd
            ) {
                MenuLateralAdmin(
                    modifier = Modifier.fillMaxHeight().fillMaxWidth(0.75f),
                    navController = navController,
                    onLinkClick = { menuLateralAberto = false }
                )
            }
        }
    }
}

@Composable
fun LivroAdminItem(
    livro: Livro,
    onEditarClick: (Livro) -> Unit,
    onExcluirClick: (Livro) -> Unit,
    corEditar: Color
) {
    val corFundoItem = Color(0xFFE7EEFF)
    val corTextoTitulo = Color(0xFF044EE7)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(corFundoItem, RoundedCornerShape(4.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = livro.titulo,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = corTextoTitulo,
            modifier = Modifier.weight(1f).padding(start = 6.dp)
        )

        Text(
            text = livro.disponibilidade,
            fontSize = 12.sp,
            color = if (livro.disponibilidade == "Disponível") Color.Green else Color.Red,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            Icons.Default.Delete,
            contentDescription = "Excluir Livro",
            tint = Color.Red,
            modifier = Modifier
                .size(20.dp)
                .clickable { onExcluirClick(livro) }
        )
        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            Icons.Default.Edit,
            contentDescription = "Editar Livro",
            tint = corEditar,
            modifier = Modifier
                .size(20.dp)
                .clickable { onEditarClick(livro) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TelaCatalogoLivrosAdminPreview() {
    TelaCatalogoLivrosAdmin(
        navController = rememberNavController(),
        onVoltarClick = { },
        onNotificacoesClick = { },
        onMenuClick = { },
        onNavSalasClick = { },
        onNavReservasClick = { },
        onNavUsuariosClick = { },
        onNavPerfilClick = { },
        currentRoute = Route.TelaCatalogoLivrosAdmin.path
    )
}