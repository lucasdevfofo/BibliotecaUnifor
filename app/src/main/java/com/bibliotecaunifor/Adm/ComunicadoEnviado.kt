package com.bibliotecaunifor.Adm

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.bibliotecaunifor.R
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

val azulUniorBotao = Color(0xFF044EE7)
val overlayPreto = Color(0x99000000)

@Composable
fun ComunicadoEnviadoScreen(
    onNavigateBackToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(overlayPreto),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.emacomverificado),
            contentDescription = "Imagem principal",
            modifier = Modifier.size(250.dp)

        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Comunicado enviado!",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Chegará nos usuários em alguns instantes",
            fontSize = 17.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onNavigateBackToLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(57.dp)
                .padding(start = 20.dp, end = 20.dp)
                .pointerHoverIcon(PointerIcon.Hand),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = azulUniorBotao)
        ) {
            Text(text = "Voltar", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComunicadoEnviadoPreview() {
    BibliotecaUniforTheme {
        ComunicadoEnviadoScreen(onNavigateBackToLogin = {})
    }
}