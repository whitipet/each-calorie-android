package data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.database.Table
import data.database.entity.Goal
import kotlinx.coroutines.flow.Flow

// TODO: Internal
@Dao
interface GoalsDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(roomCollection: Goal)

	@Query(
		"""
		SELECT * FROM ${Table.GOALS} 
		WHERE epoch_day = :epochDay
		"""
	)
	fun observeGoal(epochDay: Long): Flow<Goal?>
}