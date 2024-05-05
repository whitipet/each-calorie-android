package project.entity

import java.time.Instant

data class Consumption(
	val id: Long = 0,
	val time: Instant,
	val kcal: Int,
)