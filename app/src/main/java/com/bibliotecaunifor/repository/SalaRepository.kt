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
                    localizacao = doc.getString("localizacao") ?: "",
                    recursos = parseRecursos(doc.getString("recursos")),
                    disponivel = doc.getBoolean("disponivel") ?: true
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun parseRecursos(recursosString: String?): List<String> {
        return try {
            recursosString?.removeSurrounding("\"")?.split("\", \"") ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}