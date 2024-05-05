package data

import data.database.data_source.ConsumptionDatabaseDataSource
import data.database.data_source.GoalDatabaseDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import project.entity.Consumption
import project.entity.Goal
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

// TODO: Internal
class Repository(
	private val goalDataSource: GoalDatabaseDataSource,
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

	//region Consumption
	suspend fun saveConsumption(consumption: Consumption) = withContext(dispatcher) {
		consumptionDataSource.saveConsumption(
			data.database.entity.Consumption(
				epochSeconds = consumption.time.epochSecond,
				kcal = consumption.kcal
			)
		)
	}

	fun observeConsumption(id: Long): Flow<Consumption> = consumptionDataSource.observeConsumption(id)
		.filterNotNull()
		.map { Consumption(it.id, Instant.ofEpochSecond(it.epochSeconds), it.kcal) }

	fun observeConsumptions(date: LocalDate): Flow<List<Consumption>> =
		consumptionDataSource.observeConsumptions(date.atStartOfDay(ZoneOffset.UTC).toEpochSecond())
			.map {
				it.map { c -> Consumption(c.id, Instant.ofEpochSecond(c.epochSeconds), c.kcal) }
			}
	//endregion Consumption
}