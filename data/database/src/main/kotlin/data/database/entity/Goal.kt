package data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import data.database.Table

@Entity(tableName = Table.GOALS)
data class Goal(
	@PrimaryKey @ColumnInfo(name = "epoch_day") val epochDay: Long,
	@ColumnInfo(name = "calories") val calories: Int,
)