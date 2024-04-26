package feature.stats

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val StatsRoute = "stats"

fun NavGraphBuilder.statsScreen() = composable(StatsRoute) {
	StatsScreen()
}

fun NavController.navigateToStats() = navigate(StatsRoute)