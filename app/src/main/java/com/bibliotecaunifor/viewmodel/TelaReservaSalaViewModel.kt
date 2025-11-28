package com.bibliotecaunifor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.Reserva
import com.bibliotecaunifor.repository.ConfiguracaoRepository
import com.bibliotecaunifor.repository.ReservaRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

class TelaReservaSalaViewModel : ViewModel() {

    private val reservaRepository = ReservaRepository()
    private val configuracaoRepository = ConfiguracaoRepository()
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    fun fazerReserva(
        salaId: String,
        salaNome: String,
        dia: String,
        horarioInicio: String,
        horarioFim: String,
        finalidade: String
    ) {
        val user = auth.currentUser
        if (user == null) {
            _uiState.value = UiState.Error("Usuário não logado")
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {
                // Validar duração máxima de 2 horas
                val duracao = calcularDuracao(horarioInicio, horarioFim)
                if (duracao > 2) {
                    _uiState.value = UiState.Error("Duração máxima permitida é de 2 horas. Você selecionou ${duracao} horas.")
                    return@launch
                }

                val config = configuracaoRepository.buscarConfiguracoes()

                val reservasAtivas = reservaRepository.getMinhasReservas(user.uid)
                    .filter { it.status == "pendente" || it.status == "confirmada" }

                if (reservasAtivas.size >= config.limiteSalasPorUsuario) {
                    _uiState.value = UiState.Error("Você já tem ${reservasAtivas.size} sala(s) reservada(s). Limite máximo: ${config.limiteSalasPorUsuario}.")
                    return@launch
                }

                val dataReserva = converterDiaParaData(dia)
                val disponivel = reservaRepository.verificarDisponibilidade(
                    salaId, dataReserva, horarioInicio, horarioFim
                )

                if (!disponivel) {
                    _uiState.value = UiState.Error("Sala não disponível neste horário")
                    return@launch
                }

                val userDoc = db.collection("usuarios").document(user.uid).get().await()
                if (!userDoc.exists()) {
                    _uiState.value = UiState.Error("Dados do usuário não encontrados")
                    return@launch
                }

                val usuarioNome = userDoc.getString("nomeCompleto") ?: userDoc.getString("nome") ?: ""
                val usuarioMatricula = userDoc.getString("matricula") ?: ""

                val reservaId = "${salaId}_${user.uid}_${System.currentTimeMillis()}"
                val reserva = Reserva(
                    id = reservaId,
                    usuarioId = user.uid,
                    usuarioNome = usuarioNome,
                    usuarioMatricula = usuarioMatricula,
                    salaId = salaId,
                    salaNome = salaNome,
                    data = dataReserva,
                    horarioInicio = horarioInicio,
                    horarioFim = horarioFim,
                    status = "pendente",
                    dataCriacao = getDataAtual(),
                    dataAtualizacao = getDataAtual()
                )

                val resultado = reservaRepository.criarReserva(reserva)
                if (resultado.isSuccess) {
                    _uiState.value = UiState.Success
                } else {
                    _uiState.value = UiState.Error("Erro ao criar reserva: ${resultado.exceptionOrNull()?.message}")
                }

            } catch (e: Exception) {
                _uiState.value = UiState.Error("Erro ao fazer reserva: ${e.message}")
            }
        }
    }

    private fun converterDiaParaData(dia: String): String {
        val calendar = Calendar.getInstance()
        val diasSemana = listOf("Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado")
        val diaIndex = diasSemana.indexOf(dia)

        if (diaIndex != -1) {
            val hoje = calendar.get(Calendar.DAY_OF_WEEK) - 1
            var diasParaAdicionar = diaIndex - hoje
            if (diasParaAdicionar <= 0) diasParaAdicionar += 7

            calendar.add(Calendar.DAY_OF_MONTH, diasParaAdicionar)
        }

        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
    }

    private fun getDataAtual(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
    }

    private fun calcularDuracao(horarioInicio: String, horarioFim: String): Int {
        return try {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            val inicio = sdf.parse(horarioInicio)?.time ?: 0
            val fim = sdf.parse(horarioFim)?.time ?: 0

            if (fim < inicio) {
                // Se fim < inicio, significa que passou para o próximo dia
                ((fim - inicio + 24 * 60 * 60 * 1000) / (60 * 60 * 1000)).toInt()
            } else {
                ((fim - inicio) / (60 * 60 * 1000)).toInt()
            }
        } catch (e: Exception) {
            0
        }
    }

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }
}