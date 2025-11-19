package com.bibliotecaunifor.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.Reserva
import com.bibliotecaunifor.model.UsuarioModel
import com.bibliotecaunifor.repository.ReservaRepository
import com.bibliotecaunifor.repository.UsuarioRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TelaPerfilAlunoViewModel : ViewModel() {

    private val usuarioRepository = UsuarioRepository()
    private val reservaRepository = ReservaRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _usuarioState = MutableStateFlow<UsuarioModel?>(null)
    val usuarioState: StateFlow<UsuarioModel?> = _usuarioState.asStateFlow()

    private val _ultimasReservasState = MutableStateFlow<List<Reserva>>(emptyList())
    val ultimasReservasState: StateFlow<List<Reserva>> = _ultimasReservasState.asStateFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState.asStateFlow()

    var erro by mutableStateOf<String?>(null)
        private set

    fun carregarDados() {
        val uid = auth.currentUser?.uid ?: return
        _loadingState.value = true
        erro = null

        viewModelScope.launch {
            try {
                val usuarioEncontrado = usuarioRepository.buscarUsuario(uid)
                if (usuarioEncontrado != null) {
                    _usuarioState.value = usuarioEncontrado
                }

                val todasReservas = reservaRepository.buscarTodasReservasUsuario(uid)
                val ultimasReservas = todasReservas.take(3)
                _ultimasReservasState.value = ultimasReservas
            } catch (e: Exception) {
                erro = "Erro ao carregar dados: ${e.message}"
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun atualizarDados() {
        carregarDados()
    }
}