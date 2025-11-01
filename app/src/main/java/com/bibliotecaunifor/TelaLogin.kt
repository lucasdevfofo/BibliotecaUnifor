package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

@Composable
fun TelaLogin(
    onNavigateUp: () -> Unit,
    onCadastroClick: () -> Unit,
    onEsqueceuSenhaClick: () -> Unit,
    onEntrarClick: () -> Unit
) {
    val azulUnifor = Color(0xFF004AF5)
    val cinzaCampo = Color(0xFFD0D0D0)
    val cinzaBotao = Color(0xFF3A3A3A)
    val branco = Color.White

    var matricula by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(azulUnifor)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Start // 🔹 alinhamento geral à esquerda
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // 🔹 Logo alinhada à esquerda
        // 🔹 Logo alinhada à esquerda (tamanho ajustado para o mesmo da tela inicial)
        Image(
            painter = painterResource(id = R.drawable.logo_tela_inicial_e_cadastro),
            contentDescription = "Logo Unifor",
            modifier = Modifier
                .height(70.dp)            // ⬅️ aumentei o tamanho da logo (antes 50.dp)
                .width(220.dp)            // ⬅️ largura controlada para manter proporção
                .align(Alignment.Start),  // mantém alinhamento à esquerda
            contentScale = ContentScale.Fit
        )


        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 Texto de boas-vindas alinhado à esquerda
        Text(
            text = "Bem vindo ao aplicativo da\nBiblioteca da UNIFOR",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = branco,
            lineHeight = 28.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Mascote centralizado no meio da tela
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.mascote_mesa),
                contentDescription = "Mascote Unifor",
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth(0.8f),
                contentScale = ContentScale.Fit
            )
        }

        // 🔹 Campos de entrada e botões
        Text(
            text = "Matrícula",
            color = branco,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        CampoCinza(
            value = matricula,
            onValueChange = { matricula = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp),
            background = cinzaCampo
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Senha",
            color = branco,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        CampoCinza(
            value = senha,
            onValueChange = { senha = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp),
            background = cinzaCampo,
            isPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onEntrarClick,
            colors = ButtonDefaults.buttonColors(containerColor = cinzaBotao),
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Entrar",
                color = branco,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Links abaixo do botão
        Text(
            text = "Esqueceu a Senha?",
            color = branco,
            fontSize = 12.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onEsqueceuSenhaClick() }
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Não tem Cadastro? Cadastre-se",
            color = branco,
            fontSize = 12.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCadastroClick() }
        )
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
