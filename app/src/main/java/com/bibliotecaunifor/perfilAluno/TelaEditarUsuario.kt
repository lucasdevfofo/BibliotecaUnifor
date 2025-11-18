package com.bibliotecaunifor.perfilAluno

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.MenuLateral
import com.bibliotecaunifor.R
import com.bibliotecaunifor.Route
import com.bibliotecaunifor.model.UsuarioModel
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import com.bibliotecaunifor.viewmodel.TelaEditarUsuarioViewModel

@Composable
fun AppHeaderEditarUsuario(
    navController: NavController,
    menuAberto: MutableState<Boolean>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.livros), // Certifique-se que essa imagem existe
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
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.Black
                )
            }

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center
            ) {
                // Se não tiver o logo no drawable, comente essa linha ou adicione a imagem
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.width(56.dp))
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp, top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(
                onClick = { navController.navigate(Route.Notificacoes.path) },
                modifier = Modifier
                    .size(40.dp)
                    .pointerHoverIcon(PointerIcon.Hand)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(
                onClick = { menuAberto.value = !menuAberto.value },
                modifier = Modifier
                    .size(40.dp)
                    .pointerHoverIcon(PointerIcon.Hand)
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
}

@Composable
fun AppBottomNavEditarUsuario(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.Home, null, tint = Color.Gray,
            modifier = Modifier.clickable { navController.navigate(Route.SalasDisponiveis.path) }
        )
        Icon(Icons.Filled.DateRange, null, tint = Color.Gray,
            modifier = Modifier.clickable { navController.navigate(Route.ReservasRealizadas.path) }
        )
        Icon(Icons.Filled.List, null, tint = Color.Gray,
            modifier = Modifier.clickable { navController.navigate(Route.CatalogoLivros.path) }
        )
        Icon(Icons.Filled.Person, null, tint = Color.Black,
            modifier = Modifier.clickable { navController.navigate(Route.PerfilAluno.path) }
        )
    }
}
@Composable
fun TelaEditarUsuario(
    navController: NavController,
    viewModel: TelaEditarUsuarioViewModel = viewModel()
) {
    val menuAberto = remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Observa estados
    val uiState by viewModel.uiState.collectAsState()
    val dadosDoBanco by viewModel.usuarioAtual.collectAsState() // <--- NOVO

    // Variáveis de texto (Iniciam vazias para não piscar dados falsos)
    var nome by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var curso by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    // 1. Busca os dados assim que a tela abre
    LaunchedEffect(Unit) {
        viewModel.carregarDadosUsuario()
    }

    // 2. Quando os dados chegarem do banco, preenche os campos
    LaunchedEffect(dadosDoBanco) {
        dadosDoBanco?.let { user ->
            nome = user.nomeCompleto
            matricula = user.matricula
            email = user.email
            telefone = user.telefone
            cpf = user.cpf
            curso = user.curso
        }
    }

    // Lógica de Sucesso/Erro (Manteve igual)
    LaunchedEffect(uiState) {
        when(uiState) {
            is TelaEditarUsuarioViewModel.UiState.Success -> {
                Toast.makeText(context, "Dados atualizados!", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
            is TelaEditarUsuarioViewModel.UiState.Error -> {
                val erro = (uiState as TelaEditarUsuarioViewModel.UiState.Error).mensagem
                Toast.makeText(context, erro, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    // ... Resto do código (Scaffold, etc) continua igual ...

    Scaffold(
        topBar = {
            AppHeaderEditarUsuario(
                navController = navController,
                menuAberto = menuAberto
            )
        },
        bottomBar = { AppBottomNavEditarUsuario(navController = navController) }
    ) { paddingValues ->

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Editar Usuário",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                Icon(
                    imageVector = Icons.Filled.PersonOutline,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = Color.LightGray
                )

                Spacer(modifier = Modifier.height(5.dp))

                // --- FORMULÁRIO ---
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome", fontSize = 14.sp) },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF044EE7),
                        unfocusedBorderColor = Color.LightGray
                    )
                )
                OutlinedTextField(
                    value = matricula,
                    onValueChange = { matricula = it },
                    label = { Text("Matrícula", fontSize = 14.sp) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF044EE7),
                        unfocusedBorderColor = Color.LightGray
                    )
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", fontSize = 14.sp) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF044EE7),
                        unfocusedBorderColor = Color.LightGray
                    )
                )
                OutlinedTextField(
                    value = telefone,
                    onValueChange = { telefone = it },
                    label = { Text("Telefone", fontSize = 14.sp) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF044EE7),
                        unfocusedBorderColor = Color.LightGray
                    )
                )
                OutlinedTextField(
                    value = cpf,
                    onValueChange = { cpf = it },
                    label = { Text("CPF", fontSize = 14.sp) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF044EE7),
                        unfocusedBorderColor = Color.LightGray
                    )
                )
                OutlinedTextField(
                    value = curso,
                    onValueChange = { curso = it },
                    label = { Text("Curso", fontSize = 14.sp) },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF044EE7),
                        unfocusedBorderColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                // --- BOTÃO DE AÇÃO ---
                Button(
                    onClick = {
                        // Cria o objeto com os dados da tela
                        val usuarioAtualizado = UsuarioModel(
                            nomeCompleto = nome,
                            matricula = matricula,
                            curso = curso,
                            cpf = cpf,
                            telefone = telefone,
                            email = email
                        )
                        // Chama o ViewModel para salvar no Firebase
                        viewModel.salvarAlteracoes(usuarioAtualizado)
                    },
                    // Desabilita o botão enquanto estiver carregando para evitar múltiplos cliques
                    enabled = uiState !is TelaEditarUsuarioViewModel.UiState.Loading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F4F78)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (uiState is TelaEditarUsuarioViewModel.UiState.Loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            "CONFIRMAR",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
            }

            // --- LOADING OVERLAY (Opcional, caso queira travar a tela toda) ---
            // if (uiState is TelaEditarUsuarioViewModel.UiState.Loading) { ... }

            // --- MENU LATERAL ---
            if (menuAberto.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                        .zIndex(0.5f)
                        .clickable { menuAberto.value = false }
                )
                // Certifique-se que o componente MenuLateral existe no seu projeto
                MenuLateral(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .zIndex(1f)
                        .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)),
                    navController = navController
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaEditarUsuarioPreview() {
    val navController = rememberNavController()
    BibliotecaUniforTheme {
        // Mock do ViewModel não é necessário para Preview simples de UI,
        // mas se o preview quebrar, você pode criar um FakeViewModel
        TelaEditarUsuario(navController)
    }
}