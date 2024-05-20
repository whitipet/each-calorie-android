package feature.food_book

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel

private const val FoodBookRoute = "food_book"

fun NavGraphBuilder.foodBookScreen(
	onCloseScreenAction: (route: String) -> Unit = {},
	onAddAction: () -> Unit = {},
) = composable(FoodBookRoute) {
	val vm: FoodBookViewModel = koinNavViewModel()
	FoodBookScreen(
		uiState = vm.uiState.collectAsStateWithLifecycle(),
		onBackAction = { onCloseScreenAction(FoodBookRoute) },
		onAddAction = onAddAction,
	)
}

fun NavController.showFoodBookScreen() = navigate(FoodBookRoute)