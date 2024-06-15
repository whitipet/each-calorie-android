package feature.food

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import project.ui.theme.Theme

@Preview(showBackground = true)
@Composable
private fun FoodScreenPreview() = Theme {
	FoodScreen(
		state = FoodUIState(),
		onNameChanged = {},
		onCaloriesChanged = {},
		onProtChanged = {},
		onFatChanged = {},
		onCarbsChanged = {},
		onSaveAction = {},
		onCloseAction = {},
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FoodScreen(
	state: FoodUIState,
	onNameChanged: (name: String) -> Unit,
	onCaloriesChanged: (kcal: Int) -> Unit,
	onProtChanged: (grams: Int) -> Unit,
	onFatChanged: (grams: Int) -> Unit,
	onCarbsChanged: (grams: Int) -> Unit,
	onSaveAction: () -> Unit,
	onCloseAction: () -> Unit,
) {
	val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
	val imeController = LocalSoftwareKeyboardController.current
	Scaffold(
		modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
		topBar = {
			TopAppBar(
				navigationIcon = {
					IconButton(onClick = {
						imeController?.hide()
						onCloseAction()
					}) {
						Icon(Icons.Rounded.Close, "Close")
					}
				},
				title = { Text("Food", maxLines = 1, overflow = TextOverflow.Ellipsis) },
				actions = {
					TextButton(onClick = {
						imeController?.hide()
						onSaveAction()
					}) {
						Text("Save")
					}
				},
				scrollBehavior = scrollBehavior,
			)
		},
	) { padding ->
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(padding)
				.padding(horizontal = 16.dp, vertical = 16.dp)
				.verticalScroll(rememberScrollState()),
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			val focusRequester = remember { FocusRequester() }
			OutlinedTextField(
				modifier = Modifier
					.fillMaxWidth()
					.focusRequester(focusRequester),
				label = { Text("Name") },
				singleLine = true,
				keyboardOptions = KeyboardOptions(
					imeAction = ImeAction.Done
				),
				keyboardActions = KeyboardActions(onDone = {
					imeController?.hide()
					onSaveAction()
				}),
				value = state.name,
				onValueChange = onNameChanged
			)
			LaunchedEffect(Unit) { focusRequester.requestFocus() }

			//region Size
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(16.dp),
			) {
				OutlinedTextField(
					modifier = Modifier.weight(1f),
					readOnly = true,
					label = { Text("Size") },
					singleLine = true,
					value = "100",
					onValueChange = {}
				)

				val units = listOf("gram")
				var expanded by remember { mutableStateOf(false) }
				var selectedUnits by remember { mutableStateOf(units[0]) }
				ExposedDropdownMenuBox(
					modifier = Modifier.weight(1f),
					expanded = expanded,
					onExpandedChange = { expanded = !expanded }
				) {
					OutlinedTextField(
						modifier = Modifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true),
						readOnly = true,
						colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
						label = { Text("Units") },
						singleLine = true,
						value = selectedUnits,
						onValueChange = { },
						trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
					)
					ExposedDropdownMenu(
						expanded = expanded,
						onDismissRequest = { expanded = false }
					) {
						units.forEach { selectionOption ->
							DropdownMenuItem(
								text = { Text(text = selectionOption) },
								onClick = {
									selectedUnits = selectionOption
									expanded = false
								}
							)
						}
					}
				}
			}
			//endregion Size

			//region Nutrients
			OutlinedTextField(
				modifier = Modifier.fillMaxWidth(),
				label = { Text(text = "Calories") },
				suffix = { Text(text = "kcal") },
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
				singleLine = true,
				value = with(state.caloriesKcal.toString()) { TextFieldValue(text = this, TextRange(length)) },
				onValueChange = {
					val text = it.text
					if (text.length >= 7) return@OutlinedTextField
					onCaloriesChanged(text.toIntOrNull() ?: 0)
				}
			)

			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(16.dp)
			) {

				OutlinedTextField(
					modifier = Modifier.weight(1f),
					label = { Text("Prot") },
					suffix = { Text(text = "g") },
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
					singleLine = true,
					value = with(state.protGrams.toString()) { TextFieldValue(text = this, TextRange(length)) },
					onValueChange = {
						val text = it.text
						if (text.length >= 7) return@OutlinedTextField
						onProtChanged(text.toIntOrNull() ?: 0)
					}
				)

				OutlinedTextField(
					modifier = Modifier.weight(1f),
					label = { Text("Fat") },
					suffix = { Text(text = "g") },
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
					singleLine = true,
					value = with(state.fatGrams.toString()) { TextFieldValue(text = this, TextRange(length)) },
					onValueChange = {
						val text = it.text
						if (text.length >= 7) return@OutlinedTextField
						onFatChanged(text.toIntOrNull() ?: 0)
					}
				)

				OutlinedTextField(
					modifier = Modifier.weight(1f),
					label = { Text("Carbs") },
					suffix = { Text(text = "g") },
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
					singleLine = true,
					value = with(state.carbsGrams.toString()) { TextFieldValue(text = this, TextRange(length)) },
					onValueChange = {
						val text = it.text
						if (text.length >= 7) return@OutlinedTextField
						onCarbsChanged(text.toIntOrNull() ?: 0)
					}
				)
			}
			//endregion Nutrients
		}
	}
}