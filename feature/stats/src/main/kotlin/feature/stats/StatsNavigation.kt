package feature.stats

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel

const val StatsRoute = "stats"

fun NavGraphBuilder.statsScreen(
	onAddAction: () -> Unit,
) = composable(StatsRoute) {
	val vm: StatsViewModel = koinNavViewModel()
	StatsScreen(onAddAction = { onAddAction() })
}

fun NavController.navigateToStats() = navigate(StatsRoute)