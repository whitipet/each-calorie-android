package feature.food

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val foodModule = module {
	viewModelOf(::FoodViewModel)
}