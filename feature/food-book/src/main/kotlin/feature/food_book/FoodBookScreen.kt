package feature.food_book

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import project.entity.Food
import project.ui.theme.Theme
import kotlin.random.Random

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun FoodBookScreenPreview() = Theme {
	SharedTransitionLayout {
		AnimatedContent(targetState = true, label = "AnimatedContent") { targetState ->
			if (!targetState) return@AnimatedContent
			FoodBookScreen(
				state = FoodBookUIState(
					foods = buildList {
						for (i in 1..20) {
							add(
								Food(
									Random.nextLong(),
									Random.nextInt().toString(),
									100,
									"grams"
								)
							)
						}
					}
				),
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
	state: FoodBookUIState,
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
				items = state.foods,
				key = { it.id },
				contentType = { it::class }
			) { food ->
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.defaultMinSize(minHeight = 56.dp)
						.padding(16.dp),
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						modifier = Modifier
							.weight(1f),
						style = MaterialTheme.typography.bodyLarge,
						text = food.name
					)

					Column {
						Text(
							style = MaterialTheme.typography.bodyMedium,
							text = food.size.toString(),
						)
						Text(
							style = MaterialTheme.typography.bodySmall,
							text = food.units,
						)
					}
				}
				HorizontalDivider(thickness = 0.5.dp)
			}
		}
	}
}