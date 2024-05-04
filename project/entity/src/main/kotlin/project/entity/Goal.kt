package project.entity

import java.time.LocalDate

data class Goal(
	val date: LocalDate,
	val kcal: Int,
) {
	constructor(epochDay: Long, kcal: Int) : this(LocalDate.ofEpochDay(epochDay), kcal)
}