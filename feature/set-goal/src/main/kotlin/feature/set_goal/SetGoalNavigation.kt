package feature.set_goal

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import org.koin.androidx.compose.navigation.koinNavViewModel

private const val SetGoalRoute = "set_goal"

fun NavGraphBuilder.setGoalDialog(
	navController: NavController,
) = dialog(SetGoalRoute) {
	fun closeScreen() {
		// FIXME: Lag during fast click
		if (!navController.popBackStack(SetGoalRoute, inclusive = true, saveState = true))
			navController.currentDestination?.id?.let { navController.navigate(it) }
	}

	val vm: SetGoalModel = koinNavViewModel()
	val uiState = vm.uiState.collectAsStateWithLifecycle()
	SetGoalDialog(
		uiState = uiState,
		updateKcalAction = vm::updateKcal,
		onDismissAction = ::closeScreen,
		onSaveAction = {
			vm.saveGoal()
			closeScreen()
		},
	)
}

fun NavController.showSetGoalDialog() = navigate(SetGoalRoute)