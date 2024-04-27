package data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import project.entity.Goal
import java.time.LocalDate

class Repository(
	private val dispatcher: CoroutineDispatcher,
) {

	private val goals: MutableSharedFlow<Goal> = MutableSharedFlow()

	fun observeGoal(date: LocalDate = LocalDate.now()): Flow<Goal> = goals

	suspend fun saveGoal(calories: Int, date: LocalDate = LocalDate.now()) = withContext(dispatcher) {
		goals.emit(Goal(calories, date))
	}
}