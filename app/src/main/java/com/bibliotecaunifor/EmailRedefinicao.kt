package com.bibliotecaunifor


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
// Removidos imports de KeyboardOptions, KeyboardType, ImeAction que não eram usados
import androidx.compose.material.icons.Icons
// 🚨 CORREÇÃO: Usamos Icons.AutoMirrored para a seta de voltar
import androidx.compose.material.icons.automirrored.filled.ArrowBack
// import androidx.compose.material.icons.filled.ArrowBack // Removido, pois é obsoleto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

// Removido a MainActivity, pois ela não era o foco do erro e estava incompleta.

val azulUniforBotao = Color(0xFF044EE7)

@Composable
fun EmailRedefinicaoScreen(
    onNavigateBackToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // --- 1. CABEÇALHO PADRÃO (Top Bar Branca + Banner) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            // Imagem do Banner (Cobre toda a área de 180dp)
            Image(
                painter = painterResource(id = R.drawable.livros),
                contentDescription = "Imagem de Fundo da Biblioteca",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            // TOP BAR BRANCA FLUTUANTE (Camada superior para seta e logo)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White)
                    .align(Alignment.TopCenter),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateBackToLogin,
                    modifier = Modifier
                        .size(60.dp)
                        .pointerHoverIcon(PointerIcon.Hand)
                ) {
                    Icon(
                        // 🚨 CORREÇÃO: Usando a versão AutoMirrored
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.Black
                    )
                }
                // Spacers para centralizar a logo
                Spacer(modifier = Modifier.weight(0.67f))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            // TEXTOS (Centralizados na área visível da imagem - 124dp de altura)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(124.dp)
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Reserve sua sala\nBiblioteca Unifor",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 22.sp
                    )
                }
            }
        }
        // --- FIM CABEÇALHO PADRÃO ---


        // Conteúdo Principal da Tela
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Imagem principal (Mascote)
            Image(
                painter = painterResource(id = R.drawable.imagem_principal),
                contentDescription = "Imagem principal"
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Email de Redefinição enviado!",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "verifique seu email para fazer login",
                fontSize = 17.sp
            )
        }

        // Botão "Voltar"
        Button(
            onClick = onNavigateBackToLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(57.dp)
                .padding(bottom = 12.dp, start = 20.dp, end = 20.dp)
                .pointerHoverIcon(PointerIcon.Hand),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = azulUniforBotao)
        ) {
            Text(text = "Voltar", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EmailRedefinicaoScreenPreview() {
    BibliotecaUniforTheme {
        EmailRedefinicaoScreen(onNavigateBackToLogin = {})
    }
}