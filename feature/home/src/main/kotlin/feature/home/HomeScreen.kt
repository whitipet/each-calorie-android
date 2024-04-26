package feature.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
		onAddAction = {}
	)
}

@Composable
internal fun HomeScreen(
	onAddAction: () -> Unit,
) {
	var current: Int by rememberSaveable { mutableIntStateOf(100) }
	val goal: Int = 2500

	Scaffold(
		floatingActionButtonPosition = FabPosition.Center,
		floatingActionButton = {
			FloatingActionButton(onClick = {
				current += 100
//				onAddAction()
			}) {
				Icon(Icons.Filled.Add, "Add")
			}
		}
	) { padding ->
		val fabSizeWithOffset = 56.dp + 32.dp // TODO: Find way to calculate
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(padding)
				.padding(horizontal = 24.dp)
				.padding(top = 56.dp, bottom = fabSizeWithOffset),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			CaloriesProgress(current = current, goal = goal)
		}
	}
}

@Composable
private fun CaloriesProgress(current: Int, goal: Int) {
	Box(
		modifier = Modifier
			.aspectRatio(1f)
			.fillMaxWidth(),
		contentAlignment = Alignment.Center
	) {
		ProgressBar(current.toFloat() / goal.toFloat())

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
				.fillMaxWidth(0.75f)
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
				alignment = Alignment.CenterStart,
			)

			AutoSizeText(
				text = goal.toString(),
				modifier = Modifier.weight(1f),
				maxLines = 1,
				softWrap = false,
				alignment = Alignment.CenterEnd,
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

	Arcs(modifier = Modifier.blur(80.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded))
	Arcs()
}