package br.com.corecode.wtcchallenge.domain.repository

import br.com.corecode.wtcchallenge.domain.model.Campaign
import kotlinx.coroutines.flow.Flow

interface ICampaignRepository {

    fun getCampaigns(): Flow<Result<List<Campaign>>>
    suspend fun getCampaign(campaignId: String): Result<Campaign?>
    suspend fun saveCampaign(campaign: Campaign): Result<Unit>
    suspend fun deleteCampaign(campaignId: String): Result<Unit>

}