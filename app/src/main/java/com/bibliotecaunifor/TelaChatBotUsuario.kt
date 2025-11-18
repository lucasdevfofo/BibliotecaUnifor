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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

// COMENTE ESTAS LINHAS DO GEMINI PARA EVITAR ERROS:
// import com.google.ai.client.generativeai.GenerativeModel

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
    var isLoading by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        mensagens = listOf(
            ChatMessage(
                id = "1",
                text = "Olá! Sou o Unibô, assistente virtual da biblioteca UNIFOR. Como posso ajudar você hoje?",
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
            try {

                delay(1000 + Random.nextLong(500))


                val respostaTexto = getRespostaInteligenteDetalhada(mensagemUsuario.text)

                val respostaBot = ChatMessage(
                    id = (System.currentTimeMillis() + 1).toString(),
                    text = respostaTexto,
                    isUser = false
                )

                mensagens = novasMensagens + respostaBot
            } catch (e: Exception) {

                val respostaBot = ChatMessage(
                    id = (System.currentTimeMillis() + 1).toString(),
                    text = getRespostaInteligenteDetalhada(mensagemUsuario.text),
                    isUser = false
                )
                mensagens = novasMensagens + respostaBot
            } finally {
                isLoading = false
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

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

                if (isLoading) {
                    item {
                        MensagemDigitando()
                    }
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
                enabled = textoMensagem.isNotBlank() && !isLoading
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


private fun getRespostaInteligenteDetalhada(mensagem: String): String {
    val mensagemLower = mensagem.lowercase()
    val random = Random(System.currentTimeMillis())


    return when {

        mensagemLower.contains("disponível") || mensagemLower.contains("aberta") ||
                mensagemLower.contains("aberto") || mensagemLower.contains("funcionando") -> {
            val respostas = listOf(
                "✅ **Sim, estamos abertos agora!** 🎉\n\nHorários hoje:\n• Segunda a sexta: 7h às 20h\n• Sábados: 8h às 12h\n\n📍 Venha nos visitar!",
                "😊 **Com certeza! Biblioteca funcionando!**\n\nEstamos abertos neste momento:\n• Segunda a sexta: 7h-20h\n• Sábado: 8h-12h\n\nTe esperamos!",
                "🌟 **Sim, estamos disponíveis!**\n\nFuncionamento:\n• Segunda a sexta: 7h às 20h\n• Sábados: 8h às 12h\n\n📞 Dúvidas? (85) 3477-3000"
            )
            respostas[random.nextInt(respostas.size)]
        }


        mensagemLower.contains("horario") || mensagemLower.contains("funciona") ||
                mensagemLower.contains("que horas") || mensagemLower.contains("quando abre") -> {
            val respostas = listOf(
                "🕐 **Horários da Biblioteca:**\n\n• Segunda a sexta: 7h às 20h\n• Sábados: 8h às 12h\n• Domingos: Fechada\n\n📍 Bloco A, 2º andar",
                "📅 **Funcionamento:**\n\n• Segunda a sexta: 7h-20h\n• Sábados: 8h-12h\n• Domingos: Fechado\n\n🚨 Atenção: Fechamos em feriados",
                "⏰ **Nosso horário:**\n\n• Segunda a sexta: 7h às 20h\n• Sábados: 8h às 12h\n• Domingos: Não funcionamos\n\n💡 Dica: Venha nos horários de menor movimento!"
            )
            respostas[random.nextInt(respostas.size)]
        }


        mensagemLower.contains("livro") || mensagemLower.contains("emprestar") ||
                mensagemLower.contains("pegar livro") || mensagemLower.contains("empréstimo") -> {
            val respostas = listOf(
                "📚 **Sistema de Empréstimo:**\n\n⏱️ **Prazos:**\n• Livros: 15 dias\n• Revistas: 7 dias\n\n🔄 **Renovação:**\n• Pelo app ou presencial\n• Mais 15 dias\n\n📱 **Como:** App → 'Acervo' → Reservar",
                "📖 **Para pegar livros:**\n\n• Prazo: 15 dias\n• Limite: 5 livros\n• Renovação: +15 dias\n• Reserva: Pelo app\n\n🎯 **Dica:** Renove antes do vencimento!",
                "🔖 **Empréstimo de livros:**\n\n• Duração: 15 dias\n• Máximo: 5 livros\n• Renovação: Disponível\n• Multa: R$ 2,00/dia\n\n💡 Traga sua carteira estudantil!"
            )
            respostas[random.nextInt(respostas.size)]
        }


        mensagemLower.contains("sala") || mensagemLower.contains("estudar") ||
                mensagemLower.contains("reservar sala") || mensagemLower.contains("grupo estudo") -> {
            val respostas = listOf(
                "🏫 **Salas de Estudo:**\n\n🎯 **Tipos:**\n• Individuais (10)\n• Duplas (5)\n• Grupos (6 pessoas)\n\n⏰ **Regras:**\n• Máx. 3 horas/dia\n• Chegar 15min antes\n\n📱 **Reserva:** App → 'Salas'",
                "📚 **Salas disponíveis:**\n\n• Individuais: 10 unidades\n• Grupos: 5 salas\n• Capacidade: 2-6 pessoas\n\n⏱️ **Horário:**\n• Máximo 3h/dia\n• Reserva antecipada\n\n🚀 **Como reservar:** No app!",
                "💻 **Salas para estudo:**\n\n• Individuais: 10\n• Coletivas: 5\n• Silenciosas: Todas!\n\n📅 **Reserva:**\n• Pelo app UNIFOR\n• Máx. 3 horas\n• Chegar no horário\n\n🎯 Perfeitas para provas!"
            )
            respostas[random.nextInt(respostas.size)]
        }


        mensagemLower.contains("multa") || mensagemLower.contains("atraso") ||
                mensagemLower.contains("devolver") || mensagemLower.contains("atrasei") -> {
            val respostas = listOf(
                "💰 **Sistema de Multas:**\n\n📊 **Valores:**\n• Livros: R$ 2,00/dia\n• Salas: R$ 5,00/hora\n\n🚫 **Consequências:**\n• Bloqueio acima de R$ 20,00\n• Sem novos empréstimos\n\n💳 **Pagamento:** Balcão",
                "⚠️ **Multas por atraso:**\n\n• Livros: R$ 2,00 por dia\n• Salas: R$ 5,00 por hora\n• Bloqueio: Acima de R$ 20,00\n\n💡 **Dica:** Renove a tempo!",
                "💸 **Informações sobre multas:**\n\n• Livros: R$ 2,00/dia\n• Salas: R$ 5,00/hora atraso\n• Pagamento: No balcão\n• Bloqueio: R$ 20,00+\n\n🎯 Evite multas, renove!"
            )
            respostas[random.nextInt(respostas.size)]
        }


        mensagemLower.contains("wifi") || mensagemLower.contains("internet") ||
                mensagemLower.contains("rede") || mensagemLower.contains("conectar") -> {
            val respostas = listOf(
                "📶 **Wi-Fi Biblioteca:**\n\n🌐 **Conexão:**\n• Rede: UNIFOR_Biblioteca\n• Senha: estudar2024\n• Velocidade: 100MB\n\n💻 **Para:** Celulares, notebooks",
                "🛜 **Internet disponível:**\n\n• Rede: UNIFOR_Biblioteca\n• Senha: estudar2024\n• Cobertura: Todo prédio\n• Uso: Educacional\n\n🚀 Conecte-se e estude!",
                "📡 **Wi-Fi da biblioteca:**\n\n• Nome: UNIFOR_Biblioteca\n• Senha: estudar2024\n• Área: Toda biblioteca\n• Velocidade: Rápida\n\n💡 Perfeito para pesquisas!"
            )
            respostas[random.nextInt(respostas.size)]
        }


        mensagemLower.contains("acervo") || mensagemLower.contains("pesquisar") ||
                mensagemLower.contains("encontrar") || mensagemLower.contains("procuro") -> {
            val respostas = listOf(
                "🔍 **Acervo UNIFOR:**\n\n📊 **Estatísticas:**\n• 50.000+ livros\n• 100+ revistas\n• 5.000+ trabalhos\n\n🎯 **Busca:**\n• Por título/autor\n• Palavras-chave\n• Filtros por área\n\n📱 **Acesso:** App ou site",
                "📚 **Nosso acervo:**\n\n• Livros: 50.000+\n• Revistas: 100 títulos\n• Teses: 5.000+\n• Digital: 500+\n\n🔎 **Como pesquisar:**\nApp → Buscar → Filtrar\n\n💡 Use palavras específicas!",
                "🏛️ **Acervo da biblioteca:**\n\n• Livros: +50 mil\n• Periódicos: +100\n• Trabalhos acadêmicos\n• Mídia digital\n\n🎯 **Pesquisa:**\nNo app ou site oficial\n\n🚀 Encontre seu material!"
            )
            respostas[random.nextInt(respostas.size)]
        }


        mensagemLower.contains("estudar") || mensagemLower.contains("prova") ||
                mensagemLower.contains("exame") || mensagemLower.contains("dica") -> {
            val respostas = listOf(
                "🎯 **Dicas de Estudo:**\n\n💡 **Técnicas:**\n• Pomodoro: 25min foco\n• Revisão espaçada\n• Mapas mentais\n\n🏫 **Espaços:**\n• Salas silenciosas\n• Área colaborativa\n• Cabines individuais\n\n📚 **Recursos:** Livros de metodologia",
                "🚀 **Para estudar melhor:**\n\n• Técnica Pomodoro\n• Ambiente silencioso\n• Revisões constantes\n• Grupos de estudo\n\n📍 **Na biblioteca:**\nSalas individuais e coletivas!\n\n💡 Reserve pelo app!",
                "📖 **Dicas acadêmicas:**\n\n• Estude em blocos de 25min\n• Faça pausas regulares\n• Use nossas salas silenciosas\n• Consulte o acervo\n\n🎯 **Na UNIFOR:** Temos espaços perfeitos!"
            )
            respostas[random.nextInt(respostas.size)]
        }


        mensagemLower.contains("oi") || mensagemLower.contains("olá") ||
                mensagemLower.contains("ola") || mensagemLower.contains("bom dia") ||
                mensagemLower.contains("boa tarde") || mensagemLower.contains("boa noite") -> {
            val respostas = listOf(
                "👋 **Olá! Sou o Unibô!** 🤖\n\nSou o assistente da **Biblioteca UNIFOR**!\n\nPosso ajudar com:\n• 📚 Horários e localização\n• 📖 Empréstimo de livros\n• 🏫 Reserva de salas\n• 💰 Informações sobre multas\n• 📶 Wi-Fi e serviços\n\nEm que posso te ajudar? 😊",
                "😊 **Oi! Tudo bem?**\n\nEu sou o **Unibô**, seu assistente virtual da Biblioteca UNIFOR! 🤖\n\nPosso te ajudar com:\n• Horários de funcionamento\n• Empréstimo de livros\n• Reserva de salas de estudo\n• Informações sobre multas\n• Wi-Fi e muito mais!\n\nO que você gostaria de saber?",
                "🌟 **Olá! Seja bem-vindo(a)!**\n\nEu sou o **Unibô** 🤖, assistente da Biblioteca UNIFOR!\n\n🎯 **Posso te auxiliar com:**\n• Funcionamento e horários\n• Sistema de empréstimos\n• Reserva de espaços\n• Dúvidas sobre multas\n• Acesso ao acervo\n\nEm que posso ajudar hoje? 😄"
            )
            respostas[random.nextInt(respostas.size)]
        }


        mensagemLower.contains("obrigado") || mensagemLower.contains("obrigada") ||
                mensagemLower.contains("valeu") || mensagemLower.contains("agradeço") -> {
            val respostas = listOf(
                "😊 **De nada! Fico feliz em ajudar!**\n\nPrecisa de mais alguma informação sobre a biblioteca?\n\nEstou aqui sempre que precisar! 🤖✨",
                "🌟 **Imagina! Às ordens!**\n\nFico contente em poder ajudar! Se tiver mais dúvidas sobre a biblioteca, é só perguntar!\n\nEstou aqui para isso! 😄",
                "💙 **Por nada! Sempre às ordens!**\n\nQue bom que pude ajudar! Se precisar de mais informações sobre nossos serviços, é só chamar!\n\nAté a próxima! 🤖🎉"
            )
            respostas[random.nextInt(respostas.size)]
        }


        else -> {
            val respostas = listOf(
                "🤔 **Sobre a Biblioteca UNIFOR, posso te informar:**\n\n" +
                        "📅 **Funcionamento:** Seg-Sex (7h-20h), Sáb (8h-12h)\n" +
                        "📚 **Livros:** Empréstimo de 15 dias, renove pelo app\n" +
                        "🏫 **Salas:** Reserve no app em 'Salas Disponíveis'\n" +
                        "💰 **Multas:** R$ 2,00/dia livros, R$ 5,00/hora salas\n" +
                        "📶 **Wi-Fi:** UNIFOR_Biblioteca (senha: estudar2024)\n\n" +
                        "Quer saber mais sobre algum desses tópicos? 😊",

                "🎯 **Posso te ajudar com estas informações da Biblioteca UNIFOR:**\n\n" +
                        "• ⏰ Horários: Segunda a sexta (7h-20h)\n" +
                        "• 📖 Livros: 15 dias de empréstimo\n" +
                        "• 🏫 Salas: Reserva pelo app\n" +
                        "• 💰 Multas: R$ 2,00/dia atraso\n" +
                        "• 📶 Internet: Wi-Fi gratuito\n\n" +
                        "Sobre qual assunto gostaria de saber mais? 🤖",

                "💡 **Na Biblioteca UNIFOR temos:**\n\n" +
                        "📅 Funcionamento: Segunda a sexta, 7h-20h\n" +
                        "📚 Acervo: +50.000 livros disponíveis\n" +
                        "🏫 Salas: Individuais e para grupos\n" +
                        "💰 Sistema: Multas por atraso\n" +
                        "📶 Conectividade: Wi-Fi em toda área\n\n" +
                        "Pergunte sobre qualquer um desses serviços! 😄"
            )
            respostas[random.nextInt(respostas.size)]
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

@Composable
fun MensagemDigitando() {
    val formatoHora = SimpleDateFormat("HH:mm", Locale.getDefault())
    val hora = formatoHora.format(Date(System.currentTimeMillis()))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = hora,
            color = Color.Gray,
            fontSize = 10.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 2.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Start
        )

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
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Digitando",
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "...",
                        color = Color.Black,
                        fontSize = 14.sp
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