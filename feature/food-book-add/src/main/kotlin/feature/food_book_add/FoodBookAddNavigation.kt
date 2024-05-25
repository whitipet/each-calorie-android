package feature.food_book_add

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel

private const val FoodBookAddRoute = "food_book_add"

fun NavGraphBuilder.foodBookAddScreen(
	onCloseScreenAction: (route: String) -> Unit = {},
) = composable(FoodBookAddRoute) {
	val vm: FoodBookAddViewModel = koinNavViewModel()
	val uiState = vm.uiState.collectAsStateWithLifecycle()
	FoodBookAddScreen(
		uiState = uiState,
		updateNameAction = vm::updateName,
		onSaveAction = { onCloseScreenAction(FoodBookAddRoute) },
		onCloseAction = { onCloseScreenAction(FoodBookAddRoute) },
	)
}

fun NavController.showFoodBookAddScreen() = navigate(FoodBookAddRoute)