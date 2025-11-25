package com.bibliotecaunifor.model

import com.google.firebase.Timestamp

data class ComunicadoModel(
    val id: String = "",
    val titulo: String = "",
    val mensagem: String = "",
    val autorId: String = "",
    val autorNome: String = "",
    val dataEnvio: Timestamp = Timestamp.now()
)