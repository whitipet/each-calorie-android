package feature.set_goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SetGoalModel(private val repository: Repository) : ViewModel() {

	private val _uiState: MutableStateFlow<SetGoalUIState> = MutableStateFlow(SetGoalUIState())
	val uiState: StateFlow<SetGoalUIState> = _uiState.asStateFlow()

	init {
		viewModelScope.launch {
			repository.observeGoal().collect { goal ->
				_uiState.update { it.copy(kcal = goal.kcal, isError = goal.kcal <= 0) }
			}
		}
	}

	fun updateKcal(kcal: Int) = _uiState.update {
		it.copy(kcal = kcal, isError = kcal <= 0)
	}

	fun saveGoal() = viewModelScope.launch {
		repository.updateGoal(uiState.value.kcal)
	}
}

internal data class SetGoalUIState(
	val kcal: Int = 0,
	val isError: Boolean = true,
)