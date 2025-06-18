package feature.consumption

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val consumptionModule = module {
	viewModelOf(::ConsumptionViewModel)
}