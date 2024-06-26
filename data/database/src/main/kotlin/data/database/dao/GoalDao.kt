package data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.database.entity.Goal
import kotlinx.coroutines.flow.Flow

// TODO: Internal
@Dao
interface GoalDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(goal: Goal)

	@Query(
		"""
		SELECT * FROM goals
		WHERE epoch_day <= :epochDay
		ORDER BY epoch_day DESC
		LIMIT 1
		"""
	)
	fun observeCurrentOrPrevGoal(epochDay: Long): Flow<Goal?>
}