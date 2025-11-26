package com.bibliotecaunifor

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import com.bibliotecaunifor.repository.AuthRepository
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import com.bibliotecaunifor.viewmodel.EsqueceuSenhaViewModel
import com.bibliotecaunifor.viewmodel.EsqueceuSenhaUiState

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
fun EsqueceuSenhaScreen(
    viewModel: EsqueceuSenhaViewModel,
    onNavigateUp: () -> Unit,
    onEmailEnviado: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        when (uiState) {
            is EsqueceuSenhaUiState.Success -> {
                Toast.makeText(context, "Link de recuperação enviado para $email!", Toast.LENGTH_LONG).show()
                onEmailEnviado()
            }
            is EsqueceuSenhaUiState.Error -> {
                val errorState = uiState as EsqueceuSenhaUiState.Error
                Toast.makeText(context, "Erro: ${errorState.message}", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White),
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
                Spacer(modifier = Modifier.weight(0.67f))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            Image(
                painter = painterResource(id = R.drawable.livros),
                contentDescription = "Nova imagem de fundo",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(180.dp - 56.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Reserve sua sala",
                    color = Color.White,
                    fontSize = 18.sp
                )
                Text(
                    text = "Biblioteca Unifor",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

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
                        viewModel.enviarEmailRedefinicao(email)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044EE7)),
                shape = RoundedCornerShape(4.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
                enabled = uiState !is EsqueceuSenhaUiState.Loading
            ) {
                if (uiState is EsqueceuSenhaUiState.Loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text("Enviar", fontSize = 18.sp)
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetState()
        }
    }
}

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
        EsqueceuSenhaScreen(
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
            onNavigateUp = {},
            onEmailEnviado = {}
        )
    }
}