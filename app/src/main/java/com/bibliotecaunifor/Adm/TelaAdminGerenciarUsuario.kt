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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.Route
import com.bibliotecaunifor.model.UsuarioModel
import com.bibliotecaunifor.viewmodel.UsuarioAdminViewModel

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
    onEditarUsuarioClick: (String, UsuarioModel) -> Unit = { _, _ -> },
    onExcluirUsuarioClick: (String, UsuarioModel) -> Unit = { _, _ -> },
    currentRoute: String = ""
) {
    val viewModel: UsuarioAdminViewModel = viewModel()
    val usuarios by viewModel.usuariosSemAdmin.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val mensagem by viewModel.mensagem.collectAsState()

    var menuLateralAberto by remember { mutableStateOf(false) }
    var textoPesquisa by remember { mutableStateOf("") }

    LaunchedEffect(mensagem) {
        mensagem?.let {
            viewModel.limparMensagem()
        }
    }

    val usuariosFiltrados = usuarios.filter { (_, usuario) ->
        usuario.nomeCompleto.contains(textoPesquisa, ignoreCase = true) ||
                usuario.email.contains(textoPesquisa, ignoreCase = true) ||
                usuario.matricula.contains(textoPesquisa, ignoreCase = true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                AdminTopBarUsuarios(
                    onVoltarClick = onVoltarClick,
                    onNotificacoesClick = { navController.navigate(Route.TelaNotificacoesAdmin.path) },
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

                Text(
                    text = "Gerenciar Usuários",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp)
                )

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

                if (isLoading) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = Color(0xFF3F4F78))
                    }
                } else if (usuariosFiltrados.isEmpty()) {
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
                        items(usuariosFiltrados) { (uid, usuario) ->
                            UsuarioRealItem(
                                usuario = usuario,
                                onEditarClick = { onEditarUsuarioClick(uid, usuario) },
                                onExcluirClick = { onExcluirUsuarioClick(uid, usuario) }
                            )
                            Divider(
                                color = Color.LightGray.copy(alpha = 0.5f),
                                thickness = 1.dp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = onCadastrarUsuarioClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF3F4F78)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 4.dp
                        )
                    ) {
                        Text(
                            text = "CADASTRAR NOVO USUÁRIO",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }

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
fun UsuarioRealItem(
    usuario: UsuarioModel,
    onEditarClick: () -> Unit,
    onExcluirClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

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

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = usuario.nomeCompleto,
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
                text = "Matrícula: ${usuario.matricula} • ${usuario.curso}",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }

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
    val usuarioExemplo = UsuarioModel(
        nomeCompleto = "ISABELLE FEITOSA",
        email = "isabelle@edu.unifor.br",
        matricula = "2345987",
        curso = "Ciência da Computação",
        cpf = "123.456.789-00",
        telefone = "(85) 99999-9999",
        tipo = "usuario"
    )

    UsuarioRealItem(
        usuario = usuarioExemplo,
        onEditarClick = {},
        onExcluirClick = {}
    )
}