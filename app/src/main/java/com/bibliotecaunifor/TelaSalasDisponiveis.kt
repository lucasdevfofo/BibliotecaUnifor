package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

@Composable
fun TelaSalasDisponiveis(
    navController: NavController,
    onVoltarClick: () -> Unit,
    onSalaClick: (String) -> Unit
) {
    var menuAberto by remember { mutableStateOf(false) }
    var chatAberto by remember { mutableStateOf(false) }

    // ✅ Fecha o chat automaticamente ao abrir o menu
    LaunchedEffect(menuAberto) {
        if (menuAberto) chatAberto = false
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Cabeçalho ---
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

                // 🔹 Cabeçalho sem botão de voltar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color.White)
                        .align(Alignment.TopCenter),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))

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
                        Text("Reserve sua sala", color = Color.White, fontSize = 18.sp)
                        Text(
                            "Biblioteca Unifor",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            // --- Fim Cabeçalho ---

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Salas Disponíveis",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 20.dp),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            val salas = listOf(
                "SALA 01", "SALA 02", "SALA 03", "SALA 04",
                "SALA 05", "SALA 06", "SALA 07", "SALA 08"
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                itemsIndexed(salas) { index, sala ->
                    val bgColor = if (index % 2 == 0) Color(0xFFE7EEFF) else Color.Transparent
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(bgColor, RoundedCornerShape(4.dp))
                            .padding(vertical = 12.dp)
                            .clickable { navController.navigate("reserva_sala/$sala") }
                    ) {
                        Text(
                            text = sala,
                            color = Color(0xFF044EE7),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // --- Barra inferior ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painterResource(id = R.drawable.ic_home), "Home", tint = Color.Black)
                Icon(painterResource(id = R.drawable.ic_calendar), "Reservas", tint = Color.Gray)
                Icon(
                    painter = painterResource(id = R.drawable.ic_list),
                    contentDescription = "Listas",
                    tint = Color.Gray,
                    modifier = Modifier.clickable { navController.navigate(Route.ReservasRealizadas.path) }
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "Perfil",
                    tint = Color.Black,
                    modifier = Modifier.clickable { navController.navigate(Route.PerfilAluno.path) }
                )
            }
        }

        // --- Menu lateral ---
        if (menuAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .zIndex(0.5f)
                    .clickable(
                        onClick = { menuAberto = false },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
            )

            MenuLateral(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .zIndex(1f)
                    .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)),
                navController = navController
            )
        }

        // --- Botão flutuante do chat ---
        if (!menuAberto) {
            FloatingActionButton(
                onClick = { chatAberto = !chatAberto },
                containerColor = Color(0xFF044EE7),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 80.dp)
                    .zIndex(2f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat),
                    contentDescription = "Chatbot",
                    tint = Color.White
                )
            }
        }

        // --- Caixa do Chat (com envio) ---
        if (chatAberto && !menuAberto) {
            ChatBotPopup(onFechar = { chatAberto = false })
        }
    }
}

@Composable
fun ChatBotPopup(onFechar: () -> Unit) {
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    var mensagens by remember {
        mutableStateOf(listOf("🤖 Olá! Posso te ajudar com informações sobre horários, reservas e livros disponíveis."))
    }

    val respostas = mapOf(
        "horario" to "A biblioteca funciona de segunda a sexta, das 7h às 20h.",
        "livro" to "Você pode reservar livros pelo app ou no balcão de atendimento.",
        "mesa" to "As mesas disponíveis estão listadas acima! Basta clicar em uma para reservar.",
        "reserva" to "Para reservar, selecione uma sala e confirme sua escolha.",
        "oi" to "Olá! Como posso te ajudar? 😊"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.5f)
            .background(Color.White, RoundedCornerShape(12.dp))
            .shadow(4.dp)
            .zIndex(3f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Chat Biblioteca 🤖",
                color = Color(0xFF044EE7),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(mensagens) { _, msg ->
                    Text(
                        text = msg,
                        fontSize = 14.sp,
                        color = if (msg.startsWith("🤖")) Color.Black else Color(0xFF044EE7),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Digite sua mensagem...") },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF2F2F2),
                        unfocusedContainerColor = Color(0xFFF2F2F2),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        val textoUsuario = inputText.text.trim()
                        if (textoUsuario.isNotEmpty()) {
                            mensagens = mensagens + "Você: $textoUsuario"
                            val resposta = respostas.entries.find {
                                textoUsuario.contains(it.key, ignoreCase = true)
                            }?.value ?: "Desculpe, não entendi. Tente perguntar sobre horários, livros ou reservas."
                            mensagens = mensagens + "🤖 $resposta"
                            inputText = TextFieldValue("")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044EE7))
                ) {
                    Text("Enviar", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onFechar,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044EE7)),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Fechar", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaSalasDisponiveisPreview() {
    val fakeNavController =
        androidx.navigation.testing.TestNavHostController(androidx.compose.ui.platform.LocalContext.current)
    BibliotecaUniforTheme {
        TelaSalasDisponiveis(
            navController = fakeNavController,
            onVoltarClick = {},
            onSalaClick = {}
        )
    }
}
