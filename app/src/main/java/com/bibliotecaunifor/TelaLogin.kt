package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TelaLogin(
    onNavigateUp: () -> Unit,
    onCadastroClick: () -> Unit,
    onEsqueceuSenhaClick: () -> Unit
) {
    // O corpo da função começa aqui. A assinatura anterior estava duplicada e incompleta.

    val azulUnifor = Color(0xFF084cf4)
    val cinzaCampo = Color(0xFFD0D0D0)   // blocos dos inputs
    val cinzaBotao = Color(0xFF3A3A3A)   // botão Entrar
    val branco = Color(0xFFFFFFFF)

    var matricula by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(azulUnifor)
            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 12.dp),
        horizontalAlignment = Alignment.Start
    ) {



        Spacer(modifier = Modifier.height(12.dp))


        Text(
            text = "Bem vindo ao aplicativo da\nBiblioteca da UNIFOR",
            color = branco,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Mascote
        Image(
            painter = painterResource(id = R.drawable.mascote_mesa),
            contentDescription = "Mascote",
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(18.dp))


        Text(text = "Matrícula", color = branco, fontSize = 13.sp)
        CampoCinza(
            value = matricula,
            onValueChange = { matricula = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp),
            background = cinzaCampo
        )

        Spacer(modifier = Modifier.height(12.dp))


        Text(text = "Senha", color = branco, fontSize = 13.sp)
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
            color = branco,
            fontSize = 12.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onEsqueceuSenhaClick() } // Chama o callback do NavHost
        )

        // Texto "Não tem Cadastro? Cadastre-se"
        Text(
            text = "Não tem Cadastro? Cadastre-se",
            color = branco,
            fontSize = 12.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCadastroClick() } // Chama o callback do NavHost
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