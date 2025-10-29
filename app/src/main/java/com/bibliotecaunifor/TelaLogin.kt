package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

@Composable
fun TelaLogin(
    onNavigateUp: () -> Unit,
    onCadastroClick: () -> Unit,
    onEsqueceuSenhaClick: () -> Unit
) {
    val azulUnifor = Color(0xFF084cf4)
    val cinzaCampo = Color(0xFFD0D0D0)
    val cinzaBotao = Color(0xFF3A3A3A)
    val branco = Color(0xFFFFFFFF)

    var matricula by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Column(
        // 🚀 Alterado para fundo branco e adicionado scroll
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 🚀 --- 1. CABEÇALHO PADRÃO (Top Bar Branca + Banner) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            // Imagem do Banner (Cobre toda a área de 180dp)
            Image(
                painter = painterResource(id = R.drawable.livros),
                contentDescription = "Nova imagem de fundo",
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
                    // 🚨 Liga o botão de voltar ao callback onNavigateUp
                    onClick = onNavigateUp,
                    modifier = Modifier.size(60.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.Black
                    )
                }
                // Spacers para centralizar a logo (Padrão 0.67f e 1f)
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
                        color = branco,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 22.sp
                    )
                }
            }
        }
        // --- FIM CABEÇALHO PADRÃO ---

        // 🚨 O seu layout de login era feito com padding no Column principal.
        // Agora, o formulário deve ter seu próprio padding para não cobrir o cabeçalho.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                // Padding lateral e superior para separar do cabeçalho
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {

            // 🚨 Texto de boas vindas e Mascote removidos pois o cabeçalho já cumpre essa função visual

            Spacer(modifier = Modifier.height(18.dp)) // Ajuste o espaçamento após o cabeçalho

            Text(text = "Matrícula", color = Color.Black, fontSize = 13.sp, fontWeight = FontWeight.Bold) // Cor do texto ajustada para preto
            CampoCinza(
                value = matricula,
                onValueChange = { matricula = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                background = cinzaCampo
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "Senha", color = Color.Black, fontSize = 13.sp, fontWeight = FontWeight.Bold) // Cor do texto ajustada para preto
            CampoCinza(
                value = senha,
                onValueChange = { senha = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                background = cinzaCampo,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Botão Entrar
            Button(
                onClick = { /* TODO: autenticar */ },
                colors = ButtonDefaults.buttonColors(containerColor = cinzaBotao),
                shape = RoundedCornerShape(2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
            ) {
                Text(
                    text = "Entrar",
                    color = branco,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Texto "Esqueceu a Senha?"
            Text(
                text = "Esqueceu a Senha?",
                color = Color.Black, // Cor ajustada para preto
                fontSize = 12.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEsqueceuSenhaClick() }
            )

            Spacer(modifier = Modifier.height(8.dp)) // Espaçamento entre os links

            // Texto "Não tem Cadastro? Cadastre-se"
            Text(
                text = "Não tem Cadastro? Cadastre-se",
                color = Color.Black, // Cor ajustada para preto
                fontSize = 12.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCadastroClick() }
            )
            Spacer(modifier = Modifier.height(20.dp)) // Padding inferior
        }
    }
}


@Composable
private fun CampoCinza(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    background: Color,
    isPassword: Boolean = false
) {
    Box(
        modifier = modifier
            .background(background, RoundedCornerShape(2.dp))
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TelaLoginPreview() {
    BibliotecaUniforTheme {
        TelaLogin(onNavigateUp = {}, onCadastroClick = {}, onEsqueceuSenhaClick = {})
    }
}