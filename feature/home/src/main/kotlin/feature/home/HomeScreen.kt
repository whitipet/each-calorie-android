package feature.home

import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import project.ui.theme.Theme

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() = Theme {
	HomeScreen(
		uiState = remember { mutableStateOf(HomeUIState(600, 2500)) },
		onAddAction = {},
		onGoalAction = {}
	)
}

@Composable
internal fun HomeScreen(
	uiState: State<HomeUIState>,
	onAddAction: () -> Unit,
	onGoalAction: (kcal: Int) -> Unit,
) {
	val current: Int = uiState.value.consumedKcal
	val goal: Int = uiState.value.goal

	Scaffold(
		floatingActionButtonPosition = FabPosition.Center,
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
				.padding(bottom = fabSizeWithOffset)
				.padding(horizontal = 24.dp, vertical = 56.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			ConsumptionProgress(
				current = current,
				goal = goal,
				onGoalLongClickAction = { onGoalAction(goal) }
			)
		}
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ConsumptionProgress(
	current: Int,
	goal: Int,
	onGoalLongClickAction: () -> Unit,
) {
	Box(
		modifier = Modifier
			.aspectRatio(1f)
			.fillMaxWidth(),
		contentAlignment = Alignment.Center
	) {
		ProgressBar(
			if (goal <= 0 && current <= 0) 0.0f
			else current.toFloat() / goal.toFloat()
		)

		AutoSizeText(
			text = current.toString(),
			modifier = Modifier
				.fillMaxSize(0.84f)
				.padding(48.dp),
			maxLines = 1,
			fontWeight = FontWeight.ExtraBold,
			softWrap = false,
			alignment = Alignment.Center
		)

		Row(
			modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight(0.1f)
				.align(Alignment.BottomCenter),
			verticalAlignment = Alignment.CenterVertically
		) {
			val delta = current - goal
			AutoSizeText(
				text = "${if (delta > 0) "+" else ""}${current - goal}",
				modifier = Modifier.weight(1f),
				maxLines = 1,
				softWrap = false,
				alignment = Alignment.Center,
			)
			Spacer(modifier = Modifier.weight(0.4f))
			AutoSizeText(
				text = goal.toString(),
				modifier = Modifier
					.weight(1f)
					.combinedClickable(onLongClick = { onGoalLongClickAction() }, onClick = {}),
				maxLines = 1,
				softWrap = false,
				alignment = Alignment.Center,
			)
		}
	}
}

@Composable
private fun ProgressBar(progress: Float) {
	val colorProgress = MaterialTheme.colorScheme.surfaceContainer
	val colorProgressStart = MaterialTheme.colorScheme.tertiary
	val colorProgressEnd =
		if (progress > 1.0f) MaterialTheme.colorScheme.error
		else MaterialTheme.colorScheme.primary

	@Composable
	fun Arcs(modifier: Modifier = Modifier) {
		Canvas(
			modifier = modifier
				.fillMaxWidth()
				.aspectRatio(1f)
		) {
			val progressWidth = size.width * 0.08f

			inset(progressWidth / 2) {
				drawArc(
					color = colorProgress,
					130f,
					280f,
					false,
					style = Stroke(progressWidth, cap = StrokeCap.Round),
				)

				rotate(degrees = 70f) {
					drawArc(
						brush = Brush.sweepGradient(listOf(colorProgressStart, colorProgressEnd)),
						60f,
						280f * progress.coerceIn(0.0f, 1.0f),
						false,
						style = Stroke(progressWidth, cap = StrokeCap.Round),
						blendMode = BlendMode.Src,
					)
				}
			}
		}
	}

	if (Build.VERSION.SDK_INT >= VERSION_CODES.S) {
		Arcs(modifier = Modifier.blur(80.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded))
	}
	Arcs()
}