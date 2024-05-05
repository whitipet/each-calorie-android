package data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.database.Table
import data.database.entity.Consumption
import kotlinx.coroutines.flow.Flow

// TODO: Internal
@Dao
interface ConsumptionDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(consumption: Consumption): Long

	@Query(
		"""
		SELECT * FROM ${Table.CONSUMPTIONS}
		WHERE id == :id
		"""
	)
	fun observeConsumption(id: Long): Flow<Consumption?>

	@Query(
		"""
		SELECT * FROM ${Table.CONSUMPTIONS}
		WHERE DATE(epoch_seconds, 'auto') == DATE(:epochDay, 'auto')
		"""
	)
	fun observeConsumptions(epochDay: Long): Flow<List<Consumption>>
}