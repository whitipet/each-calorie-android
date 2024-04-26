package feature.home

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
	viewModelOf(::HomeViewModel)
}