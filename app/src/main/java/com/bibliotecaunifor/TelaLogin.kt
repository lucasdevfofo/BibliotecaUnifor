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
import androidx.compose.ui.graphics.SolidColor
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
    onEsqueceuSenhaClick: () -> Unit,
    onEntrarClick: (isAdmin: Boolean) -> Unit // ✅ Agora envia se é admin ou não
) {
    val azulUnifor = Color(0xFF004AF5)
    val cinzaCampo = Color(0xFFD0D0D0)
    val cinzaBotao = Color(0xFF3A3A3A)
    val branco = Color.White

    var matricula by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var isAdmin by remember { mutableStateOf(false) } // ✅ Novo estado de tipo de login

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(azulUnifor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🔹 Logo centralizada e grande
            Image(
                painter = painterResource(id = R.drawable.logo_tela_inicial_e_cadastro),
                contentDescription = "Logo Unifor",
                modifier = Modifier
                    .width(260.dp)
                    .padding(top = 40.dp, bottom = 16.dp),
                contentScale = ContentScale.FillWidth
            )

            // 🔹 Texto centralizado
            Text(
                text = "Bem vindo ao aplicativo da\nBiblioteca da UNIFOR",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = branco,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )

            // 🔹 Mascote centralizado
            Image(
                painter = painterResource(id = R.drawable.mascote_mesa),
                contentDescription = "Mascote Unifor",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 16.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(30.dp))

            // 🔹 Campos de login
            Text("Matrícula", fontSize = 16.sp, color = branco, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))
            CampoCinza(
                value = matricula,
                onValueChange = { matricula = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                background = cinzaCampo
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Senha", fontSize = 16.sp, color = branco, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))
            CampoCinza(
                value = senha,
                onValueChange = { senha = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                background = cinzaCampo,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 🔹 Seletor de tipo de login (Usuário / Admin)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                @Composable
                fun tipoBotao(text: String, selecionado: Boolean, aoClicar: () -> Unit) {
                    OutlinedButton(
                        onClick = aoClicar,
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (selecionado) Color.White else Color.Transparent,
                            contentColor = if (selecionado) Color(0xFF004AF5) else Color.White
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            width = 1.dp,
                            brush = SolidColor(Color.White)
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                    ) {
                        Text(text, fontWeight = FontWeight.Bold)
                    }
                }

                tipoBotao("Usuário", selecionado = !isAdmin) { isAdmin = false }
                tipoBotao("Admin", selecionado = isAdmin) { isAdmin = true }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Botão Entrar
            Button(
                onClick = { onEntrarClick(isAdmin) }, // ✅ Envia o tipo de login
                colors = ButtonDefaults.buttonColors(containerColor = cinzaBotao),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text("Entrar", color = branco, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.weight(1f))

            // 🔹 Links inferiores
            Text(
                text = "Esqueceu a Senha?",
                color = branco,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEsqueceuSenhaClick() }
                    .padding(bottom = 8.dp)
            )

            Text(
                text = "Não tem Cadastro? Cadastre-se",
                color = branco,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCadastroClick() }
                    .padding(bottom = 24.dp)
            )
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
            .background(background, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
