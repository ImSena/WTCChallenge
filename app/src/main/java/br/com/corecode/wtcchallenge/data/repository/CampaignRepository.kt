package br.com.corecode.wtcchallenge.data.repository

import android.content.Context
import android.util.Log
import br.com.corecode.wtcchallenge.data.network.RetrofitClient
import br.com.corecode.wtcchallenge.domain.model.Campaign
import br.com.corecode.wtcchallenge.domain.repository.ICampaignRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.lang.Exception

class CampaignRepository(
    context: Context,
    private val sessionRepository: SessionRepository = SessionRepository(context)
) : ICampaignRepository {

    private val campaignService = RetrofitClient.getCampaignService {
        runBlocking(Dispatchers.IO) {
            try {
                sessionRepository.activeJwtToken.first()
            } catch (e: Exception) {
                null
            }
        }
    }

    private val TAG = "CampaignRepository"

    override fun getCampaigns(): Flow<Result<List<Campaign>>> = flow {
        while (true) {
            try {
                val response = campaignService.getAllCampaigns()
                if (response.isSuccessful && response.body() != null) {
                    emit(Result.success(response.body()!!))
                } else {
                    emit(Result.failure(Exception("Erro ao buscar campanhas do servidor: ${response.code()}")))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Falha de conexão de rede nas campanhas", e)
                emit(Result.failure(e))
            }
            delay(5000)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getCampaign(campaignId: String): Result<Campaign?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = campaignService.getCampaignById(campaignId)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body())
                } else {
                    Result.success(null)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao buscar campanha $campaignId via API", e)
                Result.failure(e)
            }
        }
    }

    override suspend fun saveCampaign(campaign: Campaign): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = campaignService.saveAndBroadcastCampaign(campaign)
                if (response.isSuccessful) {
                    Log.d(TAG, "Campanha persistida no MongoDB e disparada via Firebase SDK com sucesso!")
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Falha no servidor ao salvar/disparar campanha: ${response.code()}"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erro de rede ao salvar campanha", e)
                Result.failure(e)
            }
        }
    }

    override suspend fun deleteCampaign(campaignId: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = campaignService.deleteCampaign(campaignId)
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Falha ao deletar no servidor: ${response.code()}"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erro de rede ao deletar campanha", e)
                Result.failure(e)
            }
        }
    }

    suspend fun dispararCampanha(campaign: Campaign): Result<Unit> {
        return saveCampaign(campaign)
    }
}