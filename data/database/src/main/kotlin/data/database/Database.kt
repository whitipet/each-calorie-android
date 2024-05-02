package data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import data.database.dao.GoalsDao
import data.database.entity.Goal

@Database(
	entities = [
		Goal::class,
	],
	version = 1
)
internal abstract class Database : RoomDatabase() {

	companion object {
		@Volatile
		private var instance: data.database.Database? = null
		private val LOCK = Any()

		operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
			instance ?: buildDatabase(context).also { instance = it }
		}

		private fun buildDatabase(context: Context) = Room
			.databaseBuilder(context, data.database.Database::class.java, "database.db")
			.fallbackToDestructiveMigration(true)
			.build()
	}

	abstract fun goalsDao(): GoalsDao
}