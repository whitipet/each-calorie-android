package feature.food_book

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import project.ui.theme.Theme

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun FoodBookScreenPreview() = Theme {
	SharedTransitionLayout {
		AnimatedContent(targetState = true, label = "AnimatedContent") { targetState ->
			if (!targetState) return@AnimatedContent
			FoodBookScreen(
				uiState = remember { mutableStateOf(FoodBookUIState()) },
				onBackAction = {},
				onAddAction = {},
				sharedTransitionScope = this@SharedTransitionLayout,
				animatedVisibilityScope = this@AnimatedContent,
			)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun FoodBookScreen(
	uiState: State<FoodBookUIState>,
	onBackAction: () -> Unit,
	onAddAction: () -> Unit,
	sharedTransitionScope: SharedTransitionScope,
	animatedVisibilityScope: AnimatedVisibilityScope,
) = with(sharedTransitionScope) {
	val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

	Scaffold(
		modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
		topBar = {
			TopAppBar(
				title = { Text("Food Book", maxLines = 1, overflow = TextOverflow.Ellipsis) },
				navigationIcon = {
					IconButton(
						onClick = onBackAction
					) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Back") }
				},
				scrollBehavior = scrollBehavior,
			)
		},
		floatingActionButtonPosition = FabPosition.End,
		floatingActionButton = {
			FloatingActionButton(
				modifier = Modifier.sharedBounds(
					rememberSharedContentState(key = "bounds"),
					animatedVisibilityScope = animatedVisibilityScope,
				),
				onClick = onAddAction
			) { Icon(Icons.Rounded.Add, "Add") }
		}
	) { padding ->
		LazyColumn(
			modifier = Modifier.fillMaxSize(),
			contentPadding = PaddingValues( // FIXME: PaddingValues causes frame skip during scroll
				start = padding.calculateStartPadding(LocalLayoutDirection.current),
				top = padding.calculateTopPadding(),
				end = padding.calculateEndPadding(LocalLayoutDirection.current),
				bottom = padding.calculateBottomPadding() + (56.dp + 32.dp) // TODO: Find way to calculate FAB size
			),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			items(
				items = buildList {
					for (i in 0 until 50) {
						add(i)
					}
				},
			) {
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.height(100.dp)
						.alpha(0.1f)
						.background(Color.Black)
				) {

				}
			}
		}
	}
}