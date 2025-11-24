package com.bibliotecaunifor.repository

import com.bibliotecaunifor.model.ConfiguracaoModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ConfiguracaoRepository @Inject constructor() {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("configuracoes")

    suspend fun buscarConfiguracoes(): ConfiguracaoModel {
        return try {
            val document = collection.document("regras_sistema").get().await()
            if (document.exists()) {
                ConfiguracaoModel(
                    id = document.id,
                    limiteLivrosPorUsuario = document.getLong("limiteLivrosPorUsuario")?.toInt() ?: 2,
                    diasMaximoEmprestimo = document.getLong("diasMaximoEmprestimo")?.toInt() ?: 7,
                    limiteSalasPorUsuario = document.getLong("limiteSalasPorUsuario")?.toInt() ?: 2,
                    permiteRenovacao = document.getBoolean("permiteRenovacao") ?: true,
                    diasRenovacao = document.getLong("diasRenovacao")?.toInt() ?: 7,
                    dataAtualizacao = document.getString("dataAtualizacao") ?: ""
                )
            } else {
                ConfiguracaoModel()
            }
        } catch (e: Exception) {
            ConfiguracaoModel()
        }
    }

    suspend fun salvarConfiguracoes(configuracao: ConfiguracaoModel): Result<Boolean> {
        return try {
            val dataFormatada = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")).format(Date())

            val configData = hashMapOf(
                "limiteLivrosPorUsuario" to configuracao.limiteLivrosPorUsuario,
                "diasMaximoEmprestimo" to configuracao.diasMaximoEmprestimo,
                "limiteSalasPorUsuario" to configuracao.limiteSalasPorUsuario,
                "permiteRenovacao" to configuracao.permiteRenovacao,
                "diasRenovacao" to configuracao.diasRenovacao,
                "dataAtualizacao" to dataFormatada
            )

            collection.document("regras_sistema").set(configData).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}