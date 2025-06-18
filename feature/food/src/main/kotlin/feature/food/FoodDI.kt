package feature.food

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val foodModule = module {
	viewModelOf(::FoodViewModel)
}