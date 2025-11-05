package com.bibliotecaunifor.Adm

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bibliotecaunifor.R
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

class TelaEsqueceuSenha : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BibliotecaUniforTheme {
                EsqueceuSenhaScreen(
                    onNavigateUp = { finish() },
                    onEnviarClick = { email ->
                        Toast.makeText(this, "Link enviado para $email", Toast.LENGTH_LONG).show()
                    }
                )
            }
        }
    }
}

fun validateEmail(email: String): String? {
    if (email.isBlank()) {
        return "Por favor, preencha seu e-mail institucional."
    }
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return "Formato de e-mail inválido."
    }
    return null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EsqueceuSenhaScreen(onNavigateUp: () -> Unit, onEnviarClick: (String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

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
                        tint = Color(0xFF014B82)
                    )
                }
                Spacer(modifier = Modifier.weight(0.67f))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(35.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            Image(
                painter = painterResource(id = R.drawable.livros),
                contentDescription = "Imagem de fundo",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(124.dp),
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
                        onEnviarClick(email)
                    } else {
                        Toast.makeText(context, "Por favor, corrija o email.", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF014B82)),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text("Enviar", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
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
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF014B82),
                unfocusedBorderColor = Color.LightGray,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                cursorColor = Color(0xFF014B82)
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