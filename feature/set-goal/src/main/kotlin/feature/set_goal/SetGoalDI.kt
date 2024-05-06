package feature.set_goal

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val setGoalModule = module {
	viewModelOf(::SetGoalModel)
}