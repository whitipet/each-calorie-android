package feature.food_book_add

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import project.ui.theme.Theme

@Preview(showBackground = true)
@Composable
private fun FoodBookAddDialogPreview() = Theme {
	FoodBookAddDialog(
		uiState = remember {
			mutableStateOf(
				FoodBookAddUIState()
			)
		},
		onDismissAction = {},
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FoodBookAddDialog(
	uiState: State<FoodBookAddUIState>,
	onDismissAction: () -> Unit,
) = ModalBottomSheet(onDismissRequest = onDismissAction) {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.height(200.dp)
	)
}