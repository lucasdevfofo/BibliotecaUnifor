package com.bibliotecaunifor.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.repository.AuthRepository
import com.bibliotecaunifor.repository.UsuarioRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val authRepo = AuthRepository()
    private val usuarioRepo = UsuarioRepository() // Vamos usar para checar o tipo

    var loading by mutableStateOf(false)
    var sucesso by mutableStateOf(false)
    var erro by mutableStateOf<String?>(null)

    // Agora recebe o booleano do botão (isAdmin)
    fun loginComMatricula(matricula: String, senha: String, tentarComoAdmin: Boolean) {
        loading = true
        erro = null

        // 1. Tenta logar no Auth (Email/Senha)
        authRepo.loginPorMatricula(matricula, senha) { loginOk, msg ->
            if (!loginOk) {
                loading = false
                erro = msg
                return@loginPorMatricula
            }

            // 2. Login deu certo? Agora verifica a AUTORIZAÇÃO no Firestore
            viewModelScope.launch {
                val uid = FirebaseAuth.getInstance().currentUser?.uid

                if (uid != null) {
                    val usuarioDoBanco = usuarioRepo.buscarUsuario(uid)
                    val tipoNoBanco = usuarioDoBanco?.tipo ?: "usuario" // Se não tiver tipo, assume usuario

                    // LÓGICA DE SEGURANÇA:
                    // Se o usuário quer entrar como Admin, mas no banco não é Admin...
                    if (tentarComoAdmin && tipoNoBanco != "admin") {
                        erro = "Acesso Negado: Esta conta não possui permissão de Administrador."
                        FirebaseAuth.getInstance().signOut() // Desloga o intruso imediatamente
                        loading = false
                    } else {
                        // Tudo certo! (Ou é admin legítimo, ou é usuário entrando como usuário)
                        sucesso = true
                        loading = false
                    }
                } else {
                    erro = "Erro ao recuperar identificação do usuário."
                    loading = false
                }
            }
        }
    }
}