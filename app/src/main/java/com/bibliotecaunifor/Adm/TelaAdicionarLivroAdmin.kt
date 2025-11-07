package com.bibliotecaunifor.Adm

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.R
import com.bibliotecaunifor.Route

@Composable
fun AdminTopBarAdicionar(
    navController: NavController,
    onMenuClick: () -> Unit
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White)
                .align(Alignment.TopCenter)
        )

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.TopStart)
                .zIndex(2f)
                .pointerHoverIcon(PointerIcon.Hand)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar", tint = Color.Black, modifier = Modifier.size(30.dp))
        }

        Image(
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
                onClick = { navController.navigate(Route.Notificacoes.path) },
                modifier = Modifier.size(40.dp).pointerHoverIcon(PointerIcon.Hand)
            ) {
                Icon(Icons.Outlined.Notifications, "Notificações", tint = Color.Black, modifier = Modifier.size(28.dp))
            }

            IconButton(
                onClick = onMenuClick,
                modifier = Modifier.size(40.dp).pointerHoverIcon(PointerIcon.Hand)
            ) {
                Icon(Icons.Outlined.Menu, "Menu", tint = Color.Black, modifier = Modifier.size(30.dp))
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(124.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                "Biblioteca Unifor",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAdicionarLivroAdmin(
    navController: NavController
) {
    var menuLateralAberto by remember { mutableStateOf(false) }

    // Estados do Formulário (Inicialmente vazios)
    var nome by remember { mutableStateOf("") }
    var sobre by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var disponivel by remember { mutableStateOf(true) }

    // Estado do Dropdown de Disponibilidade
    val opcoesDisponibilidade = listOf("Disponível", "Indisponível")
    var expandirDisponibilidade by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                AdminTopBarAdicionar(
                    navController = navController,
                    onMenuClick = { menuLateralAberto = true }
                )
            },
            bottomBar = {

            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Text(
                    "ADICIONAR LIVRO",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3F4F78),
                    modifier = Modifier.padding(bottom = 16.dp)
                )


                FormLabel(label = "Nome:")
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    singleLine = true,
                    placeholder = { Text("Ex: O Pequeno Príncipe", fontSize = 16.sp) },
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                        focusedBorderColor = Color(0xFF3F4F78)
                    )
                )


                FormLabel(label = "Sobre:")
                OutlinedTextField(
                    value = sobre,
                    onValueChange = { sobre = it },
                    modifier = Modifier.fillMaxWidth().height(100.dp).padding(bottom = 16.dp),
                    placeholder = { Text("Ex: É uma fábula sobre um menino...", fontSize = 16.sp) },
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                        focusedBorderColor = Color(0xFF3F4F78)
                    )
                )


                FormLabel(label = "Gênero:")
                OutlinedTextField(
                    value = genero,
                    onValueChange = { genero = it },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    singleLine = true,
                    placeholder = { Text("Ex: Fábula filosófica", fontSize = 16.sp) },
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                        focusedBorderColor = Color(0xFF3F4F78)
                    )
                )


                FormLabel(label = "Autor:")
                OutlinedTextField(
                    value = autor,
                    onValueChange = { autor = it },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    singleLine = true,
                    placeholder = { Text("Ex: Antoine de Saint-Exupéry", fontSize = 16.sp) },
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                        focusedBorderColor = Color(0xFF3F4F78)
                    )
                )

                FormLabel(label = "Disponibilidade:")
                Box(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)) {
                    OutlinedTextField(
                        value = if (disponivel) "Disponível" else "Indisponível",
                        onValueChange = { },
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                imageVector = if (expandirDisponibilidade) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Expandir",
                                Modifier.clickable { expandirDisponibilidade = !expandirDisponibilidade }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandirDisponibilidade = !expandirDisponibilidade },
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                            focusedBorderColor = Color(0xFF3F4F78)
                        )
                    )

                    DropdownMenu(
                        expanded = expandirDisponibilidade,
                        onDismissRequest = { expandirDisponibilidade = false },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        opcoesDisponibilidade.forEach { selecao ->
                            DropdownMenuItem(
                                text = { Text(selecao) },
                                onClick = {
                                    disponivel = selecao == "Disponível"
                                    expandirDisponibilidade = false
                                }
                            )
                        }
                    }
                }

                Button(
                    onClick = {

                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F4F78)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("CONFIRMAR", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }


        if (menuLateralAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(4f)
                    .clickable { menuLateralAberto = false }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                )


                MenuLateralAdmin(
                    navController = navController,
                    onLinkClick = { menuLateralAberto = false },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .fillMaxHeight()
                        .fillMaxWidth(0.75f)
                )
            }
        }
    }
}


@Composable
fun FormLabel(label: String) {
    Text(
        text = label,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF3F4F78),
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun TelaAdicionarLivroAdminPreview() {
    TelaAdicionarLivroAdmin(
        navController = rememberNavController()
    )
}