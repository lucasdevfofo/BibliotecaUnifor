package com.bibliotecaunifor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.AluguelModel
import com.bibliotecaunifor.repository.AluguelRepository
import com.bibliotecaunifor.repository.UsuarioRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TelaDescricaoViewModel : ViewModel() {

    private val aluguelRepository = AluguelRepository()
    private val usuarioRepository = UsuarioRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    fun alugarLivro(livroId: String, tituloLivro: String) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            _uiState.value = UiState.Error("Usuário não logado")
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading

            // Busca dados do usuário para salvar o nome no aluguel (opcional mas bom)
            val usuario = usuarioRepository.buscarUsuario(uid)
            val nomeUsuario = usuario?.nomeCompleto ?: "Desconhecido"

            val novoAluguel = AluguelModel(
                livroId = livroId,
                tituloLivro = tituloLivro,
                usuarioId = uid,
                nomeUsuario = nomeUsuario
            )

            val resultado = aluguelRepository.realizarAluguel(novoAluguel)

            if (resultado.isSuccess) {
                _uiState.value = UiState.Success
            } else {
                _uiState.value = UiState.Error(resultado.exceptionOrNull()?.message ?: "Erro ao alugar")
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