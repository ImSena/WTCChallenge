package br.com.corecode.wtcchallenge.ui.screens.campaings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.corecode.wtcchallenge.data.repository.CampaignRepository
import br.com.corecode.wtcchallenge.domain.model.Campaign
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CampaignsViewModel(
    private val repository: CampaignRepository = CampaignRepository()
): ViewModel() {

    private val _campaignsState = MutableStateFlow<Result<List<Campaign>>>(Result.success(emptyList()))
    val campaignsState: StateFlow<Result<List<Campaign>>> = _campaignsState.asStateFlow()

    private val _selectedCampaign = MutableStateFlow<Result<Campaign>?>(null)
    val selectedCampaign: StateFlow<Result<Campaign>?> = _selectedCampaign.asStateFlow()

    init{
        observeCampaigns()
    }

    private fun observeCampaigns(){
        viewModelScope.launch {
            repository.getCampaigns()
                .catch { e->
                    _campaignsState.value = Result.failure(e)
                }
                .collect { result ->
                    _campaignsState.value = result
                }
        }
    }

    fun getCampaignById(campaignId: String){
        viewModelScope.launch {
            val result = repository.getCampaign(campaignId)
            _selectedCampaign.value = result
        }
    }

    fun saveCampaign(campaign: Campaign, onComplete: (Result<Unit>) -> Unit = {}){
        viewModelScope.launch {
            val result = repository.saveCampaign(campaign)
            onComplete(result)
        }
    }

    fun deleteCampaign(campaignId: String, onComplete: (Result<Unit>) -> Unit = {}){
        viewModelScope.launch {
            val result = repository.deleteCampaign(campaignId)
            onComplete(result)
        }
    }

}