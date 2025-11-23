package com.bibliotecaunifor.repository

import com.bibliotecaunifor.model.UsuarioModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UsuarioRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("usuarios")


    suspend fun buscarUsuario(uid: String): UsuarioModel? {
        return buscarUsuarioPorId(uid)
    }

    suspend fun buscarTodosUsuarios(): List<Pair<String, UsuarioModel>> {
        println("📚 DEBUG Repository: buscarTodosUsuarios INICIADO")
        return try {
            val querySnapshot = collection.get().await()
            println("📚 DEBUG Repository: Encontrados ${querySnapshot.documents.size} usuários")

            val usuarios = querySnapshot.documents.mapNotNull { document ->
                val usuario = document.toObject(UsuarioModel::class.java)
                if (usuario != null) {
                    Pair(document.id, usuario)
                } else {
                    null
                }
            }
            println("📚 DEBUG Repository: ${usuarios.size} usuários mapeados")
            usuarios
        } catch (e: Exception) {
            println("❌ DEBUG Repository: ERRO - ${e.message}")
            emptyList()
        }
    }

    suspend fun buscarUsuarioPorId(uid: String): UsuarioModel? {
        return try {
            val document = collection.document(uid).get().await()
            document.toObject(UsuarioModel::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun salvarOuAtualizarUsuario(uid: String, usuario: UsuarioModel): Result<Boolean> {
        return try {
            val usuarioData = hashMapOf(
                "nomeCompleto" to usuario.nomeCompleto,
                "matricula" to usuario.matricula,
                "curso" to usuario.curso,
                "cpf" to usuario.cpf,
                "telefone" to usuario.telefone,
                "email" to usuario.email,
                "tipo" to usuario.tipo
            )

            collection.document(uid).set(usuarioData).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun excluirUsuario(uid: String): Result<Boolean> {
        return try {
            collection.document(uid).delete().await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun buscarUsuarioPorEmail(email: String): UsuarioModel? {
        return try {
            val querySnapshot = collection
                .whereEqualTo("email", email)
                .get()
                .await()

            querySnapshot.documents.firstOrNull()?.toObject(UsuarioModel::class.java)
        } catch (e: Exception) {
            null
        }
    }
}