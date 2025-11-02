package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

val azulUiforBotao = Color(0xFF044EE7)
val azulBebeBackground = Color(0xFFB3D9FF)
val cinzaBordaHeader = Color(0xFFCCCCCC).copy(alpha = 0.5f)

@Composable
fun NotificaoesMensagemsScreen(
    onNavigateBackToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .border(width = 1.dp, color = cinzaBordaHeader)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .align(Alignment.Center)
                    .padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateBackToLogin,
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
                Spacer(modifier = Modifier.weight(0.5f))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.weight(0.8f))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Notificações",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "Administrador responsável",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(250.dp)
                    .background(azulBebeBackground, RoundedCornerShape(12.dp))
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Comunicado: Todos os alunos devem atualizar seus dados cadastrais até sexta-feira. Verifique as instruções no portal do aluno.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }

        Button(
            onClick = onNavigateBackToLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(57.dp)
                .padding(bottom = 12.dp, start = 20.dp, end = 20.dp)
                .pointerHoverIcon(PointerIcon.Hand),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = azulUiforBotao)
        ) {
            Text(text = "Voltar", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificacoesMensagmsPreview() {
    BibliotecaUniforTheme {
        NotificaoesMensagemsScreen(onNavigateBackToLogin = {})
    }
}
