package br.com.corecode.wtcchallenge.data.repository

import android.util.Log
import br.com.corecode.wtcchallenge.domain.model.Client
import br.com.corecode.wtcchallenge.domain.model.ClientNote // Vamos precisar desta model
import br.com.corecode.wtcchallenge.domain.repository.IClientRepository
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ClientRepository : IClientRepository {

    private val db = Firebase.firestore
    private val clientsCollection = db.collection("clients")
    private val TAG = "ClientRepository"

    override suspend fun getClients(searchTerm: String, tags: List<String>): Result<List<Client>> {
        return try {
            var query: Query = clientsCollection

            if (searchTerm.isNotBlank()) {
                query = query
                    .orderBy("name")
                    .whereGreaterThanOrEqualTo("name", searchTerm)
                    .whereLessThanOrEqualTo("name", searchTerm + '\uf8ff')
            }

            if (tags.isNotEmpty()) {

                query = clientsCollection.whereArrayContainsAny("tags", tags)

            } else if (searchTerm.isBlank() && tags.isEmpty()) {
                query = clientsCollection.limit(50)
            }


            val snapshot = query.get().await()
            val clients = snapshot.toObjects<Client>()

            Log.d(TAG, "Clientes encontrados: ${clients.size}")
            Result.success(clients)

        } catch (e: Exception) {
            Log.e(TAG, "Erro ao buscar clientes", e)
            Result.failure(e)
        }
    }

    override suspend fun addNoteToClient(clientId: String, note: String): Result<Unit> {
        return try {
            if (clientId.isBlank() || note.isBlank()) {
                return Result.failure(IllegalArgumentException("ID do cliente ou nota estão em branco"))
            }

            val note = ClientNote(text = note)

            clientsCollection.document(clientId)
                .collection("notes")
                .add(note)
                .await()

            Log.d(TAG, "Nota adicionada ao cliente $clientId")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao adicionar nota", e)
            Result.failure(e)
        }
    }
}