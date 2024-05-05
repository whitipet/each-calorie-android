package feature.consumption

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import project.entity.Consumption
import java.time.Instant

internal class ConsumptionViewModel(private val repository: Repository) : ViewModel() {

	private val _uiState: MutableStateFlow<ConsumptionUIState> = MutableStateFlow(ConsumptionUIState())
	val uiState: StateFlow<ConsumptionUIState> = _uiState.asStateFlow()

	fun saveConsumption() {
		if (uiState.value.kcal < 1) {
			_uiState.update { it.copy(isError = true) }
			return
		}
		viewModelScope.launch {
			repository.saveConsumption(Consumption(time = uiState.value.time, kcal = uiState.value.kcal))
		}
	}

	fun updateKcal(kcal: Int) = _uiState.update {
		it.copy(
			kcal = kcal,
			isError = false
		)
	}
}

internal data class ConsumptionUIState(
	val time: Instant = Instant.now(),
	val kcal: Int = 0,
	val isError: Boolean = false,
)