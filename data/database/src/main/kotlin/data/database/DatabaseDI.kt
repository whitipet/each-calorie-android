package data.database

import data.database.data_source.ConsumptionDatabaseDataSource
import data.database.data_source.FoodDatabaseDataSource
import data.database.data_source.GoalDatabaseDataSource
import org.koin.dsl.module

val databaseModule = module {

	single<Database> { Database.invoke(get()) }

	single<GoalDatabaseDataSource> { GoalDatabaseDataSource(get<Database>().goalDao()) }
	single<FoodDatabaseDataSource> { FoodDatabaseDataSource(get<Database>().foodDao()) }
	single<ConsumptionDatabaseDataSource> { ConsumptionDatabaseDataSource(get<Database>().consumptionDao()) }
}