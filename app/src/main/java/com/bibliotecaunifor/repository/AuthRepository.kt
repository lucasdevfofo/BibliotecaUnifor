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

                    val adminData = hashMapOf(
                        "nomeCompleto" to admin.nomeCompleto,
                        "matricula" to admin.matricula,
                        "email" to admin.email,
                        "telefone" to admin.telefone,
                        "cpf" to admin.cpf,
                        "tipo" to admin.tipo
                    )

                    db.collection("usuarios")
                        .document(uid)
                        .set(adminData)
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

    // ------------ CADASTRAR USUARIO (PARA USUÁRIO COMUM) ---------------
    fun cadastrarUsuario(
        usuario: UsuarioModel,
        senha: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(usuario.email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser!!.uid

                    val usuarioData = hashMapOf(
                        "nomeCompleto" to usuario.nomeCompleto,
                        "matricula" to usuario.matricula,
                        "email" to usuario.email,
                        "telefone" to usuario.telefone,
                        "cpf" to usuario.cpf,
                        "curso" to usuario.curso,
                        "tipo" to usuario.tipo
                    )

                    db.collection("usuarios")
                        .document(uid)
                        .set(usuarioData)
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

    // ------------ ADMIN CADASTRAR USUARIO ---------------
    fun adminCadastrarUsuario(
        usuario: UsuarioModel,
        senha: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        println("🟢 DEBUG: adminCadastrarUsuario INICIADO - Email: ${usuario.email}")

        val tempAuth = FirebaseAuth.getInstance()

        tempAuth.createUserWithEmailAndPassword(usuario.email, senha)
            .addOnCompleteListener { task ->
                println("🟡 DEBUG: createUserWithEmailAndPassword COMPLETADO - Sucesso: ${task.isSuccessful}")

                if (task.isSuccessful) {
                    val uid = task.result?.user?.uid
                    println("🔵 DEBUG: UID do novo usuário: $uid")

                    if (uid != null) {
                        val usuarioData = hashMapOf(
                            "nomeCompleto" to usuario.nomeCompleto,
                            "matricula" to usuario.matricula,
                            "email" to usuario.email,
                            "telefone" to usuario.telefone,
                            "cpf" to usuario.cpf,
                            "curso" to usuario.curso,
                            "tipo" to usuario.tipo
                        )

                        println("🟣 DEBUG: Salvando no Firestore...")

                        db.collection("usuarios")
                            .document(uid)
                            .set(usuarioData)
                            .addOnSuccessListener {
                                println("✅ DEBUG: Firestore SALVO COM SUCESSO!")
                                tempAuth.signOut()
                                println("🔴 DEBUG: Logout do usuário criado")
                                onResult(true, null)
                            }
                            .addOnFailureListener { e ->
                                println("❌ DEBUG: Firestore FALHOU - ${e.message}")
                                tempAuth.signOut()
                                onResult(false, "Erro Firestore: ${e.message}")
                            }
                    } else {
                        println("❌ DEBUG: UID É NULO")
                        onResult(false, "UID do usuário é nulo")
                    }
                } else {
                    val error = task.exception?.message ?: "Erro desconhecido"
                    println("❌ DEBUG: createUser FALHOU - $error")
                    onResult(false, error)
                }
            }
            .addOnFailureListener { e ->
                println("❌ DEBUG: addOnFailureListener - ${e.message}")
                onResult(false, e.message)
            }
    }

    // ------------ LOGIN POR MATRÍCULA ---------------
    fun loginPorMatricula(
        matricula: String,
        senha: String,
        onResult: (Boolean, String?) -> Unit
    ) {
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