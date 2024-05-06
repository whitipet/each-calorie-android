package data.database.data_source

import data.database.dao.GoalDao
import data.database.entity.Goal
import kotlinx.coroutines.flow.Flow

// TODO: Internal
class GoalDatabaseDataSource(private val goalDao: GoalDao) {

	suspend fun saveGoal(goal: Goal) = goalDao.insert(goal)

	fun observeGoal(epochDay: Long): Flow<Goal?> = goalDao.observeCurrentOrPrevGoal(epochDay)
}