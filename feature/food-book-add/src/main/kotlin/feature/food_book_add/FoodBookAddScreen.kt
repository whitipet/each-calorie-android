package feature.food_book_add

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import project.ui.theme.Theme

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun FoodBookAddScreenPreview() = Theme {
	SharedTransitionLayout {
		AnimatedContent(targetState = true, label = "AnimatedContent") { targetState ->
			if (!targetState) return@AnimatedContent
			FoodBookAddScreen(
				uiState = remember { mutableStateOf(FoodBookAddUIState()) },
				updateNameAction = {},
				onSaveAction = {},
				onCloseAction = {},
				sharedTransitionScope = this@SharedTransitionLayout,
				animatedVisibilityScope = this@AnimatedContent,
			)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun FoodBookAddScreen(
	uiState: State<FoodBookAddUIState>,
	updateNameAction: (name: String) -> Unit,
	onSaveAction: () -> Unit,
	onCloseAction: () -> Unit,
	sharedTransitionScope: SharedTransitionScope,
	animatedVisibilityScope: AnimatedVisibilityScope,
) = with(sharedTransitionScope) {
	val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
	val imeController = LocalSoftwareKeyboardController.current

	Scaffold(
		modifier = Modifier
			.nestedScroll(scrollBehavior.nestedScrollConnection)
			.sharedBounds(
				rememberSharedContentState(key = "bounds"),
				animatedVisibilityScope = animatedVisibilityScope,
			),
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
				title = { Text("New food", maxLines = 1, overflow = TextOverflow.Ellipsis) },
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
				.padding(horizontal = 16.dp)
				.verticalScroll(rememberScrollState())
		) {
			val focusRequester = remember { FocusRequester() }
			OutlinedTextField(
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 16.dp)
					.focusRequester(focusRequester),
				label = { Text("Name") },
				singleLine = true,
				value = uiState.value.name,
				onValueChange = { updateNameAction(it) }
			)
			LaunchedEffect(Unit) { focusRequester.requestFocus() }

			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 24.dp, bottom = 16.dp)
			) {
				OutlinedTextField(
					modifier = Modifier.weight(1f),
					readOnly = true,
					singleLine = true,
					label = { Text("Size") },
					value = "100",
					onValueChange = {}
				)

				val units = listOf("gram")
				var expanded by remember { mutableStateOf(false) }
				var selectedUnits by remember { mutableStateOf(units[0]) }
				ExposedDropdownMenuBox(
					modifier = Modifier
						.weight(1f)
						.padding(start = 16.dp),
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
		}
	}
}