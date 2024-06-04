package feature.set_goal

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import org.koin.androidx.compose.navigation.koinNavViewModel

private const val SetGoalRoute = "set_goal"

fun NavGraphBuilder.setGoalDialog(
	onCloseScreenAction: (route: String) -> Unit = {},
) = dialog(SetGoalRoute) {
	val vm: SetGoalModel = koinNavViewModel()
	val uiState = vm.uiState.collectAsStateWithLifecycle()
	SetGoalDialog(
		state = uiState.value,
		updateKcalAction = vm::updateKcal,
		onDismissAction = { onCloseScreenAction(SetGoalRoute) },
		onSaveAction = {
			vm.saveGoal()
			onCloseScreenAction(SetGoalRoute)
		},
	)
}

fun NavController.showSetGoalDialog() = navigate(SetGoalRoute)