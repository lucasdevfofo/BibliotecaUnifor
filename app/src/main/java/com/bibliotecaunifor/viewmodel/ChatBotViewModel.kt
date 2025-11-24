package com.bibliotecaunifor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.ChatMessage
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatBotViewModel : ViewModel() {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = "AIzaSyAfGWiFSwJIZqOCKf8ybOGmUhuomjnOJ7U"
    )

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        _chatMessages.value = listOf(
            ChatMessage(
                id = "1",
                text = "Olá! Sou o Unibô, assistente virtual da biblioteca UNIFOR. Como posso ajudar você hoje?",
                isUser = false
            )
        )
    }

    fun sendMessage(userMessage: String) {
        if (userMessage.isBlank() || _isLoading.value) return

        val userChatMessage = ChatMessage(
            id = System.currentTimeMillis().toString(),
            text = userMessage,
            isUser = true
        )

        _chatMessages.value = _chatMessages.value + userChatMessage
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = generativeModel.generateContent(userMessage)

                val botMessage = response.text ?: "Não consegui processar sua mensagem."

                val botChatMessage = ChatMessage(
                    id = (System.currentTimeMillis() + 1).toString(),
                    text = botMessage,
                    isUser = false
                )

                _chatMessages.value = _chatMessages.value + botChatMessage
            } catch (e: Exception) {
                val errorMessage = ChatMessage(
                    id = (System.currentTimeMillis() + 1).toString(),
                    text = "Erro: ${e.message}",
                    isUser = false
                )
                _chatMessages.value = _chatMessages.value + errorMessage

                val fallbackResponse = getFallbackResponse(userMessage)
                val fallbackMessage = ChatMessage(
                    id = (System.currentTimeMillis() + 2).toString(),
                    text = fallbackResponse,
                    isUser = false
                )
                _chatMessages.value = _chatMessages.value + fallbackMessage
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getFallbackResponse(message: String): String {
        val lowerMessage = message.lowercase()
        return when {
            lowerMessage.contains("horario") || lowerMessage.contains("funciona") ->
                "🕐 **Horários da Biblioteca UNIFOR:**\n\n• Segunda a sexta: 7h às 20h\n• Sábados: 8h às 12h\n• Domingos: Fechada\n\n📍 Bloco A, 2º andar"

            lowerMessage.contains("livro") || lowerMessage.contains("emprestar") ->
                "📚 **Empréstimo de Livros:**\n\n• Prazo: 15 dias\n• Limite: 5 livros\n• Renovação: +15 dias pelo app\n• Multa: R$ 2,00/dia de atraso"

            lowerMessage.contains("sala") || lowerMessage.contains("reservar") ->
                "🏫 **Reserva de Salas:**\n\n• Pelo app UNIFOR\n• Máximo: 3 horas/dia\n• Salas individuais e coletivas\n• Chegar 15min antes do horário"

            lowerMessage.contains("wifi") || lowerMessage.contains("internet") ->
                "📶 **Wi-Fi Biblioteca:**\n\n• Rede: UNIFOR_Biblioteca\n• Senha: estudar2024\n• Cobertura: Todo o prédio"

            lowerMessage.contains("acervo") || lowerMessage.contains("pesquisar") ->
                "🔍 **Acervo UNIFOR:**\n\n• 50.000+ livros\n• 100+ revistas científicas\n• 5.000+ trabalhos acadêmicos\n• Busca por título/autor no app"

            lowerMessage.contains("multa") || lowerMessage.contains("atraso") ->
                "💰 **Sistema de Multas:**\n\n• Livros: R$ 2,00/dia\n• Salas: R$ 5,00/hora\n• Bloqueio acima de R$ 20,00\n• Pagamento no balcão"

            lowerMessage.contains("oi") || lowerMessage.contains("olá") || lowerMessage.contains("ola") ->
                "👋 **Olá! Sou o Unibô!** 🤖\n\nSou o assistente da **Biblioteca UNIFOR**!\n\nPosso ajudar com:\n• 📚 Horários e localização\n• 📖 Empréstimo de livros\n• 🏫 Reserva de salas\n• 💰 Informações sobre multas\n• 📶 Wi-Fi e serviços\n\nEm que posso te ajudar? 😊"

            else ->
                "🤔 **Sobre a Biblioteca UNIFOR, posso te informar:**\n\n" +
                        "📅 **Funcionamento:** Seg-Sex (7h-20h), Sáb (8h-12h)\n" +
                        "📚 **Livros:** Empréstimo de 15 dias, renove pelo app\n" +
                        "🏫 **Salas:** Reserve no app em 'Salas Disponíveis'\n" +
                        "💰 **Multas:** R$ 2,00/dia livros, R$ 5,00/hora salas\n" +
                        "📶 **Wi-Fi:** UNIFOR_Biblioteca (senha: estudar2024)\n\n" +
                        "Quer saber mais sobre algum desses tópicos? 😊"
        }
    }
}