package br.com.corecode.wtcchallenge.ui.screens.campaings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.corecode.wtcchallenge.data.repository.CampaignRepository // IMPORTAR
import br.com.corecode.wtcchallenge.data.repository.SessionRepository

class CampaignsViewModelFactory(
    private val sessionRepository: SessionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CampaignsViewModel::class.java)) {
            return CampaignsViewModel(
                sessionRepository = sessionRepository,
                campaignRepository = CampaignRepository() // INJETADO AQUI
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}