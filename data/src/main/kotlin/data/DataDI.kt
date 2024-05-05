package data

import data.database.databaseModule
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
	includes(databaseModule)

	single<Repository> { Repository(get(), get(), Dispatchers.IO) }
}