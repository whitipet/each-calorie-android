package feature.food_book_add

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import org.koin.androidx.compose.navigation.koinNavViewModel

private const val FoodBookAddRoute = "food_book_add"

fun NavGraphBuilder.foodBookAddDialog(
	onCloseScreenAction: (route: String) -> Unit = {},
) = dialog(FoodBookAddRoute) {
	val vm: FoodBookAddViewModel = koinNavViewModel()
	val uiState = vm.uiState.collectAsStateWithLifecycle()
	FoodBookAddDialog(
		uiState = uiState,
		onDismissAction = { onCloseScreenAction(FoodBookAddRoute) },
	)
}

fun NavController.showFoodBookAddDialog() = navigate(FoodBookAddRoute)