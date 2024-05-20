package feature.food_book_add

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val foodBookAddModule = module {
	viewModelOf(::FoodBookAddViewModel)
}