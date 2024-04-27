package feature.home

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel

const val HomeRoute = "home"

fun NavGraphBuilder.homeScreen(
	onAddAction: () -> Unit,
) = composable(HomeRoute) {
	val vm: HomeViewModel = koinNavViewModel()
	val homeUIState = vm.uiState.collectAsStateWithLifecycle()
	HomeScreen(
		state = homeUIState,
		onAddAction = { onAddAction() },
		onUpdateGoalAction = { vm.updateCurrent(it) },
		onUpdateCurrentAction = { vm.saveGoal(it) }
	)
}