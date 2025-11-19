package com.bibliotecaunifor.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.Reserva
import com.bibliotecaunifor.repository.ReservaRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class TelaHistoricoReservasViewModel : ViewModel() {

    private val reservaRepository = ReservaRepository()
    private val auth = FirebaseAuth.getInstance()

    var historicoReservas by mutableStateOf<List<Reserva>>(emptyList())
        private set

    var loading by mutableStateOf(false)
        private set

    var erro by mutableStateOf<String?>(null)
        private set

    fun carregarHistoricoReservas() {
        loading = true
        erro = null

        viewModelScope.launch {
            try {
                val usuarioId = auth.currentUser?.uid ?: ""
                if (usuarioId.isNotEmpty()) {
                    historicoReservas = reservaRepository.buscarTodasReservasUsuario(usuarioId)
                } else {
                    erro = "Usuário não autenticado"
                }
            } catch (e: Exception) {
                erro = "Erro ao carregar histórico: ${e.message}"
            } finally {
                loading = false
            }
        }
    }
}