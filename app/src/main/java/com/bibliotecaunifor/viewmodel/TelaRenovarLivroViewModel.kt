package com.bibliotecaunifor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.repository.AluguelRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.concurrent.TimeUnit

class TelaRenovarLivroViewModel : ViewModel() {

    private val aluguelRepository = AluguelRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    fun confirmarRenovacao(aluguelId: String, dataDevolucaoAtualMillis: Long, novaDataDevolucaoMillis: Long?) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            _uiState.value = UiState.Error("Usuário não logado")
            return
        }

        if (novaDataDevolucaoMillis == null) {
            _uiState.value = UiState.Error("Selecione a nova data de devolução.")
            return
        }

        // Adicionamos 1 dia (24h) à data atual para ser o início da renovação
        val umDiaMillis = 24L * 60 * 60 * 1000
        val inicioRenovacaoMillis = dataDevolucaoAtualMillis + umDiaMillis

        if (novaDataDevolucaoMillis < inicioRenovacaoMillis) {
            _uiState.value = UiState.Error("A data deve ser posterior ao vencimento atual.")
            return
        }

        val diferencaMillis = novaDataDevolucaoMillis - inicioRenovacaoMillis
        val diasRenovados = TimeUnit.MILLISECONDS.toDays(diferencaMillis)

        if (diasRenovados > 8) {
            _uiState.value = UiState.Error("A renovação não pode exceder 8 dias.")
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val novaDataTimestamp = Timestamp(Date(novaDataDevolucaoMillis))
            val resultado = aluguelRepository.renovarAluguel(aluguelId, novaDataTimestamp)

            if (resultado.isSuccess) {
                _uiState.value = UiState.Success
            } else {
                _uiState.value = UiState.Error(resultado.exceptionOrNull()?.message ?: "Erro ao renovar")
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