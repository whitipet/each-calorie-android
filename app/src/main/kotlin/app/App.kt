package app

import android.app.Application
import data.dataModule
import feature.consumption.consumptionModule
import feature.home.homeModule
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
			)
		}
	}
}