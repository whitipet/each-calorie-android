package data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.database.Table
import data.database.entity.Food
import kotlinx.coroutines.flow.Flow

// TODO: Internal
@Dao
interface FoodDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(food: Food): Long

	@Query(
		"""
		SELECT * FROM ${Table.FOODS}
		"""
	)
	fun observeFoods(): Flow<List<Food>>
}