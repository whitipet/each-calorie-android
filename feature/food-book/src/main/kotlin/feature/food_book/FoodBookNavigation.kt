package feature.food_book

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import org.koin.androidx.compose.navigation.koinNavViewModel

private const val FoodBookRoute = "food_book"

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.foodBookScreen(
	onCloseScreenAction: (route: String) -> Unit = {},
	onAddAction: () -> Unit = {},
	sharedTransitionScope: SharedTransitionScope,
) = composable(
	route = FoodBookRoute,
	deepLinks = listOf(navDeepLink {
		uriPattern = "ec://${FoodBookRoute}"
	})
) {
	val vm: FoodBookViewModel = koinNavViewModel()
	FoodBookScreen(
		uiState = vm.uiState.collectAsStateWithLifecycle(),
		onBackAction = { onCloseScreenAction(FoodBookRoute) },
		onAddAction = onAddAction,
		sharedTransitionScope = sharedTransitionScope,
		animatedVisibilityScope = this@composable,
	)
}

fun NavController.showFoodBookScreen() = navigate(FoodBookRoute)