package feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel

const val HomeRoute = "home"

fun NavGraphBuilder.homeScreen(
	onAddAction: () -> Unit,
) = composable(HomeRoute) {
	val vm: HomeViewModel = koinNavViewModel()
	HomeScreen(onAddAction = { onAddAction() })
}