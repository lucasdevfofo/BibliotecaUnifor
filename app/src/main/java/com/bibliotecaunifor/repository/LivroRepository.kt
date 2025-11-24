package com.bibliotecaunifor.repository

import com.bibliotecaunifor.model.Livro
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class LivroRepository @Inject constructor() {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("livros")

    suspend fun buscarTodosLivros(): List<Livro> {
        return try {
            val result = collection.get().await()
            result.documents.map { doc ->
                doc.toLivro()
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
                .map { doc -> doc.toLivro() }
                .filter { it.titulo.contains(titulo, ignoreCase = true) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun buscarLivroPorId(livroId: String): Livro? {
        return try {
            val doc = collection.document(livroId).get().await()
            if (doc.exists()) {
                doc.toLivro()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun adicionarLivro(livro: Livro): String {
        return try {
            val livroComId = if (livro.id.isEmpty()) {
                livro.copy(id = UUID.randomUUID().toString())
            } else {
                livro
            }

            val livroData = hashMapOf(
                "titulo" to livroComId.titulo,
                "descricao" to livroComId.descricao,
                "genero" to livroComId.genero,
                "autor" to livroComId.autor,
                "disponibilidade" to livroComId.disponibilidade,
                "isbn" to livroComId.isbn,
                "anoPublicacao" to livroComId.anoPublicacao,
                "editora" to livroComId.editora,
                "paginas" to livroComId.paginas,
                "imagemUrl" to livroComId.imagemUrl,
                "dataCadastro" to livroComId.dataCadastro
            )

            collection.document(livroComId.id).set(livroData).await()
            livroComId.id
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun atualizarLivro(livro: Livro) {
        try {
            val livroData = hashMapOf(
                "titulo" to livro.titulo,
                "descricao" to livro.descricao,
                "genero" to livro.genero,
                "autor" to livro.autor,
                "disponibilidade" to livro.disponibilidade,
                "isbn" to livro.isbn,
                "anoPublicacao" to livro.anoPublicacao,
                "editora" to livro.editora,
                "paginas" to livro.paginas,
                "imagemUrl" to livro.imagemUrl,
                "dataCadastro" to livro.dataCadastro
            )

            collection.document(livro.id).update(livroData as Map<String, Any>).await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun excluirLivro(livroId: String) {
        try {
            collection.document(livroId).delete().await()
        } catch (e: Exception) {
            throw e
        }
    }

    private fun com.google.firebase.firestore.DocumentSnapshot.toLivro(): Livro {
        return Livro(
            id = id,
            titulo = getString("titulo") ?: "",
            descricao = getString("descricao") ?: "",
            genero = getString("genero") ?: "",
            autor = getString("autor") ?: "",
            disponibilidade = getString("disponibilidade") ?: "Disponível",
            isbn = getString("isbn") ?: "",
            anoPublicacao = getLong("anoPublicacao")?.toInt() ?: 0,
            editora = getString("editora") ?: "",
            paginas = getLong("paginas")?.toInt() ?: 0,
            imagemUrl = getString("imagemUrl") ?: "",
            dataCadastro = getString("dataCadastro") ?: ""
        )
    }
}