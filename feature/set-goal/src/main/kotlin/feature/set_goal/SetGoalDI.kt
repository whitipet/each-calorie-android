package feature.set_goal

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val setGoalModule = module {
	viewModelOf(::SetGoalModel)
}