package feature.food_book

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import org.koin.androidx.compose.navigation.koinNavViewModel

private const val FoodBookRoute = "food_book"

fun NavGraphBuilder.foodBookScreen(
	onCloseScreenAction: (route: String) -> Unit = {},
	onAddAction: () -> Unit = {},
) = composable(
	route = FoodBookRoute,
	deepLinks = listOf(navDeepLink {
		uriPattern = "ec://${FoodBookRoute}"
	})
) {
	val vm: FoodBookViewModel = koinNavViewModel()
	val uiState = vm.uiState.collectAsStateWithLifecycle()
	FoodBookScreen(
		state = uiState.value,
		onBackAction = { onCloseScreenAction(FoodBookRoute) },
		onAddAction = onAddAction,
	)
}

fun NavController.showFoodBookScreen() = navigate(FoodBookRoute)