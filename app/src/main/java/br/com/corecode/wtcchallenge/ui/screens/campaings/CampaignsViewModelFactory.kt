package br.com.corecode.wtcchallenge.ui.screens.campaings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.corecode.wtcchallenge.data.repository.CampaignRepository
import br.com.corecode.wtcchallenge.data.repository.SessionRepository

class CampaignsViewModelFactory(
    private val context: Context,
    private val sessionRepository: SessionRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CampaignsViewModel::class.java)) {
            return CampaignsViewModel(
                sessionRepository = sessionRepository,
                campaignRepository = CampaignRepository(context)
            ) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}