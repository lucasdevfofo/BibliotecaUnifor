package com.bibliotecaunifor.Adm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import com.bibliotecaunifor.model.UsuarioModel
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import com.bibliotecaunifor.viewmodel.UsuarioAdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAdminEditarUsuario(
    uid: String,
    onVoltarClick: () -> Unit,
    onEdicaoSucesso: () -> Unit
) {
    val viewModel: UsuarioAdminViewModel = viewModel()
    val usuarios by viewModel.usuarios.collectAsState()
    val mensagem by viewModel.mensagem.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val azulPrimario = Color(0xFF3F4F78)

    // Busca o usuário na lista pelo UID
    val usuarioPair = usuarios.firstOrNull { it.first == uid }
    val usuarioData = usuarioPair?.second ?: UsuarioModel(
        nomeCompleto = "Usuário não encontrado",
        matricula = "",
        email = "",
        telefone = "",
        cpf = "",
        curso = "",
        tipo = "usuario"
    )

    var nome by remember { mutableStateOf(usuarioData.nomeCompleto) }
    var matricula by remember { mutableStateOf(usuarioData.matricula) }
    var email by remember { mutableStateOf(usuarioData.email) }
    var telefone by remember { mutableStateOf(usuarioData.telefone) }
    var cpf by remember { mutableStateOf(usuarioData.cpf) }
    var curso by remember { mutableStateOf(usuarioData.curso) }

    // Atualiza os campos quando o usuárioData muda
    LaunchedEffect(usuarioData) {
        nome = usuarioData.nomeCompleto
        matricula = usuarioData.matricula
        email = usuarioData.email
        telefone = usuarioData.telefone
        cpf = usuarioData.cpf
        curso = usuarioData.curso
    }

    LaunchedEffect(mensagem) {
        mensagem?.let {
            if (it.contains("sucesso")) {
                onEdicaoSucesso()
            }
        }
    }

    Scaffold(
        topBar = {
            AdminTopBarUsuarios(
                onVoltarClick = onVoltarClick,
                onNotificacoesClick = { },
                onMenuClick = { }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF3F3F3))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Editar Usuário",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .align(Alignment.Start)
            )

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color(0xFFE0E0E0), RoundedCornerShape(32.dp))
                    .padding(bottom = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = "Perfil", modifier = Modifier.size(48.dp), tint = Color.Gray)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome do Usuário") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().height(55.dp).padding(bottom = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray, focusedBorderColor = azulPrimario)
            )

            OutlinedTextField(
                value = matricula,
                onValueChange = { matricula = it },
                label = { Text("Matrícula") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().height(55.dp).padding(bottom = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray, focusedBorderColor = azulPrimario)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().height(55.dp).padding(bottom = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray, focusedBorderColor = azulPrimario)
            )

            OutlinedTextField(
                value = telefone,
                onValueChange = { telefone = it },
                label = { Text("Telefone") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().height(55.dp).padding(bottom = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray, focusedBorderColor = azulPrimario)
            )

            OutlinedTextField(
                value = cpf,
                onValueChange = { cpf = it },
                label = { Text("CPF") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().height(55.dp).padding(bottom = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray, focusedBorderColor = azulPrimario)
            )

            OutlinedTextField(
                value = curso,
                onValueChange = { curso = it },
                label = { Text("Curso") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().height(55.dp).padding(bottom = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray, focusedBorderColor = azulPrimario)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val usuarioAtualizado = UsuarioModel(
                        nomeCompleto = nome,
                        matricula = matricula,
                        email = email,
                        telefone = telefone,
                        cpf = cpf,
                        curso = curso,
                        tipo = usuarioData.tipo
                    )
                    viewModel.editarUsuario(uid, usuarioAtualizado)
                },
                colors = ButtonDefaults.buttonColors(containerColor = azulPrimario),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                } else {
                    Text(
                        "CONFIRMAR",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaAdminEditarUsuarioPreview() {
    BibliotecaUniforTheme {
        TelaAdminEditarUsuario(
            uid = "123",
            onVoltarClick = {},
            onEdicaoSucesso = {}
        )
    }
}