package com.bibliotecaunifor.Adm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.Route
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import com.bibliotecaunifor.Adm.AdminTopBarUsuarios
import com.bibliotecaunifor.Adm.AppBottomNavAdminPadrao
import com.bibliotecaunifor.Adm.MenuLateralAdmin
import com.bibliotecaunifor.R
import com.bibliotecaunifor.ui.theme.roxoBotao

data class UsuarioAdmin(
    val id: Int,
    val nome: String,
    val isBlocked: Boolean = false
)

val usuariosExemplo = listOf(
    UsuarioAdmin(1, "ISABELLE FEITOSA"),
    UsuarioAdmin(2, "MARIA CLARA MARQUES"),
    UsuarioAdmin(3, "ROSA MARTINS"),
    UsuarioAdmin(4, "ANA CLARA DOSTOIÉVSKI"),
    UsuarioAdmin(5, "ANALICE CASTRO"),
    UsuarioAdmin(6, "JÚLIA MENDES"),
    UsuarioAdmin(7, "PEDRO AUGUSTO"),
    UsuarioAdmin(8, "CAIO HENRIQUE")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAdminGerenciarUsuarios(
    navController: NavController,
    onVoltarClick: () -> Unit,
    onNavSalasClick: () -> Unit,
    onNavReservasClick: () -> Unit,
    onNavUsuariosClick: () -> Unit,
    onNavPerfilClick: () -> Unit,
    onCadastrarUsuarioClick: () -> Unit,
    onEditarUsuarioClick: (UsuarioAdmin) -> Unit,
    onExcluirUsuarioClick: (UsuarioAdmin) -> Unit,
    currentRoute: String
) {
    val azulPrimario = Color(0xFF3F4F78)
    val menuLateralAberto = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                AdminTopBarUsuarios(
                    onVoltarClick = onVoltarClick,
                    onNotificacoesClick = { navController.navigate(Route.TelaNotificacoesAdmin.path) },
                    onMenuClick = { menuLateralAberto.value = true }
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
                    text = "Usuários",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 20.dp, top = 16.dp, bottom = 12.dp),
                    color = Color.Black
                )

                OutlinedTextField(
                    value = "",
                    onValueChange = { /* Implementar mudança de valor */ },
                    placeholder = { Text("pesquisar usuário...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Pesquisar", tint = Color.Gray) },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(50.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = azulPrimario,
                        unfocusedBorderColor = Color.LightGray,
                        cursorColor = azulPrimario
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(usuariosExemplo) { usuario ->
                        UsuarioItem(
                            usuario = usuario,
                            // As ações onEditarClick e onExcluirClick esperam () -> Unit,
                            // então chamamos a função superior passando o objeto `usuario`.
                            onEditarClick = { onEditarUsuarioClick(usuario) },
                            onExcluirClick = { onExcluirUsuarioClick(usuario) }
                        )
                        Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 20.dp))
                    }
                }

                Button(
                    onClick = onCadastrarUsuarioClick,
                    colors = ButtonDefaults.buttonColors(containerColor = azulPrimario),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Text(
                        "CADASTRAR NOVO USUÁRIO",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        // Exibir o MenuLateralAdmin se o estado for verdadeiro
        if (menuLateralAberto.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f))
                    .clickable { menuLateralAberto.value = false },
                contentAlignment = Alignment.TopEnd
            ) {
                MenuLateralAdmin(
                    modifier = Modifier.fillMaxHeight().fillMaxWidth(0.75f),
                    navController = navController,
                    onLinkClick = { menuLateralAberto.value = false } // <-- CORREÇÃO 1 APLICADA AQUI
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
    val azulUniforClaro = Color(0xFF044EE7)
    val cinzaEscuro = Color.Gray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Ícone de Perfil",
                    tint = cinzaEscuro,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = usuario.nome,
                color = azulUniforClaro,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Row {
            IconButton(onClick = onExcluirClick, modifier = Modifier.size(36.dp).pointerHoverIcon(PointerIcon.Hand)) {
                Icon(Icons.Default.Delete, contentDescription = "Excluir", tint = Color.Black)
            }
            IconButton(onClick = onEditarClick, modifier = Modifier.size(36.dp).pointerHoverIcon(PointerIcon.Hand)) {
                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Black)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TelaAdminGerenciarUsuariosPreview() {
    // Simulação dos componentes externos para o Preview
    @Composable fun AdminTopBarUsuarios(onVoltarClick: () -> Unit, onNotificacoesClick: () -> Unit, onMenuClick: () -> Unit) {
        TopAppBar(title = { Text("Gerenciar Usuários") }, navigationIcon = { IconButton(onClick = onVoltarClick) { Icon(Icons.Default.Delete, "Voltar") } })
    }
    @Composable fun AppBottomNavAdminPadrao(onHomeClick: () -> Unit, onHistoricoClick: () -> Unit, onListasClick: () -> Unit, onPerfilClick: () -> Unit, currentRoute: String) {
        NavigationBar { NavigationBarItem(selected = true, onClick = onListasClick, icon = { Icon(Icons.Default.Person, "Usuários") }) }
    }
    @Composable fun MenuLateralAdmin(modifier: Modifier, navController: NavController, onLinkClick: () -> Unit) { // <-- CORREÇÃO 2 APLICADA AQUI
        Box(modifier.background(Color.Black.copy(alpha = 0.5f))) {
            Text("MENU ADMIN", color = Color.White, modifier = Modifier.padding(30.dp))
        }
    }

    BibliotecaUniforTheme {
        TelaAdminGerenciarUsuarios(
            navController = rememberNavController(),
            onVoltarClick = {},
            onNavSalasClick = {},
            onNavReservasClick = {},
            onNavUsuariosClick = {},
            onNavPerfilClick = {},
            onCadastrarUsuarioClick = {},
            onEditarUsuarioClick = { usuario -> /* Ação de navegação simulada */ },
            onExcluirUsuarioClick = { usuario -> /* ... */ },
            currentRoute = Route.TelaAdminGerenciarUsuarios.path
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAdminEditarPerfil(
    navController: NavController,
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onMenuClick: () -> Unit,
    onConfirmarEdicao: (
        novoNome: String,
        novaMatricula: String,
        novoEmail: String,
        novoTelefone: String,
        novoCpf: String
    ) -> Unit
) {

    var menuLateralAberto by remember { mutableStateOf(false) }


    var nome by remember { mutableStateOf("Pedro Augusto") }
    var matricula by remember { mutableStateOf("2345989") }
    var email by remember { mutableStateOf("PedroAugusto@gmail.com") }
    var telefone by remember { mutableStateOf("82 9 8273-9280") }
    var cpf by remember { mutableStateOf("888.222.444-99") }

    val scrollState = rememberScrollState()


    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                AdminTopBarUsuarios(
                    onVoltarClick = onVoltarClick,
                    onNotificacoesClick = onNotificacoesClick,

                    onMenuClick = { menuLateralAberto = true }
                )
            },

            ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Editar Perfil",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "Avatar do usuário",
                    modifier = Modifier
                        .size(96.dp)
                        .padding(bottom = 16.dp),
                    tint = Color.Gray
                )


                TextFieldCustom(value = nome, label = "Nome completo", onValueChange = { nome = it })
                TextFieldCustom(value = matricula, label = "Matrícula", onValueChange = { matricula = it }, keyboardType = KeyboardType.Companion.Number)
                TextFieldCustom(value = email, label = "Email", onValueChange = { email = it }, keyboardType = KeyboardType.Companion.Email)
                TextFieldCustom(value = telefone, label = "Telefone", onValueChange = { telefone = it }, keyboardType = KeyboardType.Companion.Phone)
                TextFieldCustom(value = cpf, label = "CPF", onValueChange = { cpf = it }, keyboardType = KeyboardType.Companion.Number)

                Spacer(modifier = Modifier.height(32.dp))


                Button(
                    onClick = {
                        onConfirmarEdicao(nome, matricula, email, telefone, cpf)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = roxoBotao),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(55.dp)
                ) {
                    Text("CONFIRMAR", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }


        if (menuLateralAberto) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))

                    .clickable { menuLateralAberto = false }
            )


            MenuLateralAdmin(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .fillMaxHeight()
                    .fillMaxWidth(0.75f)
                    .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)),
                navController = navController,
                onLinkClick = { menuLateralAberto = false } // <-- A CORREÇÃO
            )
        }
    }
}


@Composable
fun TextFieldCustom(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Companion.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.LightGray,
            cursorColor = Color.Black
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Preview(showBackground = true)
@Composable
fun TelaAdminEditarPerfilPreview() {
    Surface(color = Color.White) {
        val navController = rememberNavController()

        TelaAdminEditarPerfil(
            navController = navController,
            onVoltarClick = { /* */ },
            onNotificacoesClick = { /* */ },
            onMenuClick = { /* */ },
            onConfirmarEdicao = { _, _, _, _, _ -> /* */ }
        )
    }
}