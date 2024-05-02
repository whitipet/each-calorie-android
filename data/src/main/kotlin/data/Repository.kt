package data

import data.database.data_source.GoalsDatabaseDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import project.entity.Goal
import java.time.LocalDate

// TODO: Internal
class Repository(
	private val goalsDatabaseDataSource: GoalsDatabaseDataSource,
	private val dispatcher: CoroutineDispatcher,
) {

	suspend fun updateGoal(calories: Int, date: LocalDate = LocalDate.now()) = withContext(dispatcher) {
		goalsDatabaseDataSource.updateGoal(data.database.entity.Goal(date.toEpochDay(), calories))
	}

	fun observeGoal(date: LocalDate = LocalDate.now()): Flow<Goal> =
		goalsDatabaseDataSource.observeGoal(date.toEpochDay())
			.map { Goal(it.epochDay, it.calories) }
}