package project.entity

data class Food(
	val id: Long = 0,
	val name: String,

	val size: Int,
	val units: String,

	val caloriesKcal: Int,
	val protGrams: Int,
	val fatGrams: Int,
	val carbsGrams: Int,
)