package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import java.net.URLDecoder // Import necessário para decodificar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaComunicados(
    navController: NavController,
    tituloNotificacao: String?,
    mensagemCorpo: String?
) {
    // 1. Decodificar o título e a mensagem
    val tituloDecodificado = tituloNotificacao?.let { URLDecoder.decode(it, "UTF-8") }
    val mensagemDecodificada = mensagemCorpo?.let { URLDecoder.decode(it, "UTF-8") }

    val tituloExibido = tituloDecodificado ?: "Comunicado Desconhecido"
    val mensagemExibida = mensagemDecodificada ?: "Não foi possível carregar o conteúdo completo."

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Comunicado") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo UNIFOR",
                modifier = Modifier.size(50.dp).padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = tituloExibido,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Divider(color = Color.LightGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = mensagemExibida,
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F4F78))
            ) {
                Text("VOLTAR", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaComunicadosPreview() {
    val navController = rememberNavController()
    BibliotecaUniforTheme {
        // Passando uma mensagem de exemplo sem "+" para o Preview (em tempo de execução o URLDecoder vai corrigir)
        TelaComunicados(
            navController = navController,
            tituloNotificacao = "Aviso da Biblioteca",
            mensagemCorpo = "A renovação de livros por e-mail foi descontinuada. Use o botão 'RENOVAR' na tela de perfil para gerenciar seus empréstimos. Prazo máximo estendido por 72h para transição."
        )
    }
}