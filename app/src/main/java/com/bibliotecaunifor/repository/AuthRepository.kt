package com.bibliotecaunifor.repository

import com.bibliotecaunifor.model.AdminModel
import com.bibliotecaunifor.model.UsuarioModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // ------------ CADASTRAR ADMIN ---------------
    fun cadastrarAdmin(
        admin: AdminModel,
        senha: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(admin.email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser!!.uid

                    db.collection("usuarios")
                        .document(uid)
                        .set(admin)
                        .addOnSuccessListener {
                            onResult(true, null)
                        }
                        .addOnFailureListener { e ->
                            onResult(false, e.message)
                        }

                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    // ------------ CADASTRAR USUARIO ---------------
    fun cadastrarUsuario(
        usuario: UsuarioModel,
        senha: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(usuario.email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser!!.uid

                    db.collection("usuarios")
                        .document(uid)
                        .set(usuario)
                        .addOnSuccessListener {
                            onResult(true, null)
                        }
                        .addOnFailureListener { e ->
                            onResult(false, e.message)
                        }

                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    // ------------ LOGIN POR MATRÍCULA ---------------
    fun loginPorMatricula(
        matricula: String,
        senha: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        // 1) Buscar documento do usuário pela matrícula
        db.collection("usuarios")
            .whereEqualTo("matricula", matricula)
            .limit(1)
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    onResult(false, "Usuário não encontrado para esta matrícula.")
                } else {
                    val doc = result.documents[0]
                    val email = doc.getString("email")

                    if (email.isNullOrBlank()) {
                        onResult(false, "E-mail não encontrado para esta matrícula.")
                    } else {
                        // 2) Fazer login com email + senha no Auth
                        auth.signInWithEmailAndPassword(email, senha)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    onResult(true, null)
                                } else {
                                    onResult(
                                        false,
                                        task.exception?.message ?: "Erro ao fazer login."
                                    )
                                }
                            }
                    }
                }
            }
            .addOnFailureListener { e ->
                onResult(false, e.message ?: "Erro ao buscar usuário.")
            }
    }
}
