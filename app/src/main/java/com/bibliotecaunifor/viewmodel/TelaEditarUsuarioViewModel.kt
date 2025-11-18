package com.bibliotecaunifor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.UsuarioModel
import com.bibliotecaunifor.repository.UsuarioRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TelaEditarUsuarioViewModel : ViewModel() {

    private val repository = UsuarioRepository()
    private val auth = FirebaseAuth.getInstance()

    // Estado da AÇÃO (Salvar/Carregar)
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // NOVO: Estado dos DADOS do usuário (para preencher a tela)
    private val _usuarioAtual = MutableStateFlow<UsuarioModel?>(null)
    val usuarioAtual: StateFlow<UsuarioModel?> = _usuarioAtual.asStateFlow()

    // NOVO: Função que busca os dados ao abrir a tela
    fun carregarDadosUsuario() {
        val uid = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            // Avisa que está carregando (opcional, se quiser mostrar spinner ao abrir)
            // _uiState.value = UiState.Loading

            val usuario = repository.buscarUsuario(uid)
            if (usuario != null) {
                _usuarioAtual.value = usuario
            }
        }
    }

    fun salvarAlteracoes(usuario: UsuarioModel) {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            _uiState.value = UiState.Error("Usuário não está logado!")
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val resultado = repository.salvarOuAtualizarUsuario(uid, usuario)

            if (resultado.isSuccess) {
                _uiState.value = UiState.Success
            } else {
                _uiState.value = UiState.Error(resultado.exceptionOrNull()?.message ?: "Erro desconhecido")
            }
        }
    }

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val mensagem: String) : UiState()
    }
}