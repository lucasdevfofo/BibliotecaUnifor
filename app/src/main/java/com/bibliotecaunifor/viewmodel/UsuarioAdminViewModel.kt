package com.bibliotecaunifor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.UsuarioModel
import com.bibliotecaunifor.repository.AuthRepository
import com.bibliotecaunifor.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UsuarioAdminViewModel : ViewModel() {

    private val usuarioRepository = UsuarioRepository()
    private val authRepository = AuthRepository()

    private val _usuarios = MutableStateFlow<List<Pair<String, UsuarioModel>>>(emptyList())
    val usuarios: StateFlow<List<Pair<String, UsuarioModel>>> = _usuarios.asStateFlow()


    val usuariosSemAdmin: StateFlow<List<Pair<String, UsuarioModel>>> =
        _usuarios.map { usuariosList ->
            usuariosList.filter { (_, usuario) ->
                usuario.tipo != "admin"
            }
        }.stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _mensagem = MutableStateFlow<String?>(null)
    val mensagem: StateFlow<String?> = _mensagem.asStateFlow()

    init {
        carregarUsuarios()
    }

    fun carregarUsuarios() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val usuariosList = usuarioRepository.buscarTodosUsuarios()
                _usuarios.value = usuariosList
            } catch (e: Exception) {
                _mensagem.value = "Erro ao carregar usuários: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun cadastrarUsuario(usuario: UsuarioModel, senha: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                authRepository.adminCadastrarUsuario(usuario, senha) { success, message ->
                    if (success) {
                        _mensagem.value = "Usuário cadastrado com sucesso!"
                        carregarUsuarios()
                    } else {
                        _mensagem.value = "Erro ao cadastrar: $message"
                    }
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _mensagem.value = "Erro: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun editarUsuario(uid: String, usuario: UsuarioModel) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val resultado = usuarioRepository.salvarOuAtualizarUsuario(uid, usuario)

                if (resultado.isSuccess) {
                    _usuarios.value = _usuarios.value.map { (id, user) ->
                        if (id == uid) Pair(id, usuario) else Pair(id, user)
                    }
                    _mensagem.value = "Usuário atualizado com sucesso!"
                } else {
                    _mensagem.value = "Erro ao atualizar usuário"
                }
            } catch (e: Exception) {
                _mensagem.value = "Erro: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun excluirUsuario(uid: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val resultado = usuarioRepository.excluirUsuario(uid)

                if (resultado.isSuccess) {
                    _usuarios.value = _usuarios.value.filter { (id, _) -> id != uid }
                    _mensagem.value = "Usuário excluído com sucesso!"
                } else {
                    _mensagem.value = "Erro ao excluir usuário"
                }
            } catch (e: Exception) {
                _mensagem.value = "Erro: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun limparMensagem() {
        _mensagem.value = null
    }
}