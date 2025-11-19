package com.bibliotecaunifor.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibliotecaunifor.model.Livro
import com.bibliotecaunifor.repository.LivroRepository
import kotlinx.coroutines.launch

class TelaCatalogoLivrosViewModel : ViewModel() {

    private val livroRepository = LivroRepository()

    var livros by mutableStateOf<List<Livro>>(emptyList())
        private set

    var livrosFiltrados by mutableStateOf<List<Livro>>(emptyList())
        private set

    var loading by mutableStateOf(false)
        private set

    var erro by mutableStateOf<String?>(null)
        private set

    fun carregarLivros() {
        loading = true
        erro = null

        viewModelScope.launch {
            try {
                livros = livroRepository.buscarTodosLivros()
                livrosFiltrados = livros
            } catch (e: Exception) {
                erro = "Erro ao carregar livros: ${e.message}"
            } finally {
                loading = false
            }
        }
    }

    fun pesquisarLivros(termo: String) {
        if (termo.isBlank()) {
            livrosFiltrados = livros
        } else {
            viewModelScope.launch {
                try {
                    livrosFiltrados = livroRepository.buscarLivrosPorTitulo(termo)
                } catch (e: Exception) {
                    erro = "Erro na pesquisa: ${e.message}"
                }
            }
        }
    }
}