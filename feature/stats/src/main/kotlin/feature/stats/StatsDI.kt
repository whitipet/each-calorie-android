package feature.stats

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val statsModule = module {
	viewModelOf(::StatsViewModel)
}