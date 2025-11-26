package com.bibliotecaunifor.repository

import com.bibliotecaunifor.model.Sala
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SalaRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("salas")

    suspend fun getTodasSalas(): List<Sala> {
        return try {
            val result = collection.get().await()
            result.documents.map { doc ->
                Sala(
                    id = doc.id,
                    nome = doc.getString("nome") ?: "",
                    capacidade = doc.getLong("capacidade")?.toInt() ?: 0,
                    tipo = doc.getString("tipo") ?: "",
                    disponivel = doc.getBoolean("disponivel") ?: true
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun atualizarSala(sala: Sala): Boolean {
        return try {
            val salaData = hashMapOf(
                "nome" to sala.nome,
                "capacidade" to sala.capacidade,
                "tipo" to sala.tipo,
                "disponivel" to sala.disponivel
            )
            collection.document(sala.id).update(salaData as Map<String, Any>).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun excluirSala(salaId: String): Boolean {
        return try {
            collection.document(salaId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}