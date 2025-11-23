package com.bibliotecaunifor.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.AluguelModel
import com.bibliotecaunifor.model.Reserva
import com.bibliotecaunifor.repository.AluguelRepository
import com.bibliotecaunifor.repository.ReservaRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TelaHistoricoReservasViewModel : ViewModel() {

    private val reservaRepository = ReservaRepository()
    private val aluguelRepository = AluguelRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _historicoReservas = MutableStateFlow<List<Reserva>>(emptyList())
    val historicoReservas: StateFlow<List<Reserva>> = _historicoReservas.asStateFlow()

    private val _historicoLivros = MutableStateFlow<List<AluguelModel>>(emptyList())
    val historicoLivros: StateFlow<List<AluguelModel>> = _historicoLivros.asStateFlow()

    var loading by mutableStateOf(false)
        private set

    var erro by mutableStateOf<String?>(null)
        private set

    fun carregarHistoricoReservas() {
        loading = true
        erro = null
        val uid = auth.currentUser?.uid

        if (uid == null) {
            erro = "Usuário não autenticado"
            loading = false
            return
        }

        viewModelScope.launch {
            try {
                // Busca Reservas de Sala
                val reservasEncontradas = reservaRepository.buscarTodasReservasUsuario(uid)
                _historicoReservas.value = reservasEncontradas

                // --- MUDANÇA AQUI: Passa FALSE para ver TUDO (Histórico Completo) ---
                val livrosEncontrados = aluguelRepository.buscarAlugueisDoUsuario(uid, somenteAtivos = false)
                _historicoLivros.value = livrosEncontrados

            } catch (e: Exception) {
                erro = "Erro ao carregar histórico: ${e.message}"
            } finally {
                loading = false
            }
        }
    }
}