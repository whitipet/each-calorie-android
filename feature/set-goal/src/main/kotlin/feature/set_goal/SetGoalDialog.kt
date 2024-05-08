package feature.set_goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import project.ui.theme.Theme

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() = Theme {
	SetGoalDialog(
		uiState = remember {
			mutableStateOf(
				SetGoalUIState(
					kcal = 0,
					isError = true,
				)
			)
		},
		updateKcalAction = {},
		onDismissAction = {},
		onSaveAction = {},
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SetGoalDialog(
	uiState: State<SetGoalUIState>,
	updateKcalAction: (kcal: Int) -> Unit,
	onDismissAction: () -> Unit,
	onSaveAction: () -> Unit,
) = BasicAlertDialog(onDismissRequest = { onDismissAction() }) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentHeight(unbounded = true),
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(24.dp)
		) {
			Text(
				modifier = Modifier.fillMaxWidth(),
				style = MaterialTheme.typography.headlineSmall,
				textAlign = TextAlign.Start,
				text = "Set your goal",
			)

			val focusRequester = remember { FocusRequester() }
			OutlinedTextField(
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 16.dp)
					.focusRequester(focusRequester),
				label = { Text("kcal") },
				textStyle = MaterialTheme.typography.displayMedium,
				singleLine = true,
				keyboardOptions = KeyboardOptions(
					keyboardType = KeyboardType.Number,
					imeAction = ImeAction.Done
				),
				// FIXME: Selection
				value = with(uiState.value.kcal.toString()) { TextFieldValue(text = this, TextRange(length)) },
				onValueChange = {
					val text = it.text
					if (text.length >= 10) return@OutlinedTextField
					updateKcalAction(text.toIntOrNull() ?: 0)
				}
			)
			LaunchedEffect(Unit) { focusRequester.requestFocus() }

			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 24.dp),
				horizontalArrangement = Arrangement.End,
			) {
				TextButton(
					modifier = Modifier,
					onClick = onDismissAction,
				) {
					Text("Dismiss")
				}
				Spacer(modifier = Modifier.width(8.dp))
				TextButton(
					enabled = !uiState.value.isError,
					onClick = { onSaveAction() },
				) {
					Text("Save")
				}
			}
		}
	}
}