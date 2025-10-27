package br.com.corecode.wtcchallenge.data.repository

import br.com.corecode.wtcchallenge.domain.model.Campaign
import br.com.corecode.wtcchallenge.domain.repository.ICampaignRepository
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class CampaignRepository : ICampaignRepository {

    private val db = Firebase.firestore
    private val campaignsCollection = db.collection("campaigns")

    override fun getCampaigns(): Flow<Result<List<Campaign>>> {
        return campaignsCollection.snapshots().map { snapshot ->
            Result.success(snapshot.toObjects<Campaign>())
        }.catch {
            emit(Result.failure(it))
        }
    }

    override suspend fun getCampaign(campaignId: String): Result<Campaign> {
        return try{
            val document = campaignsCollection.document(campaignId).get().await()
            val campaign = document.toObject(Campaign::class.java)
            if(campaign != null){
                Result.success(campaign)
            }else{
                Result.failure(Exception("Campanha não encontrada."))
            }
        }catch(e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun saveCampaign(campaign: Campaign): Result<Unit> {
        return try{
            campaignsCollection.document(campaign.id).set(campaign).await()
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun deleteCampaign(campaignId: String): Result<Unit> {
        return try{
            campaignsCollection.document(campaignId).delete().await()
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

}