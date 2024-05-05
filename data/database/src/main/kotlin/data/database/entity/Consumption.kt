package data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import data.database.Table

@Entity(tableName = Table.CONSUMPTIONS)
data class Consumption(
	@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
	@ColumnInfo(name = "epoch_seconds") val epochSeconds: Long,
	@ColumnInfo(name = "kcal") val kcal: Int,
)