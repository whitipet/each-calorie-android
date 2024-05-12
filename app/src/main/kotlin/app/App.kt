package app

import android.app.Application
import data.dataModule
import feature.consumption.consumptionModule
import feature.food_book.foodBookModule
import feature.home.homeModule
import feature.set_goal.setGoalModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class Application : Application() {
	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@Application)
			modules(
				dataModule,

				homeModule,
				consumptionModule,
				setGoalModule,
				foodBookModule,
			)
		}
	}
}