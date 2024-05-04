package data

import data.database.data_source.GoalDatabaseDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import project.entity.Goal
import java.time.LocalDate

// TODO: Internal
class Repository(
	private val goalDataSource: GoalDatabaseDataSource,
	private val dispatcher: CoroutineDispatcher,
) {

	//region Goal
	private companion object {
		val defaultFallbackGoal: Goal = Goal(0, 2500)
	}

	suspend fun setDefaultGoal(kcal: Int) = withContext(dispatcher) {
		goalDataSource.saveGoal(data.database.entity.Goal(0, kcal))
	}

	suspend fun updateGoal(kcal: Int, date: LocalDate = LocalDate.now()) = withContext(dispatcher) {
		goalDataSource.saveGoal(data.database.entity.Goal(date.toEpochDay(), kcal))
	}

	fun observeGoal(date: LocalDate = LocalDate.now()): Flow<Goal> = goalDataSource.observeGoal(date.toEpochDay())
		.map {
			if (it != null) Goal(it.epochDay, it.kcal)
			else defaultFallbackGoal
		}
	//endregion Goal
}