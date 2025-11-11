package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*



data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@Composable
fun TelaChatBotUsuario(
    navController: NavController,
    onVoltarClick: () -> Unit
) {
    var mensagens by remember { mutableStateOf<List<ChatMessage>>(emptyList()) }
    var textoMensagem by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        mensagens = listOf(
            ChatMessage(
                id = "1",
                text = "Olá! Sou o Unibô, assistente virtual da biblioteca. Como posso ajudar você hoje?",
                isUser = false
            )
        )
    }


    LaunchedEffect(mensagens.size) {
        if (mensagens.isNotEmpty()) {
            lazyListState.animateScrollToItem(mensagens.size - 1)
        }
    }


    fun enviarMensagem() {
        if (textoMensagem.isBlank()) return


        val mensagemUsuario = ChatMessage(
            id = System.currentTimeMillis().toString(),
            text = textoMensagem,
            isUser = true
        )

        val novasMensagens = mensagens + mensagemUsuario
        mensagens = novasMensagens
        textoMensagem = ""


        coroutineScope.launch {
            delay(1000)

            val respostas = mapOf(
                "horario" to "A biblioteca funciona de segunda a sexta, das 7h às 20h.",
                "livro" to "Você pode reservar livros pelo app ou no balcão de atendimento.",
                "sala" to "As salas disponíveis estão na seção 'Salas Disponíveis'! Basta clicar em uma para reservar.",
                "reserva" to "Para reservar, selecione uma sala e confirme sua escolha.",
                "oi" to "Olá! Como posso te ajudar? 😊",
                "ola" to "Olá! Como posso te ajudar? 😊",
                "obrigado" to "De nada! Estou aqui para ajudar. 🤖",
                "obrigada" to "De nada! Estou aqui para ajudar. 🤖"
            )

            val respostaTexto = respostas.entries.find {
                mensagemUsuario.text.contains(it.key, ignoreCase = true)
            }?.value ?: "Desculpe, não entendi. Posso ajudar com informações sobre horários, livros, salas ou reservas."

            val respostaBot = ChatMessage(
                id = (System.currentTimeMillis() + 1).toString(),
                text = respostaTexto,
                isUser = false
            )

            mensagens = novasMensagens + respostaBot
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // TOPBAR COM LOGO REAL
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.White)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onVoltarClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.weight(1f))


            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo UNIFOR",
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.weight(1f))


            Spacer(modifier = Modifier.size(40.dp))
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xFF044EE7)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.unibo),
                    contentDescription = "Unibô",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 8.dp)
                )

                Text(
                    text = "Unibô",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }


        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.White)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(mensagens) { mensagem ->
                    MensagemChatUsuario(mensagem = mensagem)
                }
            }
        }


        Divider(
            color = Color.LightGray.copy(alpha = 0.5f),
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = textoMensagem,
                onValueChange = { textoMensagem = it },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp)),
                placeholder = {
                    Text(
                        text = "Digite sua mensagem...",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    disabledContainerColor = Color(0xFFF5F5F5),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        enviarMensagem()
                        keyboardController?.hide()
                    }
                ),
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    enviarMensagem()
                    keyboardController?.hide()
                },
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        Color(0xFF044EE7),
                        CircleShape
                    ),
                enabled = textoMensagem.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Enviar mensagem",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun MensagemChatUsuario(mensagem: ChatMessage) {
    val formatoHora = SimpleDateFormat("HH:mm", Locale.getDefault())
    val hora = formatoHora.format(Date(mensagem.timestamp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {

        if (!mensagem.isUser) {
            Text(
                text = hora,
                color = Color.Gray,
                fontSize = 10.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Start
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (mensagem.isUser) Arrangement.End else Arrangement.Start
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 280.dp)
                    .background(
                        color = if (mensagem.isUser) Color(0xFF044EE7) else Color(0xFFF0F0F0),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = mensagem.text,
                    color = if (mensagem.isUser) Color.White else Color.Black,
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )


                if (mensagem.isUser) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = hora,
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaChatBotUsuarioPreview() {
    TelaChatBotUsuario(
        navController = rememberNavController(),
        onVoltarClick = {}
    )
}