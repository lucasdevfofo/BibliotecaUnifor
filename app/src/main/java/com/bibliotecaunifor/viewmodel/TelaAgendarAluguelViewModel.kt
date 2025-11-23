package com.bibliotecaunifor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.AluguelModel
import com.bibliotecaunifor.repository.AluguelRepository
import com.bibliotecaunifor.repository.UsuarioRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.concurrent.TimeUnit
class TelaAgendarAluguelViewModel : ViewModel() {

    private val aluguelRepository = AluguelRepository()
    private val usuarioRepository = UsuarioRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    // Recebe as datas em milissegundos (Long) vindas do DatePicker
    fun confirmarAluguel(livroId: String, tituloLivro: String, inicioMillis: Long?, fimMillis: Long?) {
        val uid = auth.currentUser?.uid

        // 1. Verificações básicas (já existiam)
        if (uid == null) {
            _uiState.value = UiState.Error("Usuário não logado")
            return
        }

        if (inicioMillis == null || fimMillis == null) {
            _uiState.value = UiState.Error("Selecione as datas de retirada e devolução.")
            return
        }

        if (fimMillis < inicioMillis) {
            _uiState.value = UiState.Error("A data de devolução não pode ser antes da retirada.")
            return
        }

        // --- NOVA VALIDAÇÃO: MÁXIMO 8 DIAS ---
        val diferencaMillis = fimMillis - inicioMillis
        val diasDeAluguel = TimeUnit.MILLISECONDS.toDays(diferencaMillis)

        if (diasDeAluguel > 8) {
            _uiState.value = UiState.Error("O prazo máximo de aluguel é de 8 dias.")
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val usuario = usuarioRepository.buscarUsuario(uid)
            val nomeUsuario = usuario?.nomeCompleto ?: "Desconhecido"

            // Cria o objeto com as datas personalizadas
            val novoAluguel = AluguelModel(
                livroId = livroId,
                tituloLivro = tituloLivro,
                usuarioId = uid,
                nomeUsuario = nomeUsuario,
                dataAluguel = Timestamp(Date(inicioMillis)),
                dataDevolucaoPrevista = Timestamp(Date(fimMillis)),
                status = "ativo"
            )

            val resultado = aluguelRepository.realizarAluguel(novoAluguel)

            if (resultado.isSuccess) {
                _uiState.value = UiState.Success
            } else {
                _uiState.value = UiState.Error(resultado.exceptionOrNull()?.message ?: "Erro ao agendar")
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