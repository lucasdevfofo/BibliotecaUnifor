package com.bibliotecaunifor.Adm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

data class UsuarioAdmin(
    val id: Int,
    val nome: String,
    val email: String,
    val matricula: String,
    val isBlocked: Boolean = false
)

val usuariosExemplo = listOf(
    UsuarioAdmin(1, "ISABELLE FEITOSA", "isabelle@edu.unifor.br", "2345987"),
    UsuarioAdmin(2, "MARIA CLARA MARQUES", "maria@edu.unifor.br", "2345988"),
    UsuarioAdmin(3, "ROSA MARTINS", "rosa@edu.unifor.br", "2345989"),
    UsuarioAdmin(4, "ANA CLARA DOSTOIÉVSKI", "ana@edu.unifor.br", "2345990"),
    UsuarioAdmin(5, "ANALICE CASTRO", "analice@edu.unifor.br", "2345991"),
    UsuarioAdmin(6, "JÚLIA MENDES", "julia@edu.unifor.br", "2345992"),
    UsuarioAdmin(7, "PEDRO AUGUSTO", "pedro@edu.unifor.br", "2345993"),
    UsuarioAdmin(8, "CAIO HENRIQUE", "caio@edu.unifor.br", "2345994")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAdminGerenciarUsuarios(
    navController: NavController,
    onVoltarClick: () -> Unit = {},
    onNavSalasClick: () -> Unit = {},
    onNavReservasClick: () -> Unit = {},
    onNavUsuariosClick: () -> Unit = {},
    onNavPerfilClick: () -> Unit = {},
    onCadastrarUsuarioClick: () -> Unit = {},
    onEditarUsuarioClick: (UsuarioAdmin) -> Unit = {},
    onExcluirUsuarioClick: (UsuarioAdmin) -> Unit = {},
    currentRoute: String = ""
) {
    var menuLateralAberto by remember { mutableStateOf(false) }
    var textoPesquisa by remember { mutableStateOf("") }

    val usuariosFiltrados = usuariosExemplo.filter { usuario ->
        usuario.nome.contains(textoPesquisa, ignoreCase = true) ||
                usuario.email.contains(textoPesquisa, ignoreCase = true) ||
                usuario.matricula.contains(textoPesquisa, ignoreCase = true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                AdminTopBarUsuarios(
                    onVoltarClick = onVoltarClick,
                    onNotificacoesClick = {
                        // Navegar para notificações quando implementar
                    },
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
                // Título
                Text(
                    text = "Gerenciar Usuários",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp)
                )

                // Campo de pesquisa
                OutlinedTextField(
                    value = textoPesquisa,
                    onValueChange = { textoPesquisa = it },
                    placeholder = { Text("Pesquisar usuário...") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Pesquisar",
                            tint = Color.Gray
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(56.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF3F4F78),
                        unfocusedBorderColor = Color.LightGray,
                        cursorColor = Color(0xFF3F4F78)
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Lista de usuários
                if (usuariosFiltrados.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Nenhum usuário encontrado",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(usuariosFiltrados) { usuario ->
                            UsuarioItem(
                                usuario = usuario,
                                onEditarClick = { onEditarUsuarioClick(usuario) },
                                onExcluirClick = { onExcluirUsuarioClick(usuario) }
                            )
                            Divider(
                                color = Color.LightGray.copy(alpha = 0.5f),
                                thickness = 1.dp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }

                // Botão de cadastrar
                Button(
                    onClick = onCadastrarUsuarioClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3F4F78)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "CADASTRAR NOVO USUÁRIO",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Menu Lateral Overlay
        if (menuLateralAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { menuLateralAberto = false }
            ) {
                MenuLateralAdmin(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .fillMaxHeight()
                        .fillMaxWidth(0.75f),
                    navController = navController,
                    onLinkClick = { menuLateralAberto = false }
                )
            }
        }
    }
}

@Composable
fun UsuarioItem(
    usuario: UsuarioAdmin,
    onEditarClick: () -> Unit,
    onExcluirClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar do usuário
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "Usuário",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Informações do usuário
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = usuario.nome,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = usuario.email,
                color = Color.Gray,
                fontSize = 14.sp
            )
            Text(
                text = "Matrícula: ${usuario.matricula}",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }

        // Botões de ação
        Row {
            IconButton(
                onClick = onEditarClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = Color.Black
                )
            }
            IconButton(
                onClick = onExcluirClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Excluir",
                    tint = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaAdminGerenciarUsuariosPreview() {
    TelaAdminGerenciarUsuarios(
        navController = rememberNavController()
    )
}

@Preview(showBackground = true)
@Composable
fun UsuarioItemPreview() {
    val usuarioExemplo = UsuarioAdmin(
        1,
        "ISABELLE FEITOSA",
        "isabelle@edu.unifor.br",
        "2345987"
    )

    UsuarioItem(
        usuario = usuarioExemplo,
        onEditarClick = {},
        onExcluirClick = {}
    )
}