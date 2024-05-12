package feature.food_book

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import project.ui.theme.Theme

@Preview
@Composable
private fun FoodBookScreenPreview() = Theme {
	val uiState = FoodBookUIState()
	FoodBookScreen(
		uiState = remember { mutableStateOf(uiState) },
		onBackAction = {},
		onAddAction = {},
	)
}

@Composable
internal fun FoodBookScreen(
	uiState: State<FoodBookUIState>,
	onBackAction: () -> Unit,
	onAddAction: () -> Unit,
) {
	Scaffold(
		floatingActionButtonPosition = FabPosition.End,
		floatingActionButton = {
			FloatingActionButton(onClick = { onAddAction() }) {
				Icon(Icons.Rounded.Add, "Add")
			}
		}
	) { padding ->
		val fabSizeWithOffset = 56.dp + 32.dp // TODO: Find way to calculate
		Column(
			modifier = Modifier
				.fillMaxSize()
				.verticalScroll(rememberScrollState())
				.padding(padding)
				.padding(top = 56.dp, bottom = fabSizeWithOffset)
				.padding(horizontal = 16.dp, vertical = 8.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {

		}
	}
}