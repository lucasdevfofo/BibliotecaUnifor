package com.bibliotecaunifor.repository

import com.bibliotecaunifor.model.UsuarioModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UsuarioRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("usuarios")

    // Salva ou Atualiza os dados do usuário no Firestore
    // O documentId DEVE ser o mesmo UID do Firebase Auth
    suspend fun salvarOuAtualizarUsuario(uid: String, usuario: UsuarioModel): Result<Boolean> {
        return try {
            // .set() com Merge garante que se o campo não existir, ele cria,
            // e se existir, atualiza (sem apagar campos extras que não estejam no model)
            collection.document(uid).set(usuario).await()
            Result.success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    // Função extra: Buscar dados para preencher a tela antes de editar
    suspend fun buscarUsuario(uid: String): UsuarioModel? {
        return try {
            val document = collection.document(uid).get().await()
            document.toObject(UsuarioModel::class.java)
        } catch (e: Exception) {
            null
        }
    }
}