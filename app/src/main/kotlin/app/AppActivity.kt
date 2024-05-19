package app

import android.content.res.Configuration
import android.os.Bundle
import android.util.TypedValue
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorInt
import androidx.compose.runtime.Composable
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import feature.consumption.consumptionScreen
import feature.consumption.navigateToConsumption
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

		try {
			val windowBackgroundResId = TypedValue().let {
				theme.resolveAttribute(android.R.attr.windowBackground, it, true)
				it.resourceId
			}
			@ColorInt val windowBackgroundColor: Int = resources.getColor(windowBackgroundResId, theme)
			@ColorInt val statusBarColor: Int =
				ColorUtils.setAlphaComponent(windowBackgroundColor, (255 * 0.7f).toInt())

			val isDarkTheme = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
					Configuration.UI_MODE_NIGHT_YES
			enableEdgeToEdge(
				statusBarStyle =
				if (isDarkTheme) SystemBarStyle.dark(statusBarColor)
				else SystemBarStyle.light(statusBarColor, statusBarColor)
			)
		} catch (_: Exception) {
			enableEdgeToEdge()
		}

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
		consumptionScreen { navController.closeScreen(it) }
		setGoalDialog { navController.closeScreen(it) }
		foodBookScreen { navController.closeScreen(it) }
	}
}

private fun NavHostController.closeScreen(route: String) {
	if (!popBackStack(route, inclusive = true, saveState = true)) currentDestination?.id?.let { navigate(it) }
}