package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme


@Composable
fun TelaCadastro(navController: NavController) {
    val cinzaCampo = Color(0xFFF2F2F2)
    val azulBotao = Color(0xFF2E4C93)

    var nome by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var curso by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp)
                .size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.Black
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.livros),
                contentDescription = "Imagem da biblioteca",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo2),
                    contentDescription = "Logo Biblioteca Unifor",
                    modifier = Modifier
                        .height(54.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Reserve sua sala\nBiblioteca Unifor",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 22.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Spacer(modifier = Modifier.height(14.dp))

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                text = "CADASTRO",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Nome Completo", fontSize = 12.sp, color = Color.Gray)
            CampoCinza("Nome Completo", nome) { nome = it }

            Text(text = "Senha", fontSize = 12.sp, color = Color.Gray)
            CampoCinza("Senha", senha) { senha = it }

            Text(text = "Matrícula", fontSize = 12.sp, color = Color.Gray)
            CampoCinza("Matrícula", matricula) { matricula = it }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column(Modifier.weight(1f)) {
                    Text(text = "Curso", fontSize = 12.sp, color = Color.Gray)
                    CampoCinza("Curso", curso, Modifier.fillMaxWidth()) { curso = it }
                }
                Column(Modifier.weight(1f)) {
                    Text(text = "CPF", fontSize = 12.sp, color = Color.Gray)
                    CampoCinza("CPF", cpf, Modifier.fillMaxWidth()) { cpf = it }
                }
            }

            Text(text = "Telefone", fontSize = 12.sp, color = Color.Gray)
            CampoCinza("Telefone", telefone) { telefone = it }

            Text(text = "E-mail", fontSize = 12.sp, color = Color.Gray)
            CampoCinza("E-mail", email) { email = it }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { /* TODO: cadastrar */ },
                colors = ButtonDefaults.buttonColors(containerColor = azulBotao),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = "Cadastrar",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
fun CampoCinza(
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
fun TelaCadastroPreview() {
    BibliotecaUniforTheme {
        val mockNavController = rememberNavController()
        TelaCadastro(navController = mockNavController)
    }
}
