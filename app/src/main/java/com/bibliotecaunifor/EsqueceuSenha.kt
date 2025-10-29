package com.bibliotecaunifor

import android.widget.Toast
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

// 1. Função de validação (mantida)
fun validateEmail(email: String): String? {
    if (email.isBlank()) {
        return "Por favor, preencha seu e-mail institucional."
    }
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return "Formato de e-mail inválido."
    }
    return null
}



@Composable
@OptIn(ExperimentalMaterial3Api::class)
// O Composable agora é o ponto de entrada da tela
fun EsqueceuSenhaScreen(onNavigateUp: () -> Unit, onEnviarClick: (String) -> Unit) {

    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    // Não usamos mais Scaffold aqui, pois o cabeçalho não é um TopAppBar padrão do Material
    Column(
        modifier = Modifier
            .fillMaxSize()
            // 🚨 Use a cor de fundo que estava no seu Scaffold anterior se necessário
            .background(Color.White)
    ) {

        // 🚀 BLOCO DE CABEÇALHO PADRÃO (Da EmailRedefinicaoScreen)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            // Row Branca (Top Bar com Seta e Logo)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    // 🚨 LIGADO À NAVEGAÇÃO
                    onClick = onNavigateUp,
                    modifier = Modifier.size(60.dp)
                    // .pointerHoverIcon(PointerIcon.Hand) - Removido pois não está nas importações padrão
                ) {
                    Icon(
                        // Usando Icons.Default.ArrowBack para ser igual à EmailRedefinicao
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

            // Imagem do Banner (logo abaixo da Row Branca)
            Image(
                // 🚨 Usando o recurso de imagem da biblioteca (livros)
                painter = painterResource(id = R.drawable.livros),
                contentDescription = "Nova imagem de fundo",
                modifier = Modifier
                    .fillMaxWidth()
                    // Alinha logo abaixo da Row branca de 56dp
                    .align(Alignment.BottomCenter)
                    .height(180.dp - 56.dp), // 180dp (total) - 56dp (Row) = 124dp de altura para a imagem
                contentScale = ContentScale.Crop
            )

            // Textos da Biblioteca (Adicionados para completar o padrão)
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 28.dp), // Empurra o texto para baixo da Row branca
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Reserve sua sala",
                    color = Color.White,
                    fontSize = 18.sp,
                )
                Text(
                    text = "Biblioteca Unifor",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        // 🚀 FIM DO BLOCO DE CABEÇALHO PADRÃO


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {

            Text(
                text = "Esqueceu a senha? Digite seu email institucional para recuperar",
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )


            EmailRecoveryTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = null
                },
                isError = emailError != null,
                errorMessage = emailError
            )


            Button(
                onClick = {
                    val validationError = validateEmail(email)
                    emailError = validationError

                    if (validationError == null) {
                        onEnviarClick(email)
                        Toast.makeText(context, "Link de recuperação enviado para $email!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Por favor, corrija o email.", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044EE7)), // Usando a cor 044EE7
                shape = RoundedCornerShape(4.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text("Enviar", fontSize = 18.sp)
            }
        }
    }
}


// Componentes que não são mais necessários (pois o design foi incorporado)
/*
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EsqueceuSenhaTopBar(onNavigateUp: () -> Unit) { ... }

@Composable
fun EsqueceuSenhaHeader() { ... }
*/


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EmailRecoveryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Send
            ),
            shape = RoundedCornerShape(4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.LightGray,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            singleLine = true
        )


        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewEsqueceuSenhaScreen() {
    BibliotecaUniforTheme {
        EsqueceuSenhaScreen(onNavigateUp = {}, onEnviarClick = {})
    }
}