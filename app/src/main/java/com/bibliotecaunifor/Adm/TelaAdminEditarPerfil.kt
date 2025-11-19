package com.bibliotecaunifor.Adm

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.R
import com.bibliotecaunifor.model.UsuarioModel
import com.bibliotecaunifor.ui.theme.roxoBotao
import com.bibliotecaunifor.viewmodel.TelaAdminEditarPerfilViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAdminEditarPerfil(
    navController: NavController,
    // Injetando o ViewModel aqui
    viewModel: TelaAdminEditarPerfilViewModel = viewModel(),
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    val context = LocalContext.current
    var menuLateralAberto by remember { mutableStateOf(false) }

    // Observando os estados do ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val adminDados by viewModel.adminAtual.collectAsState()

    // Variáveis de estado local (iniciam vazias para serem preenchidas pelo banco)
    var nome by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    // 1. Busca os dados do Admin ao abrir a tela
    LaunchedEffect(Unit) {
        viewModel.carregarDadosAdmin()
    }

    // 2. Quando os dados chegarem do banco, preenche os campos
    LaunchedEffect(adminDados) {
        adminDados?.let { user ->
            nome = user.nomeCompleto
            matricula = user.matricula
            email = user.email
            telefone = user.telefone
            cpf = user.cpf
        }
    }

    // 3. Gerencia mensagens de Sucesso ou Erro
    LaunchedEffect(uiState) {
        when (uiState) {
            is TelaAdminEditarPerfilViewModel.UiState.Success -> {
                Toast.makeText(context, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                // Volta para a tela anterior ou reseta o estado
                onVoltarClick()
            }
            is TelaAdminEditarPerfilViewModel.UiState.Error -> {
                val erro = (uiState as TelaAdminEditarPerfilViewModel.UiState.Error).mensagem
                Toast.makeText(context, erro, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

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

                // Componentes de Texto
                TextFieldCustom(value = nome, label = "Nome completo", onValueChange = { nome = it })
                TextFieldCustom(value = matricula, label = "Matrícula", onValueChange = { matricula = it }, keyboardType = KeyboardType.Number)
                TextFieldCustom(value = email, label = "Email", onValueChange = { email = it }, keyboardType = KeyboardType.Email)
                TextFieldCustom(value = telefone, label = "Telefone", onValueChange = { telefone = it }, keyboardType = KeyboardType.Phone)
                TextFieldCustom(value = cpf, label = "CPF", onValueChange = { cpf = it }, keyboardType = KeyboardType.Number)

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        // Cria objeto atualizado mantendo o tipo "admin" (tratado no VM)
                        val adminAtualizado = UsuarioModel(
                            nomeCompleto = nome,
                            matricula = matricula,
                            email = email,
                            telefone = telefone,
                            cpf = cpf
                        )
                        viewModel.salvarPerfilAdmin(adminAtualizado)
                    },
                    // Desativa botão enquanto carrega
                    enabled = uiState !is TelaAdminEditarPerfilViewModel.UiState.Loading,
                    colors = ButtonDefaults.buttonColors(containerColor = roxoBotao),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(55.dp)
                ) {
                    if (uiState is TelaAdminEditarPerfilViewModel.UiState.Loading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("CONFIRMAR", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
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
                onLinkClick = { menuLateralAberto = false }
            )
        }
    }
}

@Composable
fun TextFieldCustom(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
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
        // Mock do ViewModel não necessário para preview visual simples,
        // mas idealmente usaria um FakeViewModel se o preview quebrasse.
        TelaAdminEditarPerfil(
            navController = navController,
            onVoltarClick = { /* */ },
            onNotificacoesClick = { /* */ },
            onMenuClick = { /* */ }
        )
    }
}