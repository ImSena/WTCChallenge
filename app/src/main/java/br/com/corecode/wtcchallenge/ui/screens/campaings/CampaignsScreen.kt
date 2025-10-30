package br.com.corecode.wtcchallenge.ui.screens.campaings

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.corecode.wtcchallenge.data.repository.SessionRepository
import br.com.corecode.wtcchallenge.domain.model.Campaign
import br.com.corecode.wtcchallenge.ui.screens.campaings.views.ClientCompaignsView
import br.com.corecode.wtcchallenge.ui.screens.campaings.views.operator.OperatorCampaignsListView

@Composable
fun CampaignsScreen() {
    val context = LocalContext.current
    val sessionRepo = SessionRepository(context)
    val viewModel: CampaignsViewModel = viewModel(
        factory = CampaignsViewModelFactory(sessionRepo)
    )
    val campaignState by viewModel.campaignsState.collectAsState()
    val campaigns = campaignState.getOrDefault(emptyList())

    val userRole by viewModel.userRole.collectAsState()

    if(userRole != null){
        if (userRole == "operador") {
            val operatorNavController = rememberNavController()

            NavHost(navController = operatorNavController, startDestination = "campaign_list") {
                composable("campaign_list") {
                    OperatorCampaignsListView(
                        campaigns = campaigns,
                        onAddClick = { operatorNavController.navigate("create_edit_campaign") },
                        onEditClick = { campaignId -> operatorNavController.navigate("create_edit_campaign/$campaignId") },
                        onDeleteClick = {campaignId ->
                            viewModel.deleteCampaign(campaignId){result ->
                                if(result.isSuccess){
                                    Toast.makeText(context, "Campanha deletada!", Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(context, "Erro ao excluir!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },

                        onDispararClick = { campaign ->
                            viewModel.dispararCampanha(campaign) { result ->
                                if (result.isSuccess) {
                                    Toast.makeText(context, "Campanha disparada!", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Erro ao disparar: ${result.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    )
                }

                composable("create_edit_campaign") {
                    CreateEditCampaignScreen(
                        campaignId = null,
                        viewModel = viewModel,
                        onNavigateBack = { operatorNavController.popBackStack() },
                    )
                }

                composable(
                    route = "create_edit_campaign/{campaignId}",
                    arguments = listOf(navArgument("campaignId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val campaignId = backStackEntry.arguments?.getString("campaignId")
                    CreateEditCampaignScreen(
                        campaignId = campaignId,
                        viewModel = viewModel,
                        onNavigateBack = { operatorNavController.popBackStack() },
                    )
                }
            }
        } else {
            ClientCompaignsView(campaigns)
        }
    }
}