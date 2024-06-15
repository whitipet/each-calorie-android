package feature.food

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel

private const val FoodRoute = "food_book_add"

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.foodScreen(
	onCloseScreenAction: (route: String) -> Unit = {},
	sharedTransitionScope: SharedTransitionScope,
) = composable(FoodRoute) {
	val vm: FoodViewModel = koinNavViewModel()
	val uiState = vm.uiState.collectAsStateWithLifecycle()
	FoodScreen(
		state = uiState.value,
		updateNameAction = vm::updateName,
		onSaveAction = {
			vm.saveFood()
			onCloseScreenAction(FoodRoute)
		},
		onCloseAction = { onCloseScreenAction(FoodRoute) },
		sharedTransitionScope = sharedTransitionScope,
		animatedVisibilityScope = this@composable,
	)
}

fun NavController.showFoodScreen() = navigate(FoodRoute)