package com.bibliotecaunifor.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bibliotecaunifor.model.Reserva
import com.bibliotecaunifor.repository.ReservaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TelaReservaSalaViewModel : ViewModel() {

    private val repo = ReservaRepository()

    var loading by mutableStateOf(false)
    var reservaRealizada by mutableStateOf(false)
    var erro by mutableStateOf<String?>(null)

    fun fazerReserva(reserva: Reserva) {
        loading = true
        erro = null

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Verificar disponibilidade primeiro
                val disponivel = repo.verificarDisponibilidade(
                    reserva.salaId,
                    reserva.data,
                    reserva.horarioInicio,
                    reserva.horarioFim
                )

                if (disponivel) {
                    val resultado = repo.criarReserva(reserva)
                    if (resultado.isSuccess) {
                        reservaRealizada = true
                    } else {
                        erro = "Erro ao criar reserva"
                    }
                } else {
                    erro = "Sala não disponível neste horário"
                }
            } catch (e: Exception) {
                erro = "Erro: ${e.message}"
            } finally {
                loading = false
            }
        }
    }
}