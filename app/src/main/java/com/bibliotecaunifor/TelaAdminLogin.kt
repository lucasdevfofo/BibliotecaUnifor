package com.bibliotecaunifor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

class TelaAdminLogin : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BibliotecaUniforTheme {
                TelaAdminLoginContent(
                    onLoginClicked = { },
                    onCadastroClicked = { }
                )
            }
        }
    }
}

@Composable
fun TelaAdminLoginContent(
    onLoginClicked: () -> Unit,
    onCadastroClicked: () -> Unit
) {
    val corFundoAzul = Color(0xFF004AF5)
    val corBotaoCinza = Color(0xFFBDBDBD)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(corFundoAzul)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_tela_inicial_e_cadastro),
                contentDescription = "Logo Unifor Mobile",
                modifier = Modifier
                    .offset(x = (-16).dp)
                    .align(Alignment.Start)
                    .width(200.dp)
                    .padding(top = 40.dp, bottom = 16.dp),
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = "Bem vindo ao aplicativo da\nBiblioteca da UNIFOR",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Start,
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

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onLoginClicked,
                colors = ButtonDefaults.buttonColors(containerColor = corBotaoCinza),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text(text = "Login", color = Color.Black, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onCadastroClicked,
                colors = ButtonDefaults.buttonColors(containerColor = corBotaoCinza),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text(text = "Cadastre-se", color = Color.Black, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaAdminLoginPreview() {
    BibliotecaUniforTheme {
        TelaAdminLoginContent({}, {})
    }
}