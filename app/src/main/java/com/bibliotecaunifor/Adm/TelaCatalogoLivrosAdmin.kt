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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.Route

data class LivroCatalogoAdmin(val titulo: String, val id: Int)


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
    currentRoute: String,
    onAdicionarLivroClick: () -> Unit,
    onEditarLivroClick: (LivroCatalogoAdmin) -> Unit,
    onExcluirLivroClick: (LivroCatalogoAdmin) -> Unit
) {
    var menuLateralAberto by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val cinzaFundo = Color(0xFFF0F0F0)
    val roxoBotao = Color(0xFF004AF5)

    val livros = remember {
        listOf(
            LivroCatalogoAdmin("PEQUENO PRÍNCIPE", 1),
            LivroCatalogoAdmin("NOITES BRANCAS", 2),
            LivroCatalogoAdmin("CÓDIGO LIMPO", 3),
            LivroCatalogoAdmin("ENTENDENDO ALGORITMOS", 4),
            LivroCatalogoAdmin("JAVASCRIPT: O GUIA DEFINITIVO", 5)
        )
    }

    val filteredLivros = livros.filter {
        it.titulo.contains(searchText, ignoreCase = true)
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

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 20.dp)
                ) {
                    items(filteredLivros) { livro ->
                        LivroAdminItem(
                            livro = livro,
                            onEditarClick = onEditarLivroClick,
                            onExcluirClick = onExcluirLivroClick,
                            corEditar = roxoBotao
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = onAdicionarLivroClick)
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
    livro: LivroCatalogoAdmin,
    onEditarClick: (LivroCatalogoAdmin) -> Unit,
    onExcluirClick: (LivroCatalogoAdmin) -> Unit,
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
        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            Icons.Default.Delete,
            contentDescription = "Excluir Livro",
            tint = Color.Black,
            modifier = Modifier
                .size(20.dp)
                .clickable { onExcluirClick(livro) }
        )
        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            Icons.Default.Edit,
            contentDescription = "Editar Livro",
            tint = Color.Black,
            modifier = Modifier
                .size(20.dp)
                .clickable { onEditarClick(livro) }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TelaCatalogoLivrosAdminPreview() {
    val roxoBotaoPreview = Color(0xFF004AF5)

    @Composable fun AdminTopBar(onVoltarClick: () -> Unit, onNotificacoesClick: () -> Unit, onMenuClick: () -> Unit) {}
    @Composable fun AppBottomNavAdminPadrao(onHomeClick: () -> Unit, onHistoricoClick: () -> Unit, onListasClick: () -> Unit, onPerfilClick: () -> Unit, currentRoute: String) {}
    @Composable fun MenuLateralAdmin(modifier: Modifier, navController: NavController, onLinkClick: () -> Unit) {
        Box(modifier = modifier.background(Color.Gray.copy(alpha = 0.5f)))
    }

    TelaCatalogoLivrosAdmin(
        navController = rememberNavController(),
        onVoltarClick = { },
        onNotificacoesClick = { },
        onMenuClick = { },
        onNavSalasClick = { },
        onNavReservasClick = { },
        onNavUsuariosClick = { },
        onNavPerfilClick = { },
        currentRoute = Route.CatalogoLivros.path,
        onAdicionarLivroClick = { },
        onEditarLivroClick = { _: LivroCatalogoAdmin -> },
        onExcluirLivroClick = { _: LivroCatalogoAdmin -> }
    )
}