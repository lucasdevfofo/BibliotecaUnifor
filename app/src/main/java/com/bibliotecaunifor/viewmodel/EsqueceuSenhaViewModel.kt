package com.bibliotecaunifor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class EsqueceuSenhaUiState {
    object Idle : EsqueceuSenhaUiState()
    object Loading : EsqueceuSenhaUiState()
    object Success : EsqueceuSenhaUiState()
    data class Error(val message: String) : EsqueceuSenhaUiState()
}

class EsqueceuSenhaViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<EsqueceuSenhaUiState>(EsqueceuSenhaUiState.Idle)
    val uiState: StateFlow<EsqueceuSenhaUiState> = _uiState.asStateFlow()

    fun enviarEmailRedefinicao(email: String) {
        _uiState.value = EsqueceuSenhaUiState.Loading

        viewModelScope.launch {
            try {
                val result = authRepository.enviarEmailRedefinicaoSenha(email)

                if (result.isSuccess) {
                    _uiState.value = EsqueceuSenhaUiState.Success
                } else {
                    _uiState.value = EsqueceuSenhaUiState.Error(
                        result.exceptionOrNull()?.message ?: "Erro ao enviar email"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = EsqueceuSenhaUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun resetState() {
        _uiState.value = EsqueceuSenhaUiState.Idle
    }
}