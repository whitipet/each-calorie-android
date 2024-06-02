package data.database.data_source

import data.database.dao.FoodDao
import data.database.entity.Food
import kotlinx.coroutines.flow.Flow

// TODO: Internal
class FoodDatabaseDataSource(private val foodDao: FoodDao) {

	suspend fun saveFood(food: Food): Long = foodDao.insert(food)

	fun observeFoods(): Flow<List<Food>> = foodDao.observeFoods()
}