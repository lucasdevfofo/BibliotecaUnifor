package com.bibliotecaunifor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.Sala
import com.bibliotecaunifor.repository.SalaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TelaAdminGerenciarSalasViewModel(
    private val salaRepository: SalaRepository
) : ViewModel() {

    private val _salas = MutableStateFlow<List<Sala>>(emptyList())
    val salas: StateFlow<List<Sala>> = _salas.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _mensagem = MutableStateFlow<String?>(null)
    val mensagem: StateFlow<String?> = _mensagem.asStateFlow()

    init {
        carregarSalas()
    }

    fun carregarSalas() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val salasCarregadas = salaRepository.getTodasSalas()
                _salas.value = salasCarregadas
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Erro ao carregar salas: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun atualizarSala(sala: Sala) {
        viewModelScope.launch {
            try {
                val sucesso = salaRepository.atualizarSala(sala)
                if (sucesso) {
                    _mensagem.value = "Sala atualizada com sucesso!"
                    carregarSalas()
                } else {
                    _error.value = "Erro ao atualizar sala"
                }
            } catch (e: Exception) {
                _error.value = "Erro: ${e.message}"
            }
        }
    }

    fun excluirSala(salaId: String) {
        viewModelScope.launch {
            try {
                val sucesso = salaRepository.excluirSala(salaId)
                if (sucesso) {
                    _mensagem.value = "Sala excluída com sucesso!"
                    carregarSalas()
                } else {
                    _error.value = "Erro ao excluir sala"
                }
            } catch (e: Exception) {
                _error.value = "Erro: ${e.message}"
            }
        }
    }

    fun limparErro() {
        _error.value = null
    }

    fun limparMensagem() {
        _mensagem.value = null
    }
}