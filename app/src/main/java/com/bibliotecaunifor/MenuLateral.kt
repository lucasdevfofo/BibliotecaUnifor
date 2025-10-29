package com.bibliotecaunifor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

class MenuLateralActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BibliotecaUniforTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MenuLateral(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MenuLateral(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(0.dp)
            .fillMaxHeight()
            .fillMaxWidth(0.6f)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_menu_lateral),
                contentDescription = "Imagem topo",
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .align(Alignment.Start)
            )

            val links = listOf(
                "Perfil",
                "Catálogo de Livros",
                "Alugar um Livro",
                "Acessibilidade",
                "Lista de Salas",
                "Reservar Mesa"
            )

            links.forEachIndexed { index, link ->
                val shape = when (index) {
                    else -> androidx.compose.foundation.shape.RoundedCornerShape(0.dp)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.Black.copy(alpha = 0.4f),
                            shape = shape
                        )
                        .clickable { }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = link,
                        fontSize = 18.sp,
                        color = Color.Black.copy(alpha = 0.8f),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(0.dp))
            }
        }
    }
}