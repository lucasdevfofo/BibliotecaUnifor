package com.bibliotecaunifor.Adm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


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
    var menuLateralAberto by remember { mutableStateOf(false) }
    var tempoMaximo by remember { mutableStateOf(TextFieldValue("7 dias")) }
    var limiteLivros by remember { mutableStateOf(TextFieldValue("2 livros")) }

    val corPrincipal = Color(0xFF044EE7)
    val corFundoInput = Color(0xFFF2F2F2)
    val scrollState = rememberScrollState()

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


                    Text(
                        text = "Tempo Máximo de Empréstimo",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    OutlinedTextField(
                        value = tempoMaximo,
                        onValueChange = { tempoMaximo = it },
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
                        text = "Limite de Livros por Usuário",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    OutlinedTextField(
                        value = limiteLivros,
                        onValueChange = { limiteLivros = it },
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
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .pointerHoverIcon(PointerIcon.Hand),
                        colors = ButtonDefaults.buttonColors(containerColor = corPrincipal),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Enviar", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(20.dp))
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
                    modifier = Modifier.fillMaxHeight().fillMaxWidth(0.75f).clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)),
                    navController = navController,
                    onLinkClick = { menuLateralAberto = false }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegrasDoSistemaPreview() {
    RegrasDoSistema(
        navController = rememberNavController(),
        onVoltarClick = {},
        onNotificacoesClick = {},
        onNavSalasClick = {},
        onNavReservasClick = {},
        onNavUsuariosClick = {},
        onNavPerfilClick = {},
        currentRoute = "regras-do-sistema"
    )
}