package com.bibliotecaunifor

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

class EsqueceuSenhaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BibliotecaUniforTheme {
                EsqueceuSenhaScreen(
                    onNavigateUp = { finish() },
                    onEnviarClick = { email ->

                        Toast.makeText(this, "Link de recuperação enviado para $email!", Toast.LENGTH_LONG).show()
                        finish()
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



@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EsqueceuSenhaScreen(onNavigateUp: () -> Unit, onEnviarClick: (String) -> Unit) {

    var email by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    Scaffold(
        topBar = { EsqueceuSenhaTopBar(onNavigateUp) },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            EsqueceuSenhaHeader()


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Label de instrução
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
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B5998)),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Text("Enviar", fontSize = 18.sp)
                }
            }
        }
    }
}



@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EsqueceuSenhaTopBar(onNavigateUp: () -> Unit) {
    TopAppBar(
        title = {  },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
fun EsqueceuSenhaHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.livros),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.White)
        )

        //
        Image(
            painter = painterResource(id = R.drawable.logo2
            ),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )



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

        // Exibir a mensagem de erro (substitui o `editEmail.error = "..."`)
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