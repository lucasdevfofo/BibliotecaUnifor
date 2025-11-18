package com.bibliotecaunifor.Adm

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
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
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
import com.bibliotecaunifor.R
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@Composable
fun TelaChatBotAdmin(
    onVoltarClick: () -> Unit
) {
    var mensagens by remember { mutableStateOf<List<ChatMessage>>(emptyList()) }
    var textoMensagem by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        mensagens = listOf(
            ChatMessage(
                id = "1",
                text = "👋 **Olá, Administrador!** Sou o Unibô Admin. Posso ajudar com relatórios, usuários, acervo e configurações do sistema.",
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
        if (textoMensagem.isBlank() || isLoading) return

        val mensagemUsuario = ChatMessage(
            id = System.currentTimeMillis().toString(),
            text = textoMensagem,
            isUser = true
        )

        val novasMensagens = mensagens + mensagemUsuario
        mensagens = novasMensagens
        textoMensagem = ""
        isLoading = true

        coroutineScope.launch {
            delay(800 + Random.nextLong(400))
            val respostaTexto = getRespostaAdminInteligente(mensagemUsuario.text)
            val respostaBot = ChatMessage(
                id = (System.currentTimeMillis() + 1).toString(),
                text = respostaTexto,
                isUser = false
            )
            mensagens = novasMensagens + respostaBot
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        ChatbotTopBarAdmin(
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

                if (isLoading) {
                    item {
                        MensagemDigitando()
                    }
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
                        text = "Digite comando administrativo...",
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
                        enviarMensagem()
                        keyboardController?.hide()
                    }
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    enviarMensagem()
                    keyboardController?.hide()
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    ),
                enabled = textoMensagem.isNotBlank() && !isLoading
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

@Composable
fun MensagemDigitando() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 120.dp)
                .background(
                    color = Color(0xFFF0F0F0),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = "Digitando...",
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}

private fun getRespostaAdminInteligente(mensagem: String): String {
    val mensagemLower = mensagem.lowercase()
    val random = Random(System.currentTimeMillis())

    return when {
        mensagemLower.contains("relatório") || mensagemLower.contains("estatística") -> {
            val respostas = listOf(
                "📊 **Relatórios:**\n• 1.245 visitas este mês\n• Livros mais emprestados: Programação (45x), Cálculo (38x)\n• Salas: 89% ocupação\n• Horário de pico: 14h-16h",
                "📈 **Estatísticas:**\n• Usuários ativos: 2.847\n• Acervo total: 50.328 livros\n• Reservas este mês: 156"
            )
            respostas[random.nextInt(respostas.size)]
        }

        mensagemLower.contains("usuário") || mensagemLower.contains("aluno") -> {
            val respostas = listOf(
                "👥 **Usuários:**\n• Total: 8.452\n• Ativos: 2.847\n• Bloqueados: 18",
                "🎓 **Controle de Acessos:**\n• Alunos: 7.892\n• Professores: 485\n• Com pendências: 1.8%"
            )
            respostas[random.nextInt(respostas.size)]
        }

        mensagemLower.contains("livro") || mensagemLower.contains("acervo") -> {
            val respostas = listOf(
                "📚 **Acervo:**\n• Total: 50.328 livros\n• Em empréstimo: 3.847\n• Disponíveis: 45.123\n• Em manutenção: 358",
                "🏛️ **Controle:**\n• Exatas: 15.234\n• Humanas: 18.456\n• Biológicas: 12.678\n• Artes: 3.960"
            )
            respostas[random.nextInt(respostas.size)]
        }

        mensagemLower.contains("reserva") || mensagemLower.contains("sala") -> {
            val respostas = listOf(
                "🏫 **Reservas Hoje:**\n• Confirmadas: 45\n• Canceladas: 2\n• Em andamento: 23\n• Salas mais procuradas: A12, B07",
                "📅 **Agendamentos:**\n• Salas ocupadas: 18\n• Livres: 12\n• Taxa de ocupação: 72%"
            )
            respostas[random.nextInt(respostas.size)]
        }

        mensagemLower.contains("configuração") || mensagemLower.contains("sistema") -> {
            val respostas = listOf(
                "⚙️ **Sistema:**\n• Backup: Ativo\n• Uptime: 99.8%\n• Última atualização: 2 dias\n• Próxima manutenção: 15/12",
                "🛠️ **Configurações:**\n• Segurança: 2FA Ativo\n• Logs: 30 dias\n• Disponibilidade: 99.9%\n• Storage: 68%"
            )
            respostas[random.nextInt(respostas.size)]
        }

        else -> {
            val respostas = listOf(
                "🛡️ **Comandos disponíveis:**\n• relatórios\n• usuários\n• acervo\n• reservas\n• configurações",
                "🎯 **Posso ajudar com:**\n• Relatórios e estatísticas\n• Gestão de usuários\n• Controle do acervo\n• Administração de reservas\n• Configurações do sistema"
            )
            respostas[random.nextInt(respostas.size)]
        }
    }
}

@Composable
fun ChatbotTopBarAdmin(
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(
                color = Color(0xFF3F4F78),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
    ) {

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
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.Black,
                modifier = Modifier.size(30.dp)
            )
        }


        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo UNIFOR",
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.TopCenter)
                .offset(y = 8.dp)
        )


        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp, top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(
                onClick = onNotificacoesClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notificações",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(
                onClick = onMenuClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "Menu",
                    tint = Color.Black,
                    modifier = Modifier.size(30.dp)
                )
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            Image(
                painter = painterResource(id = R.drawable.unibo),
                contentDescription = "Unibô",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Unibô Admin",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaChatBotAdminPreview() {
    BibliotecaUniforTheme {
        TelaChatBotAdmin(
            onVoltarClick = {}
        )
    }
}