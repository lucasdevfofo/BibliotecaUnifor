package com.bibliotecaunifor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bibliotecaunifor.repository.SalaRepository

class TelaAdminGerenciarSalasViewModelFactory(
    private val salaRepository: SalaRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TelaAdminGerenciarSalasViewModel::class.java)) {
            return TelaAdminGerenciarSalasViewModel(salaRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}