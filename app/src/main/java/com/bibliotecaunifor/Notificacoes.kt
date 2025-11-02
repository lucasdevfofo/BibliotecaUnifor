package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

@Composable
fun TelaNotificacoes(navController: NavController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 🔹 TopBar
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

        // 🔹 Título — com menos espaço entre logo e texto
        Text(
            text = "Notificações",
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
        )

        // 🔹 Exemplo de lista de notificações
        val notificacoes = listOf(
            Pair("Usuário 1", "Mensagem de notificação 1 — texto longo para testar rolagem."),
            Pair("Usuário 2", "Mensagem de notificação 2 — texto ainda mais longo para testar overflow."),
            Pair("Usuário 3", "Mensagem de notificação 3 — Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
            Pair("Usuário 4", "Mensagem de notificação 4 — Phasellus convallis justo in augue tincidunt tempor."),
            Pair("Usuário 4", "Mensagem de notificação 4 — Phasellus convallis justo in augue tincidunt tempor."),
            Pair("Usuário 4", "Mensagem de notificação 4 — Phasellus convallis justo in augue tincidunt tempor."),
            Pair("Usuário 5", "Mensagem de notificação 5 — Fusce mattis nunc a dui feugiat, at sagittis erat pretium.")
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            notificacoes.forEach { (nome, mensagem) ->
                Column(
                    modifier = Modifier
                        .border(1.dp, Color.Black.copy(alpha = 0.2f))
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_user),
                            contentDescription = "Foto do usuário",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = nome,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = mensagem,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
