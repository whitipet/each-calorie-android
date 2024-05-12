package feature.food_book

import androidx.lifecycle.ViewModel
import data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class FoodBookViewModel(
	private val repository: Repository,
) : ViewModel() {

	private val _uiState: MutableStateFlow<FoodBookUIState> = MutableStateFlow(FoodBookUIState())
	val uiState: StateFlow<FoodBookUIState> = _uiState.asStateFlow()
}

internal data class FoodBookUIState(
	val string: String? = null,
)