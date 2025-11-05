package com.bibliotecaunifor

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlugarLivros(
    navController: NavController,
    livroNome: String
) {
    val livroNomeDecoded = URLDecoder.decode(livroNome, StandardCharsets.UTF_8.toString())

    var menuAberto by remember { mutableStateOf(false) }
    var dataInicial by remember { mutableStateOf<LocalDate?>(null) }
    var dataFinal by remember { mutableStateOf<LocalDate?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.livros),
                    contentDescription = "Imagem de fundo biblioteca",
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color.White)
                        .align(Alignment.TopCenter),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
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

                    Spacer(modifier = Modifier.weight(0.63f))

                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo UNIFOR",
                        modifier = Modifier.size(40.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 16.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(
                        onClick = { navController.navigate(Route.Notificacoes.path) },
                        modifier = Modifier
                            .size(40.dp)
                            .pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notificações",
                            tint = Color.Black,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    IconButton(
                        onClick = { menuAberto = !menuAberto },
                        modifier = Modifier
                            .size(40.dp)
                            .pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            contentDescription = "Menu",
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(124.dp)
                        .align(Alignment.BottomCenter),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Alugue seu livro", color = Color.White, fontSize = 18.sp)
                        Text(
                            "Biblioteca Unifor",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = livroNomeDecoded.ifBlank { "Livro não identificado" },
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF044EE7),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Tempo de Permanência",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(vertical = 10.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FBFF)),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    CalendarField("Data Inicial", dataInicial) { dataInicial = it }
                    CalendarField("Data Final", dataFinal) { dataFinal = it }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044EE7))
            ) {
                Text("Alugar", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        if (menuAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable { menuAberto = false }
            )
            MenuLateral(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)),
                navController = navController
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarField(label: String, selectedDate: LocalDate?, onDateSelected: (LocalDate) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = selectedDate?.year ?: calendar.get(Calendar.YEAR)
    val month = selectedDate?.monthValue?.minus(1) ?: calendar.get(Calendar.MONTH)
    val day = selectedDate?.dayOfMonth ?: calendar.get(Calendar.DAY_OF_MONTH)

    OutlinedTextField(
        value = selectedDate?.toString() ?: "",
        onValueChange = {},
        readOnly = true,
        label = { Text(label, fontWeight = FontWeight.Medium, color = Color(0xFF044EE7)) },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                DatePickerDialog(context, { _, y, m, d ->
                    onDateSelected(LocalDate.of(y, m + 1, d))
                }, year, month, day).show()
            },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF044EE7),
            unfocusedBorderColor = Color(0xFFCBD5E1),
            focusedLabelColor = Color(0xFF044EE7)
        )
    )
}