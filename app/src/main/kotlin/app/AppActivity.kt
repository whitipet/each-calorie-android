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
import feature.set_goal.setGoalDialog
import feature.set_goal.showSetGoalDialog
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
			onGoalAction = { navController.showSetGoalDialog() },
			onConsumptionAction = { navController.navigateToConsumption(it) },
		)
		consumptionScreen(navController)
		setGoalDialog(navController)
	}
}