package feature.food_book_add

import androidx.lifecycle.ViewModel
import data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class FoodBookAddViewModel(private val repository: Repository) : ViewModel() {

	private val _uiState: MutableStateFlow<FoodBookAddUIState> = MutableStateFlow(FoodBookAddUIState())
	val uiState: StateFlow<FoodBookAddUIState> = _uiState.asStateFlow()
}

internal data class FoodBookAddUIState(
	val name: String = "",
)