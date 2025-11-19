package com.bibliotecaunifor.repository

import com.bibliotecaunifor.model.Livro
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LivroRepository @Inject constructor() {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("livros")

    suspend fun buscarTodosLivros(): List<Livro> {
        return try {
            val result = collection.get().await()
            result.documents.map { doc ->
                Livro(
                    id = doc.id,
                    titulo = doc.getString("titulo") ?: "",
                    descricao = doc.getString("descricao") ?: "",
                    genero = doc.getString("genero") ?: "",
                    autor = doc.getString("autor") ?: "",
                    disponibilidade = doc.getString("disponibilidade") ?: "Disponível",
                    isbn = doc.getString("isbn") ?: "",
                    anoPublicacao = doc.getLong("anoPublicacao")?.toInt() ?: 0,
                    editora = doc.getString("editora") ?: "",
                    paginas = doc.getLong("paginas")?.toInt() ?: 0,
                    imagemUrl = doc.getString("imagemUrl") ?: "",
                    dataCadastro = doc.getString("dataCadastro") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun buscarLivrosPorTitulo(titulo: String): List<Livro> {
        return try {
            val result = collection
                .orderBy("titulo")
                .get()
                .await()

            result.documents
                .map { doc ->
                    Livro(
                        id = doc.id,
                        titulo = doc.getString("titulo") ?: "",
                        descricao = doc.getString("descricao") ?: "",
                        genero = doc.getString("genero") ?: "",
                        autor = doc.getString("autor") ?: "",
                        disponibilidade = doc.getString("disponibilidade") ?: "Disponível",
                        isbn = doc.getString("isbn") ?: "",
                        anoPublicacao = doc.getLong("anoPublicacao")?.toInt() ?: 0,
                        editora = doc.getString("editora") ?: "",
                        paginas = doc.getLong("paginas")?.toInt() ?: 0,
                        imagemUrl = doc.getString("imagemUrl") ?: "",
                        dataCadastro = doc.getString("dataCadastro") ?: ""
                    )
                }
                .filter { it.titulo.contains(titulo, ignoreCase = true) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun buscarLivroPorId(livroId: String): Livro? {
        return try {
            val doc = collection.document(livroId).get().await()
            if (doc.exists()) {
                Livro(
                    id = doc.id,
                    titulo = doc.getString("titulo") ?: "",
                    descricao = doc.getString("descricao") ?: "",
                    genero = doc.getString("genero") ?: "",
                    autor = doc.getString("autor") ?: "",
                    disponibilidade = doc.getString("disponibilidade") ?: "Disponível",
                    isbn = doc.getString("isbn") ?: "",
                    anoPublicacao = doc.getLong("anoPublicacao")?.toInt() ?: 0,
                    editora = doc.getString("editora") ?: "",
                    paginas = doc.getLong("paginas")?.toInt() ?: 0,
                    imagemUrl = doc.getString("imagemUrl") ?: "",
                    dataCadastro = doc.getString("dataCadastro") ?: ""
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}