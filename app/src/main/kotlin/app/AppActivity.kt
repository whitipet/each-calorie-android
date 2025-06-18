package app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import feature.consumption.consumptionScreen
import feature.consumption.navigateToConsumption
import feature.food.foodScreen
import feature.food.showFoodScreen
import feature.food_book.foodBookScreen
import feature.food_book.showFoodBookScreen
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
			onGoalAction = { navController.showSetGoalDialog() },
			onConsumptionAction = { navController.navigateToConsumption(it) },
			onFoodBookAction = { navController.showFoodBookScreen() },
			onAddConsumptionAction = { navController.navigateToConsumption() },
		)
		consumptionScreen(
			onCloseScreenAction = { navController.closeScreen(it) },
		)
		setGoalDialog(
			onCloseScreenAction = { navController.closeScreen(it) }
		)
		foodBookScreen(
			onCloseScreenAction = { navController.closeScreen(it) },
			onAddAction = { navController.showFoodScreen() },
		)
		foodScreen(
			onCloseScreenAction = { navController.closeScreen(it) },
		)
	}
}

private fun NavHostController.closeScreen(route: String) {
	if (!popBackStack(route, inclusive = true, saveState = true)) currentDestination?.id?.let { navigate(it) }
}