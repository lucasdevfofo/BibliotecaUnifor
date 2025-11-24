package com.bibliotecaunifor.Adm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bibliotecaunifor.viewmodel.RegrasDoSistemaViewModel
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegrasDoSistema(
    navController: NavController,
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onNavSalasClick: () -> Unit,
    onNavReservasClick: () -> Unit,
    onNavUsuariosClick: () -> Unit,
    onNavPerfilClick: () -> Unit,
    currentRoute: String
) {
    val viewModel: RegrasDoSistemaViewModel = viewModel()
    val context = LocalContext.current

    var menuLateralAberto by remember { mutableStateOf(false) }

    val corPrincipal = Color(0xFF044EE7)
    val corFundoInput = Color(0xFFF2F2F2)
    val scrollState = rememberScrollState()

    val uiState by viewModel.uiState.collectAsState()
    val configuracao by viewModel.configuracao.collectAsState()

    LaunchedEffect(uiState) {
        when (uiState) {
            is RegrasDoSistemaViewModel.UiState.Success -> {
                Toast.makeText(context, "Configurações salvas com sucesso!", Toast.LENGTH_SHORT).show()
            }
            is RegrasDoSistemaViewModel.UiState.Error -> {
                val error = (uiState as RegrasDoSistemaViewModel.UiState.Error).message
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                AdminTopBar(
                    onVoltarClick = onVoltarClick,
                    onNotificacoesClick = onNotificacoesClick,
                    onMenuClick = { menuLateralAberto = true }
                )
            },
            bottomBar = {
                AppBottomNavAdminPadrao(
                    onHomeClick = onNavSalasClick,
                    onHistoricoClick = onNavReservasClick,
                    onListasClick = onNavUsuariosClick,
                    onPerfilClick = onNavPerfilClick,
                    currentRoute = currentRoute
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
                    .verticalScroll(scrollState)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        "REGRAS DO SISTEMA",
                        color = corPrincipal,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Divider(
                        modifier = Modifier.padding(top = 8.dp),
                        color = corPrincipal.copy(alpha = 0.5f)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    when (configuracao) {
                        is RegrasDoSistemaViewModel.ConfiguracaoState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = corPrincipal)
                            }
                        }
                        is RegrasDoSistemaViewModel.ConfiguracaoState.Error -> {
                            Text(
                                text = "Erro ao carregar configurações",
                                color = Color.Red,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                        }
                        is RegrasDoSistemaViewModel.ConfiguracaoState.Success -> {
                            val config = (configuracao as RegrasDoSistemaViewModel.ConfiguracaoState.Success).configuracao

                            Text(
                                text = "Limite de Livros por Usuário",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            OutlinedTextField(
                                value = config.limiteLivrosPorUsuario.toString(),
                                onValueChange = { novoValor ->
                                    if (novoValor.all { it.isDigit() } && novoValor.isNotEmpty()) {
                                        viewModel.atualizarLimiteLivros(novoValor.toInt())
                                    }
                                },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = corPrincipal,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedContainerColor = corFundoInput,
                                    unfocusedContainerColor = corFundoInput
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Dias Máximo de Empréstimo",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            OutlinedTextField(
                                value = config.diasMaximoEmprestimo.toString(),
                                onValueChange = { novoValor ->
                                    if (novoValor.all { it.isDigit() } && novoValor.isNotEmpty()) {
                                        viewModel.atualizarDiasEmprestimo(novoValor.toInt())
                                    }
                                },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = corPrincipal,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedContainerColor = corFundoInput,
                                    unfocusedContainerColor = corFundoInput
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Limite de Salas por Usuário",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            OutlinedTextField(
                                value = config.limiteSalasPorUsuario.toString(),
                                onValueChange = { novoValor ->
                                    if (novoValor.all { it.isDigit() } && novoValor.isNotEmpty()) {
                                        viewModel.atualizarLimiteSalas(novoValor.toInt())
                                    }
                                },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = corPrincipal,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedContainerColor = corFundoInput,
                                    unfocusedContainerColor = corFundoInput
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )

                            Spacer(modifier = Modifier.height(48.dp))

                            Button(
                                onClick = {
                                    viewModel.salvarConfiguracoes()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .pointerHoverIcon(PointerIcon.Hand),
                                colors = ButtonDefaults.buttonColors(containerColor = corPrincipal),
                                shape = RoundedCornerShape(8.dp),
                                enabled = uiState !is RegrasDoSistemaViewModel.UiState.Loading
                            ) {
                                if (uiState is RegrasDoSistemaViewModel.UiState.Loading) {
                                    CircularProgressIndicator(
                                        color = Color.White,
                                        modifier = Modifier.size(24.dp)
                                    )
                                } else {
                                    Icon(
                                        Icons.Filled.Save,
                                        contentDescription = "Salvar",
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("SALVAR CONFIGURAÇÕES", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }
        }

        if (menuLateralAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f))
                    .clickable { menuLateralAberto = false },
                contentAlignment = Alignment.TopEnd
            ) {
                MenuLateralAdmin(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.75f)
                        .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)),
                    navController = navController,
                    onLinkClick = { menuLateralAberto = false }
                )
            }
        }
    }
}