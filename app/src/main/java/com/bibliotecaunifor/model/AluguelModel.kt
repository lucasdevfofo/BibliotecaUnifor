package com.bibliotecaunifor.model

import com.google.firebase.Timestamp
import java.util.Date

data class AluguelModel(
    val id: String = "",
    val livroId: String = "",
    val tituloLivro: String = "",
    val usuarioId: String = "",
    val nomeUsuario: String = "", // Opcional, ajuda na listagem do admin
    val dataAluguel: Timestamp = Timestamp.now(),
    val dataDevolucaoPrevista: Timestamp = Timestamp(Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000)), // +7 dias
    val status: String = "ativo",

    val renovado: Boolean = false
)