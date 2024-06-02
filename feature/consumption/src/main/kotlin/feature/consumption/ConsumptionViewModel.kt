package feature.consumption

import androidx.lifecycle.SavedStateHandle
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

internal class ConsumptionViewModel(
	savedStateHandle: SavedStateHandle,
	private val repository: Repository,
) : ViewModel() {

	private val args = ConsumptionArgs(savedStateHandle)

	private val _uiState: MutableStateFlow<ConsumptionUIState> = MutableStateFlow(ConsumptionUIState())
	val uiState: StateFlow<ConsumptionUIState> = _uiState.asStateFlow()

	init {
		args.id?.let { id ->
			viewModelScope.launch {
				repository.observeConsumption(id).collect { c ->
					_uiState.update { it.copy(id = c.id, time = c.time, kcal = c.kcal) }
				}
			}
		}
	}

	fun saveConsumption() = viewModelScope.launch {
		with(uiState.value) { repository.saveConsumption(Consumption(id = id ?: 0, time = time, kcal = kcal)) }
	}

	fun deleteConsumption() =
		uiState.value.id?.let { id ->
			viewModelScope.launch {
				repository.deleteConsumption(id)
			}
		}

	fun updateKcal(kcal: Int) = _uiState.update {
		it.copy(
			kcal = kcal,
			isError = kcal < 1
		)
	}
}

internal data class ConsumptionUIState(
	val id: Long? = null,
	val time: Instant = Instant.now(),
	val kcal: Int = 0,
	val isError: Boolean = true,
)