package com.bibliotecaunifor.repository

import com.bibliotecaunifor.model.ComunicadoModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class ComunicadoRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("comunicados")

    // Admin envia um novo comunicado
    suspend fun enviarComunicado(comunicado: ComunicadoModel): Result<Boolean> {
        return try {
            val docRef = collection.document() // Cria ID automático
            val comunicadoComId = comunicado.copy(id = docRef.id)
            docRef.set(comunicadoComId).await()
            Result.success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    // Busca todos os comunicados ordenados por data (do mais recente para o mais antigo)
    suspend fun buscarTodosComunicados(): List<ComunicadoModel> {
        return try {
            val snapshot = collection
                .orderBy("dataEnvio", Query.Direction.DESCENDING)
                .get()
                .await()

            snapshot.toObjects(ComunicadoModel::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}