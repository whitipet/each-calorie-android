package feature.consumption

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel

const val ConsumptionRoute = "consumption"

fun NavGraphBuilder.consumptionScreen(
	navController: NavController,
) = composable(ConsumptionRoute) {
	val vm: ConsumptionViewModel = koinNavViewModel()
	val uiState = vm.uiState.collectAsStateWithLifecycle()

	fun closeScreen() {
		// FIXME: Lag during fast click
		if (!navController.popBackStack(ConsumptionRoute, inclusive = true, saveState = true))
			navController.currentDestination?.id?.let { navController.navigate(it) }
	}

	ConsumptionScreen(
		uiState = uiState,
		onBackAction = ::closeScreen,
		updateKcalAction = vm::updateKcal,
		onSaveAction = {
			vm.saveConsumption()
			closeScreen()
		}
	)
}

fun NavController.navigateToConsumption() = navigate(ConsumptionRoute)