package com.bibliotecaunifor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.UsuarioModel
import com.bibliotecaunifor.repository.UsuarioRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TelaPerfilAdminViewModel : ViewModel() {

    private val repository = UsuarioRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _adminState = MutableStateFlow<UsuarioModel?>(null)
    val adminState: StateFlow<UsuarioModel?> = _adminState.asStateFlow()

    fun carregarDados() {
        val uid = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            val adminEncontrado = repository.buscarUsuario(uid)
            if (adminEncontrado != null) {
                _adminState.value = adminEncontrado
            }
        }
    }
}