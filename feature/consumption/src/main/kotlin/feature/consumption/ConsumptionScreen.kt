package feature.consumption

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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
		uiState = remember { mutableStateOf(ConsumptionUIState()) },
		onBackAction = {},
		updateKcalAction = {},
		onSaveAction = {}
	)
}

@Composable
internal fun ConsumptionScreen(
	uiState: State<ConsumptionUIState>,
	onBackAction: () -> Unit,
	updateKcalAction: (kcal: Int) -> Unit,
	onSaveAction: () -> Unit,
) {
	Scaffold(
		floatingActionButtonPosition = FabPosition.End,
		floatingActionButton = {
			FloatingActionButton(onClick = { onSaveAction() }) {
				Icon(Icons.Rounded.Check, "Save")
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

			// TODO: Clear focus on ime close
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
				keyboardActions = KeyboardActions(onDone = { onSaveAction() }),
				isError = uiState.value.isError,
				supportingText = if (uiState.value.isError) {
					{ Text("Incorrect value") }
				} else null,
				// FIXME: Selection
				value = with(uiState.value.kcal.toString()) { TextFieldValue(text = this, TextRange(length)) },
				onValueChange = {
					val text = it.text
					if (text.length >= 7) return@OutlinedTextField
					updateKcalAction(
						try {
							Integer.parseInt(text)
						} catch (_: Exception) {
							0
						}
					)
				}
			)
			LaunchedEffect(Unit) { focusRequester.requestFocus() }
		}

		FilledTonalIconButton(
			modifier = Modifier
				.padding(padding)
				.padding(4.dp),
			onClick = { onBackAction() }) {
			Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Back")
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