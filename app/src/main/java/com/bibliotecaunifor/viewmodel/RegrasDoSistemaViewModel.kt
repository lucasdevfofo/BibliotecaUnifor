package com.bibliotecaunifor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.ConfiguracaoModel
import com.bibliotecaunifor.repository.ConfiguracaoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegrasDoSistemaViewModel : ViewModel() {

    private val configuracaoRepository = ConfiguracaoRepository()

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    private val _configuracao = MutableStateFlow<ConfiguracaoState>(ConfiguracaoState.Loading)
    val configuracao: StateFlow<ConfiguracaoState> = _configuracao

    private var configuracaoAtual: ConfiguracaoModel = ConfiguracaoModel()

    init {
        carregarConfiguracoes()
    }

    private fun carregarConfiguracoes() {
        viewModelScope.launch {
            _configuracao.value = ConfiguracaoState.Loading
            try {
                configuracaoAtual = configuracaoRepository.buscarConfiguracoes()
                _configuracao.value = ConfiguracaoState.Success(configuracaoAtual)
            } catch (e: Exception) {
                _configuracao.value = ConfiguracaoState.Error("Erro ao carregar configurações: ${e.message}")
            }
        }
    }

    fun atualizarLimiteLivros(novoLimite: Int) {
        configuracaoAtual = configuracaoAtual.copy(limiteLivrosPorUsuario = novoLimite)
        _configuracao.value = ConfiguracaoState.Success(configuracaoAtual)
    }

    fun atualizarDiasEmprestimo(novosDias: Int) {
        configuracaoAtual = configuracaoAtual.copy(diasMaximoEmprestimo = novosDias)
        _configuracao.value = ConfiguracaoState.Success(configuracaoAtual)
    }

    fun atualizarLimiteSalas(novoLimite: Int) {
        configuracaoAtual = configuracaoAtual.copy(limiteSalasPorUsuario = novoLimite)
        _configuracao.value = ConfiguracaoState.Success(configuracaoAtual)
    }

    fun salvarConfiguracoes() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val resultado = configuracaoRepository.salvarConfiguracoes(configuracaoAtual)
                if (resultado.isSuccess) {
                    _uiState.value = UiState.Success
                } else {
                    _uiState.value = UiState.Error(resultado.exceptionOrNull()?.message ?: "Erro desconhecido")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Erro ao salvar: ${e.message}")
            }
        }
    }

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }

    sealed class ConfiguracaoState {
        object Loading : ConfiguracaoState()
        data class Success(val configuracao: ConfiguracaoModel) : ConfiguracaoState()
        data class Error(val message: String) : ConfiguracaoState()
    }
}