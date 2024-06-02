package data

import data.database.data_source.ConsumptionDatabaseDataSource
import data.database.data_source.FoodDatabaseDataSource
import data.database.data_source.GoalDatabaseDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import project.entity.Consumption
import project.entity.Food
import project.entity.Goal
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

// TODO: Internal
class Repository(
	private val goalDataSource: GoalDatabaseDataSource,
	private val foodDataSource: FoodDatabaseDataSource,
	private val consumptionDataSource: ConsumptionDatabaseDataSource,
	private val dispatcher: CoroutineDispatcher,
) {

	//region Goal
	private companion object {
		val defaultFallbackGoal: Goal = Goal(0, 2500)
	}

	suspend fun setDefaultGoal(kcal: Int) = withContext(dispatcher) {
		goalDataSource.saveGoal(data.database.entity.Goal(0, kcal))
	}

	suspend fun updateGoal(kcal: Int, date: LocalDate = LocalDate.now()) = withContext(dispatcher) {
		goalDataSource.saveGoal(data.database.entity.Goal(date.toEpochDay(), kcal))
	}

	fun observeGoal(date: LocalDate = LocalDate.now()): Flow<Goal> = goalDataSource.observeGoal(date.toEpochDay())
		.map {
			if (it != null) Goal(it.epochDay, it.kcal)
			else defaultFallbackGoal
		}
	//endregion Goal

	//region Food
	suspend fun saveFood(food: Food) = withContext(dispatcher) {
		foodDataSource.saveFood(
			data.database.entity.Food(
				id = food.id,
				name = food.name,
				size = food.size,
				units = food.units,
			)
		)
	}

	fun observeFoods(): Flow<List<Food>> =
		foodDataSource.observeFoods()
			.map { it.map { f -> Food(f.id, f.name, f.size, f.units) } }
	//endregion Food

	//region Consumption
	suspend fun saveConsumption(consumption: Consumption) = withContext(dispatcher) {
		consumptionDataSource.saveConsumption(
			data.database.entity.Consumption(
				id = consumption.id,
				epochSeconds = consumption.time.epochSecond,
				kcal = consumption.kcal
			)
		)
	}

	suspend fun deleteConsumption(id: Long) = withContext(dispatcher) {
		consumptionDataSource.deleteConsumption(id)
	}

	fun observeConsumption(id: Long): Flow<Consumption> = consumptionDataSource.observeConsumption(id)
		.filterNotNull()
		.map { Consumption(it.id, Instant.ofEpochSecond(it.epochSeconds), it.kcal) }

	fun observeConsumptions(date: LocalDate): Flow<List<Consumption>> =
		consumptionDataSource.observeConsumptions(date.atStartOfDay(ZoneOffset.UTC).toEpochSecond())
			.map { it.map { c -> Consumption(c.id, Instant.ofEpochSecond(c.epochSeconds), c.kcal) } }
	//endregion Consumption
}