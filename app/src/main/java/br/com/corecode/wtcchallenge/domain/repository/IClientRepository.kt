package br.com.corecode.wtcchallenge.domain.repository

import br.com.corecode.wtcchallenge.domain.model.Client

interface IClientRepository {
    suspend fun getClients(searchTerm: String, tags: List<String>): Result<List<Client>>
    suspend fun addNoteToClient(clientId: String, note: String): Result<Unit>
}