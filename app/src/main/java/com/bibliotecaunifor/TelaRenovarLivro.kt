package com.bibliotecaunifor

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bibliotecaunifor.ui.theme.roxoBotao
import com.bibliotecaunifor.viewmodel.TelaRenovarLivroViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaRenovarLivro(
    navController: NavController,
    // Novos parâmetros que vêm da rota
    aluguelId: String = "",
    tituloLivro: String = "LIVRO SELECIONADO",
    dataDevolucaoAtualMillis: Long = System.currentTimeMillis(),
    viewModel: TelaRenovarLivroViewModel = viewModel()
) {
    var menuAberto by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    // --- LÓGICA DE DATAS ---
    val dateFormatter = remember { SimpleDateFormat("dd/MM", Locale("pt", "BR")) }

    // 1. Decodifica o título (tira os símbolos estranhos da URL)
    val tituloDecodificado = remember(tituloLivro) {
        try { URLDecoder.decode(tituloLivro, StandardCharsets.UTF_8.toString()) } catch (e: Exception) { tituloLivro }
    }

    // 2. Calcula o início da renovação (Dia seguinte ao vencimento atual)
    val dataInicioRenovacaoMillis = remember { dataDevolucaoAtualMillis + (24L * 60 * 60 * 1000) }

    // 3. Estado da data final escolhida pelo usuário
    var novaDataFimMillis by remember { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }

    // 4. Texto dinâmico do campo (Ex: "25/11 - Selecione...")
    val textoPermanencia = remember(novaDataFimMillis) {
        val inicio = dateFormatter.format(Date(dataInicioRenovacaoMillis))
        val fim = if (novaDataFimMillis != null) dateFormatter.format(Date(novaDataFimMillis!!)) else "Selecione"
        "$inicio - $fim"
    }

    // --- REAÇÃO AO SUCESSO/ERRO ---
    LaunchedEffect(uiState) {
        when(uiState) {
            is TelaRenovarLivroViewModel.UiState.Success -> {
                Toast.makeText(context, "Renovação confirmada!", Toast.LENGTH_LONG).show()
                navController.popBackStack() // Volta para o Perfil
            }
            is TelaRenovarLivroViewModel.UiState.Error -> {
                val erro = (uiState as TelaRenovarLivroViewModel.UiState.Error).message
                Toast.makeText(context, erro, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- HEADER (MANTIDO IGUAL AO SEU) ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.livros),
                    contentDescription = "Imagem de fundo",
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
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.Black)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo UNIFOR",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                // ... Ícones de Notificação e Menu (Mantidos) ...
                Row(
                    modifier = Modifier.align(Alignment.TopEnd).padding(end = 16.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(onClick = { navController.navigate(Route.Notificacoes.path) }, modifier = Modifier.size(40.dp)) {
                        Icon(Icons.Outlined.Notifications, null, tint = Color.Black, modifier = Modifier.size(28.dp))
                    }
                    IconButton(onClick = { menuAberto = !menuAberto }, modifier = Modifier.size(40.dp)) {
                        Icon(Icons.Outlined.Menu, null, tint = Color.Black, modifier = Modifier.size(30.dp))
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth().height(124.dp).align(Alignment.BottomCenter),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Reserve seu livro", color = Color.White, fontSize = 18.sp)
                        Text("Biblioteca Unifor", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // --- TÍTULO DO LIVRO (AGORA DINÂMICO) ---
            Text(
                text = tituloDecodificado.uppercase(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF044EE7),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "TEMPO DE PERMANÊNCIA (RENOVAÇÃO)",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 20.dp)
            )

            // --- CAMPO DE DATA ---
            // Tornei o campo todo clicável para abrir o calendário
            Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                OutlinedTextField(
                    value = textoPermanencia,
                    onValueChange = { },
                    readOnly = true, // Usuário não digita, só escolhe
                    trailingIcon = {
                        Icon(
                            Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Selecionar Data",
                            tint = Color.Gray
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable { showDatePicker = true }, // Clique no Box ativa
                    enabled = false, // Desabilita edição manual
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Black,
                        disabledBorderColor = Color.LightGray,
                        disabledLabelColor = Color.Black
                    )
                )
                // Overlay invisível para garantir o clique
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { showDatePicker = true }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Máximo de 8 dias a partir do vencimento atual.",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(140.dp))

            // --- BOTÃO CONFIRMAR ---
            Button(
                onClick = {
                    viewModel.confirmarRenovacao(aluguelId, dataDevolucaoAtualMillis, novaDataFimMillis)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F4F78)),
                shape = RoundedCornerShape(8.dp),
                enabled = uiState !is TelaRenovarLivroViewModel.UiState.Loading
            ) {
                if (uiState is TelaRenovarLivroViewModel.UiState.Loading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        "CONFIRMAR RENOVAÇÃO",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // --- BOTTOM BAR (MANTIDA) ---
            Row(
                modifier = Modifier.fillMaxWidth().height(56.dp).background(Color.White),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Home, null, tint = Color.Gray, modifier = Modifier.clickable { navController.navigate(Route.SalasDisponiveis.path) })
                Icon(Icons.Filled.DateRange, null, tint = Color.Gray, modifier = Modifier.clickable { navController.navigate(Route.ReservasRealizadas.path) })
                Icon(Icons.Filled.List, null, tint = Color.Gray, modifier = Modifier.clickable { navController.navigate(Route.CatalogoLivros.path) })
                Icon(Icons.Filled.Person, null, tint = Color.Gray, modifier = Modifier.clickable { navController.navigate(Route.PerfilAluno.path) })
            }
        }

        // --- DIALOG DO CALENDÁRIO ---
        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(initialSelectedDateMillis = dataInicioRenovacaoMillis)
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        novaDataFimMillis = datePickerState.selectedDateMillis
                        showDatePicker = false
                    }) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) { Text("Cancelar") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        // --- MENU LATERAL (MANTIDO) ---
        if (menuAberto) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)).zIndex(0.5f)
                    .clickable(onClick = { menuAberto = false }, indication = null, interactionSource = remember { MutableInteractionSource() })
            )
            MenuLateral(
                modifier = Modifier.align(Alignment.TopEnd).zIndex(1f).clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)),
                navController = navController
            )
        }
    }
}