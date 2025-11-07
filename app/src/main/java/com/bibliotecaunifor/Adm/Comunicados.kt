package com.bibliotecaunifor.Adm

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.bibliotecaunifor.R
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

val azulUifrBotao = Color(0xFF044EE7)
val laranjaBloco = Color(0xFFFFE066)
val cinzaBordaHeader = Color(0xFFE0E0E0)

@Composable
fun ComunicadosScreen(
    onSendMessage: () -> Unit,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .border(width = 1.dp, color = cinzaBordaHeader)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .align(Alignment.Center)
                    .padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onSendMessage,
                    modifier = Modifier
                        .size(60.dp)
                        .pointerHoverIcon(PointerIcon.Hand)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.weight(0.5f))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.weight(0.8f))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Comunicado",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = {
                    Text(
                        "Digite aqui...",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(250.dp)
                    .shadow(4.dp, RoundedCornerShape(12.dp))
                    .background(laranjaBloco, RoundedCornerShape(12.dp)),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                ),
                singleLine = false,
                maxLines = 10
            )
        }

        Button(
            onClick = onSendMessage,
            modifier = Modifier
                .fillMaxWidth()
                .height(57.dp)
                .padding(bottom = 12.dp, start = 20.dp, end = 20.dp)
                .pointerHoverIcon(PointerIcon.Hand),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = azulUifrBotao)
        ) {
            Text(text = "Enviar", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComunicadosPreview() {
    BibliotecaUniforTheme {
        ComunicadosScreen(onSendMessage = {})
    }
}