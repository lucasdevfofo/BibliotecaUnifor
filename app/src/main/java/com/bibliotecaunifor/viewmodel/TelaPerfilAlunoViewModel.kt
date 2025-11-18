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

class TelaPerfilAlunoViewModel : ViewModel() {

    private val repository = UsuarioRepository()
    private val auth = FirebaseAuth.getInstance()

    // Estado que guarda os dados do usuário. Começa nulo.
    private val _usuarioState = MutableStateFlow<UsuarioModel?>(null)
    val usuarioState: StateFlow<UsuarioModel?> = _usuarioState.asStateFlow()

    fun carregarDados() {
        val uid = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            val usuarioEncontrado = repository.buscarUsuario(uid)
            if (usuarioEncontrado != null) {
                _usuarioState.value = usuarioEncontrado
            }
        }
    }
}