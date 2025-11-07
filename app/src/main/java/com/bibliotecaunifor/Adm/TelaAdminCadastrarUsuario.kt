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
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAdminCadastrarUsuario(
    onVoltarClick: () -> Unit,
    onConfirmarCadastro: (
        novoNome: String,
        novaMatricula: String,
        novoEmail: String,
        novoTelefone: String,
        novoCpf: String,
        novoCurso: String
    ) -> Unit
) {
    val azulPrimario = Color(0xFF3F4F78)
    val cinzaClaroFundo = Color(0xFFF3F3F3)

    var nome by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var curso by remember { mutableStateOf("") }


    Scaffold(
        topBar = {

            AdminTopBarUsuarios(
                onVoltarClick = onVoltarClick,
                onNotificacoesClick = {   },
                onMenuClick = {   }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(cinzaClaroFundo)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cadastrar Usuário",
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
                modifier = Modifier.fillMaxWidth().height(55.dp), // Removido o padding bottom
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray, focusedBorderColor = azulPrimario)
            )
            Spacer(modifier = Modifier.height(10.dp))


            OutlinedTextField(
                value = matricula,
                onValueChange = { matricula = it },
                label = { Text("Matrícula") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().height(55.dp),
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray, focusedBorderColor = azulPrimario)
            )
            Spacer(modifier = Modifier.height(10.dp))


            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().height(55.dp),
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray, focusedBorderColor = azulPrimario)
            )
            Spacer(modifier = Modifier.height(10.dp))


            OutlinedTextField(
                value = telefone,
                onValueChange = { telefone = it },
                label = { Text("Telefone") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().height(55.dp),
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray, focusedBorderColor = azulPrimario)
            )
            Spacer(modifier = Modifier.height(10.dp)) // <-- Espaço de 10.dp


            OutlinedTextField(
                value = cpf,
                onValueChange = { cpf = it },
                label = { Text("CPF") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().height(55.dp),
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray, focusedBorderColor = azulPrimario)
            )
            Spacer(modifier = Modifier.height(10.dp)) // <-- Espaço de 10.dp


            OutlinedTextField(
                value = curso,
                onValueChange = { curso = it },
                label = { Text("Curso") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().height(55.dp),
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray, focusedBorderColor = azulPrimario)
            )


            Spacer(modifier = Modifier.height(24.dp))


            Button(
                onClick = {
                    onConfirmarCadastro(nome, matricula, email, telefone, cpf, curso)
                },
                colors = ButtonDefaults.buttonColors(containerColor = azulPrimario),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
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

@Preview(showBackground = true)
@Composable
fun TelaAdminCadastrarUsuarioPreview() {
    BibliotecaUniforTheme {
        TelaAdminCadastrarUsuario(
            onVoltarClick = {},
            onConfirmarCadastro = { a, b, c, d, e, f -> }
        )
    }
}