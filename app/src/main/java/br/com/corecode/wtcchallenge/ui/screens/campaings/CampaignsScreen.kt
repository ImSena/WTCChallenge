package br.com.corecode.wtcchallenge.ui.screens.campaings

import androidx.compose.runtime.Composable
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
    if (typeUser == "operador") {
        val operatorNavController = rememberNavController()

        val sampleCampaigns = listOf(
            Campaign("1", "Campanha Especial WTC", "Participe do nosso evento exclusivo...", listOf()),
            Campaign("2", "Innovation Talks: Varejo", "Conecte-se com líderes do setor...", listOf())
        )

        NavHost(navController = operatorNavController, startDestination = "campaign_list") {
            composable("campaign_list") {
                OperatorCampaignsListView(
                    campaigns = sampleCampaigns,
                    onAddClick = { operatorNavController.navigate("create_edit_campaign") },
                    onEditClick = { campaignId -> operatorNavController.navigate("create_edit_campaign/$campaignId") }
                )
            }

            composable("create_edit_campaign") {
                CreateEditCampaignScreen(
                    campaignId = null,
                    onNavigateBack = { operatorNavController.popBackStack() }
                )
            }

            composable(
                route = "create_edit_campaign/{campaignId}",
                arguments = listOf(navArgument("campaignId") { type = NavType.StringType })
            ) { backStackEntry ->
                CreateEditCampaignScreen(
                    campaignId = backStackEntry.arguments?.getString("campaignId"),
                    onNavigateBack = { operatorNavController.popBackStack() }
                )
            }
        }
    } else {
        ClientCompaignsView()
    }
}