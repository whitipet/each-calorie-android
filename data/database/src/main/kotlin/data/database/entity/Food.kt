package data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import data.database.Table

@Entity(tableName = Table.FOODS)
data class Food(
	@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
	@ColumnInfo(name = "name") val name: String,

	@ColumnInfo(name = "size") val size: Int,
	@ColumnInfo(name = "units") val units: String,

	@ColumnInfo(name = "caloriesKcal") val caloriesKcal: Int,
	@ColumnInfo(name = "protGrams") val protGrams: Int,
	@ColumnInfo(name = "fatGrams") val fatGrams: Int,
	@ColumnInfo(name = "carbsGrams") val carbsGrams: Int,
)