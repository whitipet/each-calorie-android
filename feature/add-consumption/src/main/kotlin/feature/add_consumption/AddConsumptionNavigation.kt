package feature.add_consumption

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val AddConsumptionRoute = "add_consumption"

fun NavGraphBuilder.addConsumptionScreen() = composable(AddConsumptionRoute) {
	AddConsumptionScreen()
}

fun NavController.navigateToAddConsumption() = navigate(AddConsumptionRoute)