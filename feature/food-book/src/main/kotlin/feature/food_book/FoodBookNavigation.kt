package feature.food_book

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel

private const val FoodBookRoute = "food_book"

fun NavGraphBuilder.foodBookScreen(navController: NavController) = composable(FoodBookRoute) {
	fun closeScreen() {
		// FIXME: Lag during fast click
		if (!navController.popBackStack(FoodBookRoute, inclusive = true, saveState = true))
			navController.currentDestination?.id?.let { navController.navigate(it) }
	}

	val vm: FoodBookViewModel = koinNavViewModel()
	FoodBookScreen(
		uiState = vm.uiState.collectAsStateWithLifecycle(),
		onBackAction = { closeScreen() },
		onAddAction = {},
	)
}

fun NavController.showFoodBookScreen() = navigate(FoodBookRoute)