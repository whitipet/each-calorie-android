package feature.food_book

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val foodBookModule = module {
	viewModelOf(::FoodBookViewModel)
}