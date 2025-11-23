package com.bibliotecaunifor

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bibliotecaunifor.ui.theme.roxoBotao
import com.bibliotecaunifor.viewmodel.TelaAgendarAluguelViewModel
import java.net.URLDecoder // <--- Import Adicionado
import java.nio.charset.StandardCharsets // <--- Import Adicionado
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAgendarAluguel(
    navController: NavController,
    livroId: String,
    tituloLivro: String,
    viewModel: TelaAgendarAluguelViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    // --- CORREÇÃO: Decodifica o título para remover os "+" ---
    val tituloDecodificado = remember(tituloLivro) {
        try {
            URLDecoder.decode(tituloLivro, StandardCharsets.UTF_8.toString())
        } catch (e: Exception) {
            tituloLivro // Se der erro, usa o original
        }
    }
    // --------------------------------------------------------

    // Estados para o DatePicker
    var showDatePickerInicio by remember { mutableStateOf(false) }
    var showDatePickerFim by remember { mutableStateOf(false) }

    // Datas selecionadas (em milissegundos)
    var dataInicioMillis by remember { mutableStateOf<Long?>(null) }
    var dataFimMillis by remember { mutableStateOf<Long?>(null) }

    // Formatador de data para exibir no texto
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")) }

    // Reage ao sucesso/erro
    LaunchedEffect(uiState) {
        when(uiState) {
            is TelaAgendarAluguelViewModel.UiState.Success -> {
                Toast.makeText(context, "Agendamento realizado!", Toast.LENGTH_LONG).show()
                // Volta para o catálogo (remove a tela de descrição e agendamento da pilha)
                // Se der erro de navegação aqui, tente usar apenas navController.popBackStack() duas vezes
                try {
                    navController.popBackStack("catalogo_livros", inclusive = false)
                } catch (e: Exception) {
                    // Fallback se a rota não for encontrada, volta 2 telas
                    navController.popBackStack()
                    navController.popBackStack()
                }
            }
            is TelaAgendarAluguelViewModel.UiState.Error -> {
                val erro = (uiState as TelaAgendarAluguelViewModel.UiState.Error).message
                Toast.makeText(context, erro, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar Aluguel") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = tituloDecodificado.uppercase(), // <--- Usando o título corrigido
                color = roxoBotao,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            // --- CAMPO DATA INICIAL ---
            OutlinedTextField(
                value = if (dataInicioMillis != null) dateFormatter.format(Date(dataInicioMillis!!)) else "",
                onValueChange = {},
                label = { Text("Data de Retirada") },
                readOnly = true, // Impede digitar, obriga clicar no ícone
                trailingIcon = {
                    IconButton(onClick = { showDatePickerInicio = true }) {
                        Icon(Icons.Filled.DateRange, contentDescription = "Selecionar Data")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- CAMPO DATA FINAL ---
            OutlinedTextField(
                value = if (dataFimMillis != null) dateFormatter.format(Date(dataFimMillis!!)) else "",
                onValueChange = {},
                label = { Text("Data de Devolução") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePickerFim = true }) {
                        Icon(Icons.Filled.DateRange, contentDescription = "Selecionar Data")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            // --- BOTÃO CONFIRMAR ---
            Button(
                onClick = {
                    // Passando o título corrigido para salvar no banco corretamente
                    viewModel.confirmarAluguel(livroId, tituloDecodificado, dataInicioMillis, dataFimMillis)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = roxoBotao),
                shape = RoundedCornerShape(8.dp),
                enabled = uiState !is TelaAgendarAluguelViewModel.UiState.Loading
            ) {
                if (uiState is TelaAgendarAluguelViewModel.UiState.Loading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("CONFIRMAR AGENDAMENTO", fontWeight = FontWeight.Bold)
                }
            }
        }

        // --- DIALOGS DE CALENDÁRIO ---
        if (showDatePickerInicio) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showDatePickerInicio = false },
                confirmButton = {
                    TextButton(onClick = {
                        dataInicioMillis = datePickerState.selectedDateMillis
                        // Opcional: define data final automaticamente para 7 dias depois
                        if (dataInicioMillis != null) {
                            dataFimMillis = dataInicioMillis!! + (7L * 24 * 60 * 60 * 1000)
                        }
                        showDatePickerInicio = false
                    }) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePickerInicio = false }) { Text("Cancelar") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        if (showDatePickerFim) {
            val datePickerState = rememberDatePickerState(initialSelectedDateMillis = dataFimMillis)
            DatePickerDialog(
                onDismissRequest = { showDatePickerFim = false },
                confirmButton = {
                    TextButton(onClick = {
                        dataFimMillis = datePickerState.selectedDateMillis
                        showDatePickerFim = false
                    }) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePickerFim = false }) { Text("Cancelar") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}