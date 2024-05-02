package data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.database.entity.Goal
import kotlinx.coroutines.flow.Flow

// TODO: Internal
@Dao
interface GoalsDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(roomCollection: Goal)

	@Query(
		"""
		SELECT * FROM goals 
		WHERE epoch_day <= :epochDay
		ORDER BY epoch_day DESC
		LIMIT 1
		"""
	)
	fun observeGoal(epochDay: Long): Flow<Goal?>
}