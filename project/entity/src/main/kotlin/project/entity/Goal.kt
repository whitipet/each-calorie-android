package project.entity

import java.time.LocalDate

data class Goal(
	val date: LocalDate = LocalDate.now(),
	val calories: Int,
) {
	constructor(epochDay: Long, calories: Int) : this(LocalDate.ofEpochDay(epochDay), calories)
}