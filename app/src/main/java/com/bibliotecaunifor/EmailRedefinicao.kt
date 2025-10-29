package com.bibliotecaunifor

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BibliotecaUniforTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // 🚨 No Preview/Activity de teste, precisamos passar uma função vazia
                    EmailRedefinicaoScreen(
                        onNavigateBackToLogin = {},
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
// 🚨 MUDANÇA 1: Adicionar o parâmetro de navegação
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
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                // Conteúdo da Top Bar (Seta e Logo)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        // Note que você está usando uma Row branca separada
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        // 🚨 MUDANÇA 2: Ligar a seta ao evento de navegação
                        onClick = onNavigateBackToLogin,
                        modifier = Modifier
                            .size(60.dp)
                            .pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.67f))
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                // Imagem do Banner
                Image(
                    painter = painterResource(id = R.drawable.noca_imagem_fundo),
                    contentDescription = "Nova imagem de fundo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
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

        Spacer(modifier = Modifier.height(100.dp))
        Button(
            // 🚨 MUDANÇA 3: Ligar o botão "Voltar" ao evento de navegação
            onClick = onNavigateBackToLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(57.dp)
                .padding(bottom = 12.dp, start = 20.dp, end = 20.dp)
                .pointerHoverIcon(PointerIcon.Hand),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Voltar", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EmailRedefinicaoScreenPreview() {
    BibliotecaUniforTheme {
        // 🚨 No Preview, passamos uma função vazia {}
        EmailRedefinicaoScreen(onNavigateBackToLogin = {})
    }
}