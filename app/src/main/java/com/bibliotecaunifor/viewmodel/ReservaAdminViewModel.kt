package com.bibliotecaunifor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.Reserva
import com.bibliotecaunifor.repository.ReservaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReservaAdminViewModel : ViewModel() {

    private val reservaRepository = ReservaRepository()

    private val _reservas = MutableStateFlow<List<Reserva>>(emptyList())
    val reservas: StateFlow<List<Reserva>> = _reservas.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _mensagem = MutableStateFlow<String?>(null)
    val mensagem: StateFlow<String?> = _mensagem.asStateFlow()

    init {
        carregarTodasReservas()
    }

    fun carregarTodasReservas() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val reservasList = reservaRepository.getTodasReservas()
                _reservas.value = reservasList
            } catch (e: Exception) {
                _mensagem.value = "Erro ao carregar reservas: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun limparMensagem() {
        _mensagem.value = null
    }
}