package data

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
	single<Repository> { Repository(Dispatchers.IO) }
}