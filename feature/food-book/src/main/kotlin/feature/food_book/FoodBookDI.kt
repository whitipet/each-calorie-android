package feature.food_book

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val foodBookModule = module {
	viewModelOf(::FoodBookViewModel)
}