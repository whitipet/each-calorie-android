package app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import feature.add_consumption.addConsumptionScreen
import feature.add_consumption.navigateToAddConsumption
import feature.stats.StatsRoute
import feature.stats.statsScreen
import project.ui.theme.Theme

class AppActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent { Theme { App() } }
	}
}

@Composable
private fun App() {
	val navController = rememberNavController()
	NavHost(
		navController = navController,
		startDestination = StatsRoute,
	) {
		statsScreen(
			onNavigateToAdd = {
				navController.navigateToAddConsumption()
			},
		)

		addConsumptionScreen()
	}
}