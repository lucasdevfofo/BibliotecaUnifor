package com.bibliotecaunifor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.ComunicadoModel
import com.bibliotecaunifor.repository.ComunicadoRepository
import com.bibliotecaunifor.repository.UsuarioRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TelaComunicadosViewModel : ViewModel() {

    private val comunicadoRepo = ComunicadoRepository()
    private val usuarioRepo = UsuarioRepository() // Para pegar o nome do Admin
    private val auth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    fun enviarMensagem(mensagem: String) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            _uiState.value = UiState.Error("Usuário não logado")
            return
        }

        if (mensagem.isBlank()) {
            _uiState.value = UiState.Error("A mensagem não pode estar vazia")
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading

            // Busca dados do autor (Admin)
            val admin = usuarioRepo.buscarUsuario(uid)
            val nomeAutor = admin?.nomeCompleto ?: "Administração"

            val novoComunicado = ComunicadoModel(
                titulo = "Comunicado", // Título padrão ou poderia ser um campo extra
                mensagem = mensagem,
                autorId = uid,
                autorNome = nomeAutor,
                dataEnvio = Timestamp.now()
            )

            val resultado = comunicadoRepo.enviarComunicado(novoComunicado)

            if (resultado.isSuccess) {
                _uiState.value = UiState.Success
            } else {
                _uiState.value = UiState.Error("Erro ao enviar comunicado")
            }
        }
    }

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }
}