package com.bibliotecaunifor.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bibliotecaunifor.model.Sala
import com.bibliotecaunifor.repository.SalaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TelaSalasDisponiveisViewModel : ViewModel() {

    private val repo = SalaRepository()

    var loading by mutableStateOf(false)
    var salas by mutableStateOf<List<Sala>>(emptyList())
    var erro by mutableStateOf<String?>(null)

    fun carregarSalas() {
        loading = true
        erro = null

        CoroutineScope(Dispatchers.IO).launch {
            try {
                salas = repo.getTodasSalas()
            } catch (e: Exception) {
                erro = "Erro ao carregar salas: ${e.message}"
            } finally {
                loading = false
            }
        }
    }
}