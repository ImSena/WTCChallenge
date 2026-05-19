package br.com.corecode.wtcchallenge.data.network

import br.com.corecode.wtcchallenge.domain.model.Campaign
import retrofit2.Response
import retrofit2.http.*

interface CampaignApiService {

    @GET("/campaigns")
    suspend fun getAllCampaigns(): Response<List<Campaign>>

    @GET("/campaigns/{id}")
    suspend fun getCampaignById(@Path("id") id: String): Response<Campaign>

    @POST("/campaigns")
    suspend fun saveAndBroadcastCampaign(@Body campaign: Campaign): Response<Campaign>

    @DELETE("/campaigns/{id}")
    suspend fun deleteCampaign(@Path("id") id: String): Response<Void>
}