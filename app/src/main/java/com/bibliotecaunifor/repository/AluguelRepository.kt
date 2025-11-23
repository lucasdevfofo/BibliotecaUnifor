package com.bibliotecaunifor.repository

import com.bibliotecaunifor.model.AluguelModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class AluguelRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("alugueis")

    // Cria o aluguel
    suspend fun realizarAluguel(aluguel: AluguelModel): Result<Boolean> {
        return try {
            val aluguelRef = collection.document()
            val livroRef = db.collection("livros").document(aluguel.livroId)

            db.runBatch { batch ->
                // Salva o ID gerado dentro do documento para facilitar
                val aluguelComId = aluguel.copy(id = aluguelRef.id)
                batch.set(aluguelRef, aluguelComId)

                // Marca o livro como indisponível
                batch.update(livroRef, "disponibilidade", "Indisponível")
            }.await()

            Result.success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    // Busca a lista (CORRIGIDO O PROBLEMA DO ID)
    suspend fun buscarAlugueisDoUsuario(uid: String, somenteAtivos: Boolean): List<AluguelModel> {
        return try {
            var query: Query = collection.whereEqualTo("usuarioId", uid)

            if (somenteAtivos) {
                query = query.whereEqualTo("status", "ativo")
            }

            val snapshot = query.get().await()

            // --- CORREÇÃO IMPORTANTE ---
            // Pegamos o ID do documento manualmente para garantir que não venha vazio
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(AluguelModel::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // Devolve o livro
    suspend fun devolverLivro(aluguelId: String, livroId: String): Result<Boolean> {
        return try {
            // Validação extra para evitar crash
            if (aluguelId.isBlank()) throw Exception("ID do aluguel inválido")

            val aluguelRef = collection.document(aluguelId)
            val livroRef = db.collection("livros").document(livroId)

            db.runBatch { batch ->
                batch.update(aluguelRef, "status", "devolvido")
                batch.update(livroRef, "disponibilidade", "Disponível")
            }.await()

            Result.success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    // Renova o aluguel
    suspend fun renovarAluguel(aluguelId: String, novaDataDevolucao: Timestamp): Result<Boolean> {
        return try {
            // Validação extra para evitar crash
            if (aluguelId.isBlank()) throw Exception("ID do aluguel inválido")

            val aluguelRef = collection.document(aluguelId)

            // Atualiza apenas a data e a marcação de renovado
            aluguelRef.update(
                mapOf(
                    "dataDevolucaoPrevista" to novaDataDevolucao,
                    "renovado" to true
                )
            ).await()

            Result.success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}