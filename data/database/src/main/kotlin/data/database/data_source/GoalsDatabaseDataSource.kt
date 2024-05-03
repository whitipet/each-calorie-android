package data.database.data_source

import data.database.dao.GoalsDao
import data.database.entity.Goal
import kotlinx.coroutines.flow.Flow

// TODO: Internal
class GoalsDatabaseDataSource(
	private val goalsDao: GoalsDao,
) {

	suspend fun updateGoal(goal: Goal) = goalsDao.insert(goal)

	fun observeGoal(epochDay: Long): Flow<Goal?> = goalsDao.observeCurrentOrPrevGoal(epochDay)
}