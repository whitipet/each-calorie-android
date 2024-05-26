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

fun NavGraphBuilder.consumptionScreen(
	onCloseScreenAction: (route: String) -> Unit = {},
) = composable(
	"$ConsumptionRouteBase?$argId={$argId}",
	deepLinks = listOf(navDeepLink {
		uriPattern = "ec://${ConsumptionRouteBase}"
	})
) {
	val vm: ConsumptionViewModel = koinNavViewModel()
	val uiState = vm.uiState.collectAsStateWithLifecycle()
	ConsumptionScreen(
		uiState = uiState,
		onCloseAction = { onCloseScreenAction(ConsumptionRouteBase) },
		updateKcalAction = vm::updateKcal,
		onSaveAction = {
			vm.saveConsumption()
			onCloseScreenAction(ConsumptionRouteBase)
		},
		onDeleteAction = {
			vm.deleteConsumption()
			onCloseScreenAction(ConsumptionRouteBase)
		}
	)
}

fun NavController.navigateToConsumption(id: Long? = null) = navigate("$ConsumptionRouteBase?$argId=${id.toString()}")

internal class ConsumptionArgs(val id: Long?) {
	constructor(savedStateHandle: SavedStateHandle) : this(savedStateHandle.get<String>(argId)?.toLongOrNull())
}