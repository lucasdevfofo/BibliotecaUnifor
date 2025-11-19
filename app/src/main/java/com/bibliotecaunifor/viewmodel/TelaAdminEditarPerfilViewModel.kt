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

class TelaAdminEditarPerfilViewModel : ViewModel() {

    private val repository = UsuarioRepository()
    private val auth = FirebaseAuth.getInstance()

    // Estados da UI
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // Dados do Admin carregados do banco
    private val _adminAtual = MutableStateFlow<UsuarioModel?>(null)
    val adminAtual: StateFlow<UsuarioModel?> = _adminAtual.asStateFlow()

    // Busca os dados assim que a tela abre
    fun carregarDadosAdmin() {
        val uid = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            val usuario = repository.buscarUsuario(uid)
            if (usuario != null) {
                _adminAtual.value = usuario
            }
        }
    }

    // Salva as alterações
    fun salvarPerfilAdmin(usuario: UsuarioModel) {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            _uiState.value = UiState.Error("Sessão inválida. Faça login novamente.")
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading

            // Garante que o tipo continue sendo admin ou mantém o que já estava
            val usuarioParaSalvar = usuario.copy(tipo = "admin")

            val resultado = repository.salvarOuAtualizarUsuario(uid, usuarioParaSalvar)

            if (resultado.isSuccess) {
                _uiState.value = UiState.Success
            } else {
                _uiState.value = UiState.Error(resultado.exceptionOrNull()?.message ?: "Erro ao salvar")
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