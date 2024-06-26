package data.database.data_source

import data.database.dao.ConsumptionDao
import data.database.entity.Consumption
import kotlinx.coroutines.flow.Flow

// TODO: Internal
class ConsumptionDatabaseDataSource(private val consumptionDao: ConsumptionDao) {

	suspend fun saveConsumption(consumption: Consumption): Long = consumptionDao.insert(consumption)

	suspend fun deleteConsumption(id: Long) = consumptionDao.delete(id)

	fun observeConsumption(id: Long): Flow<Consumption?> = consumptionDao.observeConsumption(id)

	fun observeConsumptions(epochSecondsAtStartOfDay: Long): Flow<List<Consumption>> =
		consumptionDao.observeConsumptions(epochSecondsAtStartOfDay)
}