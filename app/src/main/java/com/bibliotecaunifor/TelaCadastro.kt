package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme


@Composable
fun TelaCadastro(
    onNavigateUp: () -> Unit,
    onCadastrarClick: () -> Unit
) {
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
            .verticalScroll(rememberScrollState())
    ) {

        // --- 1. CABEÇALHO IDÊNTICO AO EMAILREDEFINICAO (Top Bar Branca + Banner) ---
        // Usamos um Box para alinhar o Banner e os Textos DENTRO da área total de 180dp
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            // Imagem do Banner (Cobre toda a área de 180dp)
            Image(
                painter = painterResource(id = R.drawable.livros),
                contentDescription = "Nova imagem de fundo",
                modifier = Modifier.matchParentSize(), // Ocupa os 180dp completos
                contentScale = ContentScale.Crop
            )

            // 🚀 TOP BAR BRANCA FLUTUANTE (Camada superior para seta e logo)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White) // Fundo branco da Top Bar
                    .align(Alignment.TopCenter), // Alinhada no topo do Box
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateUp,
                    modifier = Modifier.size(60.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
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

            // 🚀 TEXTOS (Centralizados na área visível da imagem)
            // Usamos um Box aninhado para delimitar a área onde o texto deve centralizar:
            // Começa em 56dp (fim da Top Bar) e tem 124dp de altura (180 - 56)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(124.dp) // Altura da área visível da imagem
                    .align(Alignment.BottomCenter), // Alinhado abaixo da Top Bar (56dp + 124dp = 180dp)
                contentAlignment = Alignment.Center // Centraliza o texto nesta nova área
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
        // --- FIM CABEÇALHO IDÊNTICO ---


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
            // ... (restante do formulário) ...

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
                onClick = onCadastrarClick,
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
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// ... (Restante do Composable CampoCinza e Preview permanece o mesmo) ...

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
        TelaCadastro(onNavigateUp = {}, onCadastrarClick = {})
    }
}