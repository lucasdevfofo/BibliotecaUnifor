package com.bibliotecaunifor.Adm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import com.bibliotecaunifor.R



// O @Composable TelaCadastroAdm e CampoCinzaAdm permanecem no mesmo arquivo
@Composable
fun TelaCadastroAdm(
    onNavigateUp: () -> Unit,
    onCadastrarClick: () -> Unit
) {
    val azulBotao = Color(0xFF3F4F78)

    var nome by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.livros),
                contentDescription = "Nova imagem de fundo",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            // Bar for back button and logo
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White)
                    .align(Alignment.TopCenter),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateUp,
                    modifier = Modifier.size(60.dp).pointerHoverIcon(PointerIcon.Hand)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.weight(1f)) // Espaço para centralizar

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.weight(1f)) // Espaço para centralizar

                // Filler para o lado direito (simulando icons laterais)
                Spacer(modifier = Modifier.width(60.dp))
            }

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
                        text = "Reserve sua sala",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Biblioteca Unifor",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                text = "CADASTRO ADMIN",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Nome Completo", fontSize = 12.sp, color = Color.Gray)
            CampoCinzaAdm("Nome Completo", nome) { nome = it }

            Text(text = "Senha", fontSize = 12.sp, color = Color.Gray)
            CampoCinzaAdm("Senha", senha) { senha = it }

            Text(text = "Matrícula", fontSize = 12.sp, color = Color.Gray)
            CampoCinzaAdm("Matrícula", matricula) { matricula = it }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column(Modifier.weight(1f)) {
                    Text(text = "CPF", fontSize = 12.sp, color = Color.Gray)
                    CampoCinzaAdm("CPF", cpf, Modifier.fillMaxWidth()) { cpf = it }
                }
                Column(Modifier.weight(1f)) {
                    Text(text = "Telefone", fontSize = 12.sp, color = Color.Gray)
                    CampoCinzaAdm("Telefone", telefone, Modifier.fillMaxWidth()) { telefone = it }
                }
            }

            Text(text = "E-mail", fontSize = 12.sp, color = Color.Gray)
            CampoCinzaAdm("E-mail", email) { email = it }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onCadastrarClick,
                colors = ButtonDefaults.buttonColors(containerColor = azulBotao),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .pointerHoverIcon(PointerIcon.Hand),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = "Cadastrar",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun CampoCinzaAdm(
    placeholder: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    val cinzaCampo = Color(0xFFF2F2F2)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(cinzaCampo, RoundedCornerShape(6.dp))
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
                innerTextField()
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp)
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun TelaCadastroAdmPreview() {
    BibliotecaUniforTheme {
        TelaCadastroAdm(onNavigateUp = {}, onCadastrarClick = {})
    }
}