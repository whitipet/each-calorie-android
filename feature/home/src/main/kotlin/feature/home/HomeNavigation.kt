package feature.home

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel

const val HomeRoute = "home"

fun NavGraphBuilder.homeScreen(
	onGoalAction: () -> Unit,
	onConsumptionAction: (consumptionId: Long) -> Unit,
	onFoodBookAction: () -> Unit,
	onAddConsumptionAction: () -> Unit,
) = composable(HomeRoute) {
	val vm: HomeViewModel = koinNavViewModel()
	val uiState = vm.uiState.collectAsStateWithLifecycle()
	HomeScreen(
		uiState = uiState,
		onGoalAction = onGoalAction,
		onConsumptionAction = onConsumptionAction,
		onFoodBookAction = onFoodBookAction,
		onAddConsumptionAction = onAddConsumptionAction,
	)
}