package feature.home

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel

const val HomeRoute = "home"

fun NavGraphBuilder.homeScreen(
	onAddAction: () -> Unit,
	onGoalAction: () -> Unit,
	onConsumptionAction: (consumptionId: Long) -> Unit,
) = composable(HomeRoute) {
	val vm: HomeViewModel = koinNavViewModel()
	val uiState = vm.uiState.collectAsStateWithLifecycle()
	HomeScreen(
		uiState = uiState,
		onAddAction = onAddAction,
		onGoalAction = onGoalAction,
		onConsumptionAction = onConsumptionAction,
	)
}