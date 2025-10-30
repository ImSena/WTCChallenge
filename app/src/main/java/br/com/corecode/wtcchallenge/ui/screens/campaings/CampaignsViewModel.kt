package br.com.corecode.wtcchallenge.ui.screens.campaings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.corecode.wtcchallenge.data.repository.CampaignRepository
import br.com.corecode.wtcchallenge.data.repository.SessionRepository
import br.com.corecode.wtcchallenge.domain.model.Campaign
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CampaignsViewModel(
    private val sessionRepository: SessionRepository,
    private val campaignRepository: CampaignRepository
): ViewModel() {

    private val _campaignsState = MutableStateFlow<Result<List<Campaign>>>(Result.success(emptyList()))
    val campaignsState: StateFlow<Result<List<Campaign>>> = _campaignsState.asStateFlow()

    private val _selectedCampaign = MutableStateFlow<Result<Campaign?>>(Result.success(null))
    val selectedCampaign: StateFlow<Result<Campaign?>> = _selectedCampaign.asStateFlow()

    val userRole: StateFlow<String?> = sessionRepository.activeUserRole
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    init{
        observeCampaigns()
    }

    private fun observeCampaigns(){
        viewModelScope.launch {
            campaignRepository.getCampaigns()
                .catch { e->
                    _campaignsState.value = Result.failure(e)
                }
                .collect { result ->
                    _campaignsState.value = result
                }
        }
    }

    fun getCampaignById(campaignId: String){
        if (campaignId.isEmpty()) {
            _selectedCampaign.value = Result.success(null)
            return
        }

        viewModelScope.launch {
            val result = campaignRepository.getCampaign(campaignId)
            _selectedCampaign.value = result
        }
    }

    fun clearSelectedCampaign() {
        _selectedCampaign.value = Result.success(null)
    }

    fun saveCampaign(campaign: Campaign, onComplete: (Result<Unit>) -> Unit = {}){
        viewModelScope.launch {
            val result = campaignRepository.saveCampaign(campaign)
            onComplete(result)
        }
    }

    fun deleteCampaign(campaignId: String, onComplete: (Result<Unit>) -> Unit = {}){
        viewModelScope.launch {
            val result = campaignRepository.deleteCampaign(campaignId)
            onComplete(result)
        }
    }

    fun dispararCampanha(campaign: Campaign, onComplete: (Result<Unit>) -> Unit) {
        viewModelScope.launch {
            val result = campaignRepository.dispararCampanha(campaign)
            onComplete(result)
        }
    }
}