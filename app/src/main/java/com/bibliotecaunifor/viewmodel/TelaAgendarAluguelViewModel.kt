package com.bibliotecaunifor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.AluguelModel
import com.bibliotecaunifor.repository.AluguelRepository
import com.bibliotecaunifor.repository.ConfiguracaoRepository
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
    private val configuracaoRepository = ConfiguracaoRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    private val _configuracao = MutableStateFlow<ConfiguracaoState>(ConfiguracaoState.Loading)
    val configuracao: StateFlow<ConfiguracaoState> = _configuracao

    init {
        carregarConfiguracoes()
    }

    private fun carregarConfiguracoes() {
        viewModelScope.launch {
            try {
                val config = configuracaoRepository.buscarConfiguracoes()
                _configuracao.value = ConfiguracaoState.Success(config)
            } catch (e: Exception) {
                _configuracao.value = ConfiguracaoState.Error("Erro ao carregar configurações")
            }
        }
    }

    fun confirmarAluguel(livroId: String, tituloLivro: String, inicioMillis: Long?, fimMillis: Long?) {
        val uid = auth.currentUser?.uid

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

        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {

                val config = configuracaoRepository.buscarConfiguracoes()


                val diferencaMillis = fimMillis - inicioMillis
                val diasDeAluguel = TimeUnit.MILLISECONDS.toDays(diferencaMillis)

                if (diasDeAluguel > config.diasMaximoEmprestimo) {
                    _uiState.value = UiState.Error("O prazo máximo de aluguel é de ${config.diasMaximoEmprestimo} dias.")
                    return@launch
                }


                val alugueisAtivos = aluguelRepository.buscarAlugueisDoUsuario(uid, somenteAtivos = true)
                if (alugueisAtivos.size >= config.limiteLivrosPorUsuario) {
                    _uiState.value = UiState.Error("Você já atingiu o limite de ${config.limiteLivrosPorUsuario} livro(s) alugado(s).")
                    return@launch
                }

                val usuario = usuarioRepository.buscarUsuario(uid)
                val nomeUsuario = usuario?.nomeCompleto ?: "Desconhecido"

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
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Erro ao validar aluguel: ${e.message}")
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
        data class Success(val configuracao: com.bibliotecaunifor.model.ConfiguracaoModel) : ConfiguracaoState()
        data class Error(val message: String) : ConfiguracaoState()
    }
}