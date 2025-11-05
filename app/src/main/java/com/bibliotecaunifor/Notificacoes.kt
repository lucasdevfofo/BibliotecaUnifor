package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.net.URLEncoder

data class Notificacao(val titulo: String, val mensagem: String, val isAdmin: Boolean = false)

@Composable
fun NotificacaoItem(notificacao: Notificacao, navController: NavController) {
    val corFundo = if (notificacao.isAdmin) Color(0xFFFFF3E0) else Color(0xFFF5F5F5)
    val corBorda = if (notificacao.isAdmin) Color(0xFFFF9800) else Color.Black.copy(alpha = 0.2f)
    val corTitulo = if (notificacao.isAdmin) Color(0xFFE65100) else Color.Black

    Column(
        modifier = Modifier
            .border(1.dp, corBorda, RoundedCornerShape(8.dp))
            .background(corFundo, RoundedCornerShape(8.dp))
            .padding(12.dp)
            .fillMaxWidth()
            .clickable {
                val tituloEncoded = URLEncoder.encode(notificacao.titulo, "UTF-8")
                val mensagemEncoded = URLEncoder.encode(notificacao.mensagem, "UTF-8")
                navController.navigate("comunicados/$tituloEncoded/$mensagemEncoded")
            }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (!notificacao.isAdmin) {
                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "Foto do usuário",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Admin",
                    tint = corTitulo,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp)
                )
            }
            Text(
                text = if (notificacao.isAdmin) "ADMINISTRADOR (Aviso)" else notificacao.titulo,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = corTitulo
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = notificacao.mensagem,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun TelaNotificacoes(navController: NavController) {
    val notificacoesOriginais = listOf(
        Pair("Rodrigo Silva", "Mensagem de notificação 1 — texto longo para testar rolagem."),
        Pair("Thiago Narak", "Mensagem de notificação 2 — texto ainda mais longo para testar overflow."),
        Pair("Matheus Linhares", "Mensagem de notificação 3 — Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
        Pair("Cinthia Cruz", "Mensagem de notificação 4 — Phasellus convallis justo in augue tincidunt tempor."),
        Pair("Carlos Eduardo", "Mensagem de notificação 4 — Phasellus convallis justo in augue tincidunt tempor."),
        Pair("Americo", "Mensagem de notificação 4 — Phasellus convallis justo in augue tincidunt tempor."),
        Pair("Isaura", "Mensagem de notificação 5 — Fusce mattis nunc a dui feugiat, at sagittis erat pretium.")
    )

    val notificacoesLista = remember {
        val adminMessage = Notificacao(
            titulo = "Aviso da Biblioteca",
            mensagem = "A renovação de livros por e-mail foi descontinuada. Use o botão 'RENOVAR' na tela de perfil para gerenciar seus empréstimos. Prazo máximo estendido por 72h para transição.",
            isAdmin = true
        )

        val userMessages = notificacoesOriginais.map { Notificacao(it.first, it.second) }
        listOf(adminMessage) + userMessages
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .size(60.dp)
                        .pointerHoverIcon(PointerIcon.Hand)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.weight(0.67f))

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo UNIFOR",
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Text(
            text = "Notificações",
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(notificacoesLista) { notificacao ->
                NotificacaoItem(notificacao = notificacao, navController = navController)
            }
        }
    }
}