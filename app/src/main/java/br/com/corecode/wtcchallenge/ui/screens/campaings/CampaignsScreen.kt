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
import br.com.corecode.wtcchallenge.domain.model.Campaign
import br.com.corecode.wtcchallenge.ui.screens.campaings.views.ClientCompaignsView
import br.com.corecode.wtcchallenge.ui.screens.campaings.views.operator.OperatorCampaignsListView

@Composable
fun CampaignsScreen(typeUser: String = "operador") {
    val context = LocalContext.current
    val viewModel: CampaignsViewModel = viewModel()
    val campaignState by viewModel.campaignsState.collectAsState()
    val campaigns = campaignState.getOrDefault(emptyList())
    if (typeUser == "operador") {
        val operatorNavController = rememberNavController()
        val sampleCampaigns =
        NavHost(navController = operatorNavController, startDestination = "campaign_list") {
            composable("campaign_list") {
                OperatorCampaignsListView(
                    campaigns = campaigns,
                    onAddClick = { operatorNavController.navigate("create_edit_campaign") },
                    onEditClick = { campaignId -> operatorNavController.navigate("create_edit_campaign/$campaignId") },
                    onDeleteClick = {campaignId ->
                        viewModel.deleteCampaign(campaignId){result ->
                            if(result.isSuccess){
                                Toast.makeText(context, "Campanha deletada com sucesso!", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(context, "Erro ao excluir!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )
            }

            composable("create_edit_campaign") {
                CreateEditCampaignScreen(
                    campaignId = null,
                    onNavigateBack = { operatorNavController.popBackStack() },
                    onSaveCampaign = { campaign ->
                        viewModel.saveCampaign(campaign){result ->
                            if(result.isSuccess){
                                Toast.makeText(context, "Campanha criada com sucesso!", Toast.LENGTH_SHORT).show()
                                operatorNavController.popBackStack()
                            }else{
                                Toast.makeText(context, "Erro ao salvar campanha: ${result.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )
            }

            composable(
                route = "create_edit_campaign/{campaignId}",
                arguments = listOf(navArgument("campaignId") { type = NavType.StringType })
            ) { backStackEntry ->
                val campaignId = backStackEntry.arguments?.getString("campaignId")
                CreateEditCampaignScreen(
                    campaignId = campaignId,
                    onNavigateBack = { operatorNavController.popBackStack() },
                    onSaveCampaign = { campaign ->
                        viewModel.saveCampaign(campaign){result ->
                            if(result.isSuccess){
                                Toast.makeText(context, "Campanha atualizada!", Toast.LENGTH_SHORT).show()
                                operatorNavController.popBackStack()
                            }else{
                                Toast.makeText(context, "Erro ao atualizar: ${result.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                )
            }
        }
    } else {
        ClientCompaignsView(campaigns)
    }
}