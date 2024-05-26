package feature.consumption

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import project.ui.theme.Theme
import java.time.ZoneId
import java.time.chrono.IsoChronology
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.FormatStyle
import java.util.Locale

@Preview
@Composable
private fun ConsumptionScreenPreview() = Theme {
	ConsumptionScreen(
		uiState = remember {
			mutableStateOf(
				ConsumptionUIState(
					id = 0,
					kcal = 100,
					isError = false
				)
			)
		},
		onCloseAction = {},
		updateKcalAction = {},
		onSaveAction = {},
		onDeleteAction = {},
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ConsumptionScreen(
	uiState: State<ConsumptionUIState>,
	onCloseAction: () -> Unit,
	updateKcalAction: (kcal: Int) -> Unit,
	onSaveAction: () -> Unit,
	onDeleteAction: () -> Unit,
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
					}) { Icon(Icons.Rounded.Close, "Close") }
				},
				title = { Text("Consumption", maxLines = 1, overflow = TextOverflow.Ellipsis) },
				actions = {
					if (uiState.value.id != null) {
						TextButton(onClick = {
							imeController?.hide()
							onDeleteAction()
						}) { Text("Delete") }
					}
					TextButton(
						enabled = !uiState.value.isError,
						onClick = {
							imeController?.hide()
							onSaveAction()
						}) { Text("Save") }
				},
				scrollBehavior = scrollBehavior,
			)
		},
	) { padding ->
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(padding)
				.padding(horizontal = 16.dp)
				.verticalScroll(rememberScrollState()),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			//region Date + time
			Row(
				modifier = Modifier
					.fillMaxWidth()
			) {
				OutlinedTextField(
					modifier = Modifier.weight(1f),
					readOnly = true,
					singleLine = true,
					label = { Text("Date") },
					trailingIcon = {
						IconButton(onClick = {
							// TODO: Show date picker
						}) { Icon(Icons.Rounded.DateRange, "Date") }
					},
					value = DateTimeFormatter.ofPattern(
						DateTimeFormatterBuilder.getLocalizedDateTimePattern(
							FormatStyle.SHORT,
							null,
							IsoChronology.INSTANCE,
							Locale.getDefault()
						)
					).withZone(ZoneId.systemDefault()).format(uiState.value.time),
					onValueChange = {}
				)
				OutlinedTextField(
					modifier = Modifier
						.weight(1f)
						.padding(start = 16.dp),
					readOnly = true,
					singleLine = true,
					label = { Text("Time") },
					trailingIcon = {
						IconButton(onClick = {
							// TODO: Show time picker
						}) {
							Icon(Icons.Rounded.Refresh, "Time") // TODO: Change icon
						}
					},
					value = DateTimeFormatter.ofPattern(
						DateTimeFormatterBuilder.getLocalizedDateTimePattern(
							null,
							FormatStyle.SHORT,
							IsoChronology.INSTANCE,
							Locale.getDefault()
						)
					).withZone(ZoneId.systemDefault()).format(uiState.value.time),
					onValueChange = {}
				)
			}
			//endregion Date + time

			val focusRequester = remember { FocusRequester() }
			OutlinedTextField(
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 24.dp)
					.focusRequester(focusRequester)
					.clearFocusOnKeyboardDismiss(),
				label = { Text("kcal") },
				textStyle = MaterialTheme.typography.displayMedium,
				singleLine = true,
				trailingIcon = { Icon(Icons.Rounded.Done, null) },
				keyboardOptions = KeyboardOptions(
					keyboardType = KeyboardType.Number,
					imeAction = ImeAction.Done
				),
				keyboardActions = KeyboardActions(onDone = {
					if (uiState.value.isError) return@KeyboardActions
					imeController?.hide()
					onSaveAction()
				}),
				// FIXME: Selection
				value = with(uiState.value.kcal.toString()) { TextFieldValue(text = this, TextRange(length)) },
				onValueChange = {
					val text = it.text
					if (text.length >= 7) return@OutlinedTextField
					updateKcalAction(text.toIntOrNull() ?: 0)
				}
			)
			LaunchedEffect(Unit) { focusRequester.requestFocus() }
		}
	}
}

@OptIn(ExperimentalLayoutApi::class)
private fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {
	var isFocused by remember { mutableStateOf(false) }
	var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }
	if (isFocused) {
		val imeIsVisible = WindowInsets.isImeVisible
		val focusManager = LocalFocusManager.current
		LaunchedEffect(imeIsVisible) {
			if (imeIsVisible) keyboardAppearedSinceLastFocused = true
			else if (keyboardAppearedSinceLastFocused) focusManager.clearFocus()
		}
	}
	onFocusEvent {
		if (isFocused != it.isFocused) {
			isFocused = it.isFocused
			if (isFocused) keyboardAppearedSinceLastFocused = false
		}
	}
}