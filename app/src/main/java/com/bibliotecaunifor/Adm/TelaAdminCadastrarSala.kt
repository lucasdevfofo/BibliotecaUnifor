package com.bibliotecaunifor.Adm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.R
import com.bibliotecaunifor.Route
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

@Composable
fun TelaAdminCadastrarSala(
    navController: NavController,
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onMenuClick: () -> Unit,
    onNavHomeClick: () -> Unit,
    onNavHistoricoClick: () -> Unit,
    onNavListasClick: () -> Unit,
    onNavPerfilClick: () -> Unit,
    currentRoute: String
) {
    var menuAberto by remember { mutableStateOf(false) }


    var nomeSala by remember { mutableStateOf("") }
    var quantidadeMesas by remember { mutableStateOf("") }

    val azulPrimario = Color(0xFF3F4F78)
    val cinzaClaro = Color(0xFFF5F5F5)

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                AppBottomNavAdminPadrao(
                    onHomeClick = onNavHomeClick,
                    onHistoricoClick = onNavHistoricoClick,
                    onListasClick = onNavListasClick,
                    onPerfilClick = onNavPerfilClick,
                    currentRoute = currentRoute
                )
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {

                AdminTopBarCadastro(
                    onVoltarClick = onVoltarClick,
                    onNotificacoesClick = { navController.navigate(Route.TelaNotificacoesAdmin.path) },
                    onMenuClick = { menuAberto = true }
                )


                Text(
                    text = "Cadastrar Nova Sala",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 24.dp, bottom = 8.dp)
                        .align(Alignment.Start)
                )

                Text(
                    text = "Preencha os dados da nova sala",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(start = 20.dp, bottom = 32.dp)
                        .align(Alignment.Start)
                )


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {

                    Column {
                        Text(
                            text = "Nome da Sala *",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = nomeSala,
                            onValueChange = { nomeSala = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            placeholder = {
                                Text(
                                    text = "Ex: SALA 01, LAB 02, etc.",
                                    color = Color.Gray
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = cinzaClaro,
                                unfocusedContainerColor = cinzaClaro,
                                focusedIndicatorColor = azulPrimario,
                                unfocusedIndicatorColor = Color.Gray
                            ),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true
                        )
                    }


                    Column {
                        Text(
                            text = "Quantidade de Mesas *",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = quantidadeMesas,
                            onValueChange = {
                                if (it.all { char -> char.isDigit() }) {
                                    quantidadeMesas = it
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            placeholder = {
                                Text(
                                    text = "Ex: 20, 30, 40",
                                    color = Color.Gray
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = cinzaClaro,
                                unfocusedContainerColor = cinzaClaro,
                                focusedIndicatorColor = azulPrimario,
                                unfocusedIndicatorColor = Color.Gray
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))


                    Button(
                        onClick = {

                            if (nomeSala.isNotBlank() && quantidadeMesas.isNotBlank()) {

                                navController.popBackStack()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = azulPrimario),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = nomeSala.isNotBlank() && quantidadeMesas.isNotBlank()
                    ) {
                        Text(
                            text = "CADASTRAR SALA",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }


                    Text(
                        text = "Cancelar",
                        color = azulPrimario,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onVoltarClick() }
                            .padding(vertical = 16.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }


        if (menuAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f))
                    .clickable { menuAberto = false },
                contentAlignment = Alignment.TopEnd
            ) {
                MenuLateralAdmin(
                    modifier = Modifier.fillMaxHeight().fillMaxWidth(0.75f),
                    navController = navController,
                    onLinkClick = { menuAberto = false }
                )
            }
        }
    }
}

@Composable
fun AdminTopBarCadastro(
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {

        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.livros),
            contentDescription = "Imagem de fundo biblioteca",
            modifier = Modifier.matchParentSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White)
                .align(Alignment.TopCenter)
        )


        IconButton(
            onClick = onVoltarClick,
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.TopStart)
                .zIndex(2f)
        ) {
            Icon(Icons.Filled.ArrowBack, "Voltar", tint = Color.Black, modifier = Modifier.size(30.dp))
        }


        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo UNIFOR",
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.TopCenter)
                .offset(y = 8.dp)
                .zIndex(2f)
        )


        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp, top = 8.dp)
                .zIndex(2f),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(
                onClick = onNotificacoesClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(Icons.Outlined.Notifications, "Notificações", tint = Color.Black, modifier = Modifier.size(28.dp))
            }

            IconButton(
                onClick = onMenuClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(Icons.Outlined.Menu, "Menu", tint = Color.Black, modifier = Modifier.size(30.dp))
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
                Text("Cadastrar Sala", color = Color.White, fontSize = 18.sp)
                Text(
                    "Biblioteca Unifor",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaAdminCadastrarSalaPreview() {
    BibliotecaUniforTheme {
        TelaAdminCadastrarSala(
            navController = rememberNavController(),
            onVoltarClick = {},
            onNotificacoesClick = {},
            onMenuClick = {},
            onNavHomeClick = {},
            onNavHistoricoClick = {},
            onNavListasClick = {},
            onNavPerfilClick = {},
            currentRoute = Route.TelaAdminCadastrarSala.path
        )
    }
}