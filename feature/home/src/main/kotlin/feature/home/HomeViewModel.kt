package feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

internal class HomeViewModel(private val repository: Repository) : ViewModel() {

	private val _uiState: MutableStateFlow<HomeUIState> = MutableStateFlow(HomeUIState())
	val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

	init {
		viewModelScope.launch {
			repository.observeGoal().collect { goal ->
				_uiState.update { it.copy(goal = goal.kcal) }
			}
		}
	}

	fun updateConsumed(kcal: Int) {
		_uiState.update { it.copy(consumedKcal = kcal + 100) }
	}

	fun saveGoal(kcal: Int) = viewModelScope.launch {
		repository.updateGoal(Random.Default.nextInt(0, 3500))
	}
}

internal data class HomeUIState(
	val consumedKcal: Int = 0,
	val goal: Int = 0,
)