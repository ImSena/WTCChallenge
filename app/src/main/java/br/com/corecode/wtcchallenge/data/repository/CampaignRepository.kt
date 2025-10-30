package br.com.corecode.wtcchallenge.data.repository

import android.util.Log
import br.com.corecode.wtcchallenge.domain.model.Campaign
import br.com.corecode.wtcchallenge.domain.model.Message
import br.com.corecode.wtcchallenge.domain.model.RichMessage
import br.com.corecode.wtcchallenge.domain.model.RichMessageAction
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
    private val anunciosChatDoc = db.collection("chats").document("wtc_anuncios")
    private val anunciosMessagesCol = anunciosChatDoc.collection("messages")
    private val TAG = "CampaignRepository"

    override fun getCampaigns(): Flow<Result<List<Campaign>>> {
        return campaignsCollection
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .snapshots()
            .map { snapshot ->
                Log.d(TAG, "Campanhas atualizadas: ${snapshot.size()} novas")
                Result.success(snapshot.toObjects<Campaign>())
            }.catch { e ->
                Log.e(TAG, "Erro ao ouvir campanhas", e)
                emit(Result.failure(e))
            }
    }

    override suspend fun getCampaign(campaignId: String): Result<Campaign?> {
        return try {
            val document = campaignsCollection.document(campaignId).get().await()
            val campaign = document.toObject<Campaign>()
            if (campaign != null) {
                Result.success(campaign)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao buscar campanha $campaignId", e)
            Result.failure(e)
        }
    }

    override suspend fun saveCampaign(campaign: Campaign): Result<Unit> {
        return try {
            val docId = campaign.id.ifEmpty { campaignsCollection.document().id }

            val campaignToSave = campaign.copy(
                id = docId,
                timestamp = System.currentTimeMillis()
            )

            campaignsCollection.document(docId).set(campaignToSave).await()
            Log.d(TAG, "Campanha salva: $docId")
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

    suspend fun dispararCampanha(campaign: Campaign): Result<Unit> {
        return try {
            val actions = mutableListOf<RichMessageAction>()
            val actionUrls = mutableMapOf<String, String>()

            if (campaign.btn1Title.isNotBlank() && campaign.btn1Url.isNotBlank()) {
                actions.add(RichMessageAction("btn1", campaign.btn1Title))
                actionUrls["btn1"] = campaign.btn1Url
            }
            if (campaign.btn2Title.isNotBlank() && campaign.btn2Url.isNotBlank()) {
                actions.add(RichMessageAction("btn2", campaign.btn2Title))
                actionUrls["btn2"] = campaign.btn2Url
            }

            val richMessage = RichMessage(
                title = campaign.title,
                body = campaign.body,
                url = campaign.mainUrl,
                actions = actions,
                actionUrls = actionUrls
            )

            val message = Message(
                senderId = "wtc_operador",
                timestamp = System.currentTimeMillis(),
                type = "rich",
                richMessage = richMessage
            )

            anunciosMessagesCol.add(message).await()

            val chatUpdates = mapOf(
                "lastMessage" to campaign.title,
                "lastMessageTimestamp" to message.timestamp
            )
            anunciosChatDoc.update(chatUpdates).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao disparar campanha", e)
            Result.failure(e)
        }
    }
}