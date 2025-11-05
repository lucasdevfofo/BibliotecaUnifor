package com.bibliotecaunifor.Adm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bibliotecaunifor.R
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

class TelaLoginAdm : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BibliotecaUniforTheme {
                TelaLoginAdmContent(
                    onLoginClicked = {},
                    onEsqueceuSenhaClicked = {
                        startActivity(Intent(this, TelaEsqueceuSenha::class.java))
                    },
                    onCadastroClicked = {}
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaLoginAdmContent(
    onLoginClicked: () -> Unit,
    onEsqueceuSenhaClicked: () -> Unit,
    onCadastroClicked: () -> Unit
) {
    val corFundoAzul = Color(0xFF004AF5)
    val corCampoCinza = Color(0xFFBDBDBD)
    val corBotaoEntrar = Color(0xFF424242)

    var matricula by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(corFundoAzul)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_tela_inicial_e_cadastro),
                contentDescription = "Logo Unifor Mobile",
                modifier = Modifier
                    .width(260.dp)
                    .padding(top = 40.dp, bottom = 16.dp),
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = "Bem vindo ao aplicativo da\nBiblioteca da UNIFOR",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.mascote_mesa),
                contentDescription = "Mascote Unifor",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text("Matrícula", fontSize = 16.sp, color = Color.White, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = matricula,
                onValueChange = { matricula = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = corCampoCinza,
                    focusedContainerColor = corCampoCinza,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Senha", fontSize = 16.sp, color = Color.White, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = senha,
                onValueChange = { senha = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = corCampoCinza,
                    focusedContainerColor = corCampoCinza,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onLoginClicked,
                colors = ButtonDefaults.buttonColors(containerColor = corBotaoEntrar),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text("Entrar", color = Color.White, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            ClickableText(
                text = AnnotatedString("Esqueceu a Senha?"),
                onClick = { onEsqueceuSenhaClicked() },
                style = TextStyle(color = Color.White, fontSize = 14.sp, textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            ClickableText(
                text = AnnotatedString("Não tem Cadastro? Cadastre-se"),
                onClick = { onCadastroClicked() },
                style = TextStyle(color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaLoginAdmPreview() {
    BibliotecaUniforTheme {
        TelaLoginAdmContent({}, {}, {})
    }
}