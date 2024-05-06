package feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import project.entity.Consumption
import java.time.LocalDate

internal class HomeViewModel(private val repository: Repository) : ViewModel() {

	private val _uiState: MutableStateFlow<HomeUIState> = MutableStateFlow(HomeUIState())
	val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

	init {
		viewModelScope.launch {
			repository.observeGoal().collect { goal ->
				_uiState.update { it.copy(goal = goal.kcal) }
			}
		}

		viewModelScope.launch {
			repository.observeConsumptions(LocalDate.now()).collect { consumptions ->
				_uiState.update {
					it.copy(
						consumedKcal = consumptions.fold(0) { kcal, c -> kcal + c.kcal },
						consumptions = consumptions.sortedByDescending { c -> c.time }
					)
				}
			}
		}
	}
}

internal data class HomeUIState(
	val goal: Int = 0,
	val consumedKcal: Int = 0,
	val consumptions: List<Consumption> = emptyList(),
)