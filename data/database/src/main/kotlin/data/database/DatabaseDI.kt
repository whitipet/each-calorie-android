package data.database

import data.database.data_source.GoalsDatabaseDataSource
import org.koin.dsl.module

val databaseModule = module {

	single<Database> { Database.invoke(get()) }

	single<GoalsDatabaseDataSource> {
		GoalsDatabaseDataSource(
			get<Database>().goalsDao()
		)
	}
}