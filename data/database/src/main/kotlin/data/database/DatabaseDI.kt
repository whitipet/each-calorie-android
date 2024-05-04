package data.database

import data.database.data_source.GoalDatabaseDataSource
import org.koin.dsl.module

val databaseModule = module {

	single<Database> { Database.invoke(get()) }

	single<GoalDatabaseDataSource> {
		GoalDatabaseDataSource(get<Database>().goalDao())
	}
}