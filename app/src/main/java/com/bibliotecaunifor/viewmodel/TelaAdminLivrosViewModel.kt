package com.bibliotecaunifor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.Livro
import com.bibliotecaunifor.repository.LivroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TelaAdminLivrosViewModel : ViewModel() {

    private val livroRepository = LivroRepository()

    private val _livros = MutableStateFlow<List<Livro>>(emptyList())
    val livros: StateFlow<List<Livro>> = _livros.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _mensagem = MutableStateFlow<String?>(null)
    val mensagem: StateFlow<String?> = _mensagem.asStateFlow()

    init {
        carregarLivros()
    }

    fun carregarLivros() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _livros.value = livroRepository.buscarTodosLivros()
                _mensagem.value = null
            } catch (e: Exception) {
                _mensagem.value = "Erro ao carregar livros: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun adicionarLivro(livro: Livro) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                livroRepository.adicionarLivro(livro)
                carregarLivros()
                _mensagem.value = "Livro adicionado com sucesso!"
            } catch (e: Exception) {
                _mensagem.value = "Erro ao adicionar livro: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun atualizarLivro(livro: Livro) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                livroRepository.atualizarLivro(livro)
                carregarLivros()
                _mensagem.value = "Livro atualizado com sucesso!"
            } catch (e: Exception) {
                _mensagem.value = "Erro ao atualizar livro: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun excluirLivro(livroId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                livroRepository.excluirLivro(livroId)
                carregarLivros()
                _mensagem.value = "Livro excluído com sucesso!"
            } catch (e: Exception) {
                _mensagem.value = "Erro ao excluir livro: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun buscarLivroPorId(livroId: String, onResult: (Livro?) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val livro = livroRepository.buscarLivroPorId(livroId)
                onResult(livro)
                _mensagem.value = null
            } catch (e: Exception) {
                _mensagem.value = "Erro ao buscar livro: ${e.message}"
                onResult(null)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun limparMensagem() {
        _mensagem.value = null
    }
}