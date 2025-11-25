package com.bibliotecaunifor.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.ComunicadoModel
import com.bibliotecaunifor.repository.ComunicadoRepository
import kotlinx.coroutines.launch

class TelaNotificacoesAdminViewModel : ViewModel() {

    private val repository = ComunicadoRepository()

    var listaComunicados by mutableStateOf<List<ComunicadoModel>>(emptyList())
        private set

    var loading by mutableStateOf(false)
        private set

    fun carregarComunicados() {
        loading = true
        viewModelScope.launch {
            // Busca todos os comunicados do banco
            listaComunicados = repository.buscarTodosComunicados()
            loading = false
        }
    }
}