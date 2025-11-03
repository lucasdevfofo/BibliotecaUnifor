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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlugarLivros(navController: NavController) {
    var menuAberto by remember { mutableStateOf(false) }
    var dataInicial by remember { mutableStateOf<LocalDate?>(null) }
    var dataFinal by remember { mutableStateOf<LocalDate?>(null) }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // --- Cabeçalho padrão ---
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
                        onClick = { navController.popBackStack() }, // ✅ volta pra tela anterior
                        modifier = Modifier.size(60.dp).pointerHoverIcon(PointerIcon.Hand)
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
                        modifier = Modifier.size(40.dp).pointerHoverIcon(PointerIcon.Hand)
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
                        modifier = Modifier.size(40.dp).pointerHoverIcon(PointerIcon.Hand)
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

            // --- Corpo da tela ---
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Pequeno Príncipe",
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF044EE7),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = "Tempo de Permanência",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CalendarField("Data Inicial", dataInicial) { dataInicial = it }
                CalendarField("Data Final", dataFinal) { dataFinal = it }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    // futuro: confirmar aluguel
                    navController.popBackStack() // volta pra tela anterior
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(50.dp)
                    .pointerHoverIcon(PointerIcon.Hand),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044EE7))
            ) {
                Text(text = "Alugar", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        // --- Menu lateral ---
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
        label = { Text(label, fontWeight = FontWeight.Medium) },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                DatePickerDialog(context, { _, y, m, d ->
                    onDateSelected(LocalDate.of(y, m + 1, d))
                }, year, month, day).show()
            }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AlugarLivrosPreview() {
    val fakeNavController =
        androidx.navigation.testing.TestNavHostController(LocalContext.current)
    BibliotecaUniforTheme {
        AlugarLivros(navController = fakeNavController)
    }
}
