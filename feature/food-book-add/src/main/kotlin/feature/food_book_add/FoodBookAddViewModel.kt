package feature.food_book_add

import androidx.lifecycle.ViewModel
import data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class FoodBookAddViewModel(private val repository: Repository) : ViewModel() {

	private val _uiState: MutableStateFlow<FoodBookAddUIState> = MutableStateFlow(FoodBookAddUIState())
	val uiState: StateFlow<FoodBookAddUIState> = _uiState.asStateFlow()

	fun updateName(name: String) = _uiState.update {
		it.copy(name = name)
	}
}

internal data class FoodBookAddUIState(
	val name: String = "",
)