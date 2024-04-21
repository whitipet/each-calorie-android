package feature.stats

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val StatsRoute = "stats"

fun NavGraphBuilder.statsScreen(
	onNavigateToAdd: () -> Unit,
) {
	composable(StatsRoute) {
		StatsScreen(onAddAction = { onNavigateToAdd() })
	}
}

fun NavController.navigateToStats() = this.navigate(StatsRoute)