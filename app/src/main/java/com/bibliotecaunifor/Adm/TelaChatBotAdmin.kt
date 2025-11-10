package com.bibliotecaunifor.Adm

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import java.text.SimpleDateFormat
import java.util.*

data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@Composable
fun TelaChatbot(
    onVoltarClick: () -> Unit
) {
    var mensagens by remember { mutableStateOf<List<ChatMessage>>(emptyList()) }
    var textoMensagem by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current


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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ChatbotTopBarMinimal(
            onVoltarClick = onVoltarClick,
            onNotificacoesClick = { /* abre notificações */ },
            onMenuClick = { /* abre menu */ }
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(mensagens) { mensagem ->
                    MensagemChat(mensagem = mensagem)
                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
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
                        color = Color.Gray
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    disabledContainerColor = Color(0xFFF5F5F5),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        enviarMensagem(
                            textoMensagem,
                            mensagens,
                            { novaLista -> mensagens = novaLista },
                            { textoMensagem = "" }
                        )
                        keyboardController?.hide()
                    }
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    enviarMensagem(
                        textoMensagem,
                        mensagens,
                        { novaLista -> mensagens = novaLista },
                        { textoMensagem = "" }
                    )
                    keyboardController?.hide()
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    ),
                enabled = textoMensagem.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Enviar mensagem",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun MensagemChat(mensagem: ChatMessage) {
    val formatoHora = SimpleDateFormat("HH:mm", Locale.getDefault())
    val hora = formatoHora.format(Date(mensagem.timestamp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (mensagem.isUser) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .background(
                    color = if (mensagem.isUser) MaterialTheme.colorScheme.primary else Color(0xFFF0F0F0),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (mensagem.isUser) 16.dp else 0.dp,
                        bottomEnd = if (mensagem.isUser) 0.dp else 16.dp
                    )
                )
                .padding(12.dp)
        ) {
            Text(
                text = mensagem.text,
                color = if (mensagem.isUser) Color.White else Color.Black,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = hora,
                color = if (mensagem.isUser) Color.White.copy(alpha = 0.7f) else Color.Gray,
                fontSize = 10.sp
            )
        }
    }
}

fun enviarMensagem(
    texto: String,
    mensagensAtuais: List<ChatMessage>,
    onMensagensUpdate: (List<ChatMessage>) -> Unit,
    onClearText: () -> Unit
) {
    if (texto.isBlank()) return


    val mensagemUsuario = ChatMessage(
        id = System.currentTimeMillis().toString(),
        text = texto,
        isUser = true
    )

    val novasMensagens = mensagensAtuais + mensagemUsuario
    onMensagensUpdate(novasMensagens)
    onClearText()}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaChatbotPreview() {
    BibliotecaUniforTheme {
        TelaChatbot(
            onVoltarClick = {}
        )
    }
}
