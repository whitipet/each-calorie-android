package feature.food_book_add

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel

private const val FoodBookAddRoute = "food_book_add"

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.foodBookAddScreen(
	onCloseScreenAction: (route: String) -> Unit = {},
	sharedTransitionScope: SharedTransitionScope,
) = composable(FoodBookAddRoute) {
	val vm: FoodBookAddViewModel = koinNavViewModel()
	val uiState = vm.uiState.collectAsStateWithLifecycle()
	FoodBookAddScreen(
		uiState = uiState,
		updateNameAction = vm::updateName,
		onSaveAction = { onCloseScreenAction(FoodBookAddRoute) },
		onCloseAction = { onCloseScreenAction(FoodBookAddRoute) },
		sharedTransitionScope = sharedTransitionScope,
		animatedVisibilityScope = this@composable,
	)
}

fun NavController.showFoodBookAddScreen() = navigate(FoodBookAddRoute)