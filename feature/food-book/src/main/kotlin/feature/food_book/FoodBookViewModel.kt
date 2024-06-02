package feature.food_book

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import project.entity.Food

internal class FoodBookViewModel(
	private val repository: Repository,
) : ViewModel() {

	private val _uiState: MutableStateFlow<FoodBookUIState> = MutableStateFlow(FoodBookUIState())
	val uiState: StateFlow<FoodBookUIState> = _uiState.asStateFlow()

	init {
		viewModelScope.launch {
			repository.observeFoods().collect { foods ->
				Log.d("FoodBookViewModel", "FoodBookViewModel: ${foods}")
				_uiState.update { it.copy(foods = foods) }
			}
		}
	}
}

internal data class FoodBookUIState(
	val foods: List<Food> = emptyList(),
)