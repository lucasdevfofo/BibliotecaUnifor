package com.bibliotecaunifor.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.AluguelModel
import com.bibliotecaunifor.model.ConfiguracaoModel
import com.bibliotecaunifor.model.Reserva
import com.bibliotecaunifor.model.UsuarioModel
import com.bibliotecaunifor.repository.AluguelRepository
import com.bibliotecaunifor.repository.ConfiguracaoRepository
import com.bibliotecaunifor.repository.ReservaRepository
import com.bibliotecaunifor.repository.UsuarioRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TelaPerfilAlunoViewModel : ViewModel() {

    private val usuarioRepository = UsuarioRepository()
    private val reservaRepository = ReservaRepository()
    private val aluguelRepository = AluguelRepository()
    private val configuracaoRepository = ConfiguracaoRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _usuarioState = MutableStateFlow<UsuarioModel?>(null)
    val usuarioState: StateFlow<UsuarioModel?> = _usuarioState.asStateFlow()

    private val _ultimasReservasState = MutableStateFlow<List<Reserva>>(emptyList())
    val ultimasReservasState: StateFlow<List<Reserva>> = _ultimasReservasState.asStateFlow()

    private val _livrosAlugadosState = MutableStateFlow<List<AluguelModel>>(emptyList())
    val livrosAlugadosState: StateFlow<List<AluguelModel>> = _livrosAlugadosState.asStateFlow()

    private val _reservasAtivasState = MutableStateFlow<List<Reserva>>(emptyList())
    val reservasAtivasState: StateFlow<List<Reserva>> = _reservasAtivasState.asStateFlow()

    private val _configuracaoState = MutableStateFlow<ConfiguracaoModel?>(null)
    val configuracaoState: StateFlow<ConfiguracaoModel?> = _configuracaoState.asStateFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState.asStateFlow()

    var erro by mutableStateOf<String?>(null)
        private set

    fun carregarDados() {
        val uid = auth.currentUser?.uid ?: return
        _loadingState.value = true
        erro = null

        viewModelScope.launch {
            try {
                val usuarioEncontrado = usuarioRepository.buscarUsuario(uid)
                if (usuarioEncontrado != null) {
                    _usuarioState.value = usuarioEncontrado
                }

                val config = configuracaoRepository.buscarConfiguracoes()
                _configuracaoState.value = config

                val todasReservas = reservaRepository.buscarTodasReservasUsuario(uid)

                val reservasAtivas = todasReservas.filter {
                    it.status == "pendente" || it.status == "confirmada"
                }
                _reservasAtivasState.value = reservasAtivas

                val ultimasReservas = todasReservas.take(3)
                _ultimasReservasState.value = ultimasReservas

                val alugueisEncontrados = aluguelRepository.buscarAlugueisDoUsuario(uid, somenteAtivos = true)
                _livrosAlugadosState.value = alugueisEncontrados

            } catch (e: Exception) {
                erro = "Erro ao carregar dados: ${e.message}"
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun atualizarDados() {
        carregarDados()
    }

    fun devolverLivro(aluguel: AluguelModel) {
        viewModelScope.launch {
            _loadingState.value = true
            val resultado = aluguelRepository.devolverLivro(aluguel.id, aluguel.livroId)

            if (resultado.isSuccess) {
                carregarDados()
            } else {
                erro = "Erro ao devolver livro."
            }
            _loadingState.value = false
        }
    }
}