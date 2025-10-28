package br.com.corecode.wtcchallenge.data.repository

import android.util.Log
import br.com.corecode.wtcchallenge.domain.model.Campaign
import br.com.corecode.wtcchallenge.domain.repository.ICampaignRepository
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class CampaignRepository : ICampaignRepository {

    private val db = Firebase.firestore
    private val campaignsCollection = db.collection("campaigns")
    private val TAG = "CampaignRepository"

    override fun getCampaigns(): Flow<Result<List<Campaign>>> {
        var query : Query = campaignsCollection
        return campaignsCollection
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .snapshots()
            .map { snapshot ->
                Log.d(TAG, "Campanhas atualizadas: ${snapshot.size()} novas")
                Result.success(snapshot.toObjects<Campaign>())
            }
            .catch { e ->
                Log.e(TAG, "Erro ao ouvir campanhas", e)
                emit(Result.failure(e))
            }
    }

    override suspend fun getCampaign(campaignId: String): Result<Campaign> {
        return try {
            val document = campaignsCollection.document(campaignId).get().await()
            val campaign = document.toObject<Campaign>()
            if (campaign != null) {
                Result.success(campaign)
            } else {
                Result.failure(Exception("Campanha não encontrada."))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao buscar campanha $campaignId", e)
            Result.failure(e)
        }
    }

    override suspend fun saveCampaign(campaign: Campaign): Result<Unit> {
        return try {
            campaignsCollection.document(campaign.id).set(campaign).await()
            Log.d(TAG, "Campanha salva: ${campaign.id}")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao salvar campanha", e)
            Result.failure(e)
        }
    }

    override suspend fun deleteCampaign(campaignId: String): Result<Unit> {
        return try {
            campaignsCollection.document(campaignId).delete().await()
            Log.d(TAG, "Campanha deletada: $campaignId")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao deletar campanha", e)
            Result.failure(e)
        }
    }
}