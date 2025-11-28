package com.bibliotecaunifor.repository

import com.bibliotecaunifor.model.Reserva
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

class ReservaRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("reservas")

    suspend fun criarReserva(reserva: Reserva): Result<Boolean> {
        return try {
            val reservaData = hashMapOf(
                "id" to reserva.id,
                "usuarioId" to reserva.usuarioId,
                "usuarioNome" to reserva.usuarioNome,
                "usuarioMatricula" to reserva.usuarioMatricula,
                "salaId" to reserva.salaId,
                "salaNome" to reserva.salaNome,
                "data" to reserva.data,
                "horarioInicio" to reserva.horarioInicio,
                "horarioFim" to reserva.horarioFim,
                "status" to reserva.status,
                "dataCriacao" to getDataAtual(),
                "dataAtualizacao" to getDataAtual()
            )

            collection.document(reserva.id).set(reservaData).await()
            Result.success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getMinhasReservas(usuarioId: String): List<Reserva> {
        return try {
            val result = collection
                .whereEqualTo("usuarioId", usuarioId)
                .get()
                .await()

            result.documents.map { doc ->
                Reserva(
                    id = doc.getString("id") ?: doc.id,
                    usuarioId = doc.getString("usuarioId") ?: "",
                    usuarioNome = doc.getString("usuarioNome") ?: "",
                    usuarioMatricula = doc.getString("usuarioMatricula") ?: "",
                    salaId = doc.getString("salaId") ?: "",
                    salaNome = doc.getString("salaNome") ?: "",
                    data = doc.getString("data") ?: "",
                    horarioInicio = doc.getString("horarioInicio") ?: "",
                    horarioFim = doc.getString("horarioFim") ?: "",
                    status = doc.getString("status") ?: "pendente",
                    dataCriacao = doc.getString("dataCriacao") ?: "",
                    dataAtualizacao = doc.getString("dataAtualizacao") ?: ""
                )
            }.sortedByDescending { it.data }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun buscarTodasReservasUsuario(usuarioId: String): List<Reserva> {
        return try {
            val result = collection
                .whereEqualTo("usuarioId", usuarioId)
                .get()
                .await()

            result.documents.map { doc ->
                Reserva(
                    id = doc.getString("id") ?: doc.id,
                    usuarioId = doc.getString("usuarioId") ?: "",
                    usuarioNome = doc.getString("usuarioNome") ?: "",
                    usuarioMatricula = doc.getString("usuarioMatricula") ?: "",
                    salaId = doc.getString("salaId") ?: "",
                    salaNome = doc.getString("salaNome") ?: "",
                    data = doc.getString("data") ?: "",
                    horarioInicio = doc.getString("horarioInicio") ?: "",
                    horarioFim = doc.getString("horarioFim") ?: "",
                    status = doc.getString("status") ?: "pendente",
                    dataCriacao = doc.getString("dataCriacao") ?: "",
                    dataAtualizacao = doc.getString("dataAtualizacao") ?: ""
                )
            }.sortedByDescending { reserva ->
                try {
                    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    format.parse(reserva.data)
                } catch (e: Exception) {
                    null
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun verificarDisponibilidade(salaId: String, data: String, horarioInicio: String, horarioFim: String): Boolean {
        return try {
            // Buscar capacidade da sala
            val salaDoc = db.collection("salas").document(salaId).get().await()
            if (!salaDoc.exists()) return false

            val capacidade = salaDoc.getLong("capacidade")?.toInt() ?: 0
            if (capacidade <= 0) return false

            // Buscar todas as reservas confirmadas ou pendentes na mesma sala, mesmo dia, mesmo horário
            val result = collection
                .whereEqualTo("salaId", salaId)
                .whereEqualTo("data", data)
                .whereIn("status", listOf("pendente", "confirmada"))
                .get()
                .await()

            // Contar quantas reservas ocupam este horário
            val reservasNoHorario = result.documents.count { doc ->
                val reservaInicio = doc.getString("horarioInicio") ?: ""
                val reservaFim = doc.getString("horarioFim") ?: ""
                conflitoHorario(horarioInicio, horarioFim, reservaInicio, reservaFim)
            }

            // Se o número de reservas < capacidade, há espaço disponível
            reservasNoHorario < capacidade
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getTodasReservas(): List<Reserva> {
        return try {
            val result = collection
                .get()
                .await()

            result.documents.map { doc ->
                Reserva(
                    id = doc.getString("id") ?: doc.id,
                    usuarioId = doc.getString("usuarioId") ?: "",
                    usuarioNome = doc.getString("usuarioNome") ?: "",
                    usuarioMatricula = doc.getString("usuarioMatricula") ?: "",
                    salaId = doc.getString("salaId") ?: "",
                    salaNome = doc.getString("salaNome") ?: "",
                    data = doc.getString("data") ?: "",
                    horarioInicio = doc.getString("horarioInicio") ?: "",
                    horarioFim = doc.getString("horarioFim") ?: "",
                    status = doc.getString("status") ?: "pendente",
                    dataCriacao = doc.getString("dataCriacao") ?: "",
                    dataAtualizacao = doc.getString("dataAtualizacao") ?: ""
                )
            }.sortedByDescending { reserva ->
                try {
                    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    format.parse(reserva.data)
                } catch (e: Exception) {
                    null
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getReservasPorSala(salaId: String): List<Reserva> {
        return try {
            val result = collection
                .whereEqualTo("salaId", salaId)
                .get()
                .await()

            result.documents.map { doc ->
                Reserva(
                    id = doc.getString("id") ?: doc.id,
                    usuarioId = doc.getString("usuarioId") ?: "",
                    usuarioNome = doc.getString("usuarioNome") ?: "",
                    usuarioMatricula = doc.getString("usuarioMatricula") ?: "",
                    salaId = doc.getString("salaId") ?: "",
                    salaNome = doc.getString("salaNome") ?: "",
                    data = doc.getString("data") ?: "",
                    horarioInicio = doc.getString("horarioInicio") ?: "",
                    horarioFim = doc.getString("horarioFim") ?: "",
                    status = doc.getString("status") ?: "pendente",
                    dataCriacao = doc.getString("dataCriacao") ?: "",
                    dataAtualizacao = doc.getString("dataAtualizacao") ?: ""
                )
            }.sortedByDescending { it.data }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun conflitoHorario(inicio1: String, fim1: String, inicio2: String, fim2: String): Boolean {
        return !(fim1 <= inicio2 || inicio1 >= fim2)
    }

    private fun getDataAtual(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
    }
}