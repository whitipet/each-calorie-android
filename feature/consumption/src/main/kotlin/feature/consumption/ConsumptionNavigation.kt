package feature.consumption

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import org.koin.androidx.compose.navigation.koinNavViewModel

private const val ConsumptionRouteBase = "consumption"
private const val argId = "id"

fun NavGraphBuilder.consumptionScreen(navController: NavController) = composable(
	"$ConsumptionRouteBase?$argId={$argId}",
	deepLinks = listOf(navDeepLink {
		uriPattern = "ec://${ConsumptionRouteBase}"
	})
) {
	fun closeScreen() {
		// FIXME: Lag during fast click
		if (!navController.popBackStack(ConsumptionRouteBase, inclusive = true, saveState = true))
			navController.currentDestination?.id?.let { navController.navigate(it) }
	}

	val vm: ConsumptionViewModel = koinNavViewModel()
	val uiState = vm.uiState.collectAsStateWithLifecycle()
	ConsumptionScreen(
		uiState = uiState,
		onBackAction = ::closeScreen,
		updateKcalAction = vm::updateKcal,
		onSaveAction = {
			vm.saveConsumption()
			closeScreen()
		},
		onDeleteAction = {
			vm.deleteConsumption()
			closeScreen()
		}
	)
}

fun NavController.navigateToConsumption(id: Long? = null) = navigate("$ConsumptionRouteBase?$argId=${id.toString()}")

internal class ConsumptionArgs(val id: Long?) {
	constructor(savedStateHandle: SavedStateHandle) : this(savedStateHandle.get<String>(argId)?.toLongOrNull())
}