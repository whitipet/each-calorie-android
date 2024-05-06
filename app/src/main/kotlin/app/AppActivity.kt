package app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import feature.consumption.consumptionScreen
import feature.consumption.navigateToConsumption
import feature.home.HomeRoute
import feature.home.homeScreen
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
		startDestination = HomeRoute,
	) {
		homeScreen(
			onAddAction = { navController.navigateToConsumption() },
			onConsumptionAction = { navController.navigateToConsumption(it) },
		)
		consumptionScreen(navController)
		statsScreen()
	}
}