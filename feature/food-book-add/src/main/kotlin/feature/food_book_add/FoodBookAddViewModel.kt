package feature.food_book_add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import project.entity.Food

internal class FoodBookAddViewModel(private val repository: Repository) : ViewModel() {

	private val _uiState: MutableStateFlow<FoodBookAddUIState> = MutableStateFlow(FoodBookAddUIState())
	val uiState: StateFlow<FoodBookAddUIState> = _uiState.asStateFlow()

	fun updateName(name: String) = _uiState.update {
		it.copy(name = name)
	}

	fun saveFood() = viewModelScope.launch {
		with(uiState.value) { repository.saveFood(Food(name = name, size = size, units = units)) }
	}
}

internal data class FoodBookAddUIState(
	val name: String = "",
	val size: Int = 100,
	val units: String = "grams",
)