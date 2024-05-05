package feature.consumption

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val consumptionModule = module {
	viewModelOf(::ConsumptionViewModel)
}