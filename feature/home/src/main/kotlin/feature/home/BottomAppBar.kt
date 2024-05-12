package feature.home

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateTo
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.BottomAppBarState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * <a href="https://m3.material.io/components/bottom-app-bar/overview" class="external" target="_blank">Material Design bottom app bar</a>.
 *
 * A bottom app bar displays navigation and key actions at the bottom of mobile screens.
 *
 * ![Bottom app bar image](https://developer.android.com/images/reference/androidx/compose/material3/bottom-app-bar.png)
 *
 * @sample androidx.compose.material3.samples.SimpleBottomAppBar
 *
 * @sample androidx.compose.material3.samples.BottomAppBarWithFAB
 *
 * A bottom app bar that uses a [scrollBehavior] to customize its nested scrolling behavior when
 * working in conjunction with a scrolling content looks like:
 *
 * @sample androidx.compose.material3.samples.ExitAlwaysBottomAppBar
 *
 * Also see [NavigationBar].
 *
 * @param actions the icon content of this BottomAppBar. The default layout here is a [Row],
 * so content inside will be placed horizontally.
 * @param modifier the [Modifier] to be applied to this BottomAppBar
 * @param containerColor the color used for the background of this BottomAppBar. Use
 * [Color.Transparent] to have no color.
 * @param contentColor the preferred color for content inside this BottomAppBar. Defaults to either
 * the matching content color for [containerColor], or to the current [LocalContentColor] if
 * [containerColor] is not a color from the theme.
 * @param tonalElevation when [containerColor] is [ColorScheme.surface], a translucent primary color
 * overlay is applied on top of the container. A higher tonal elevation value will result in a
 * darker color in light theme and lighter color in dark theme. See also: [Surface].
 * @param contentPadding the padding applied to the content of this BottomAppBar
 * @param windowInsets a window insets that app bar will respect.
 * @param scrollBehavior a [BottomAppBarScrollBehavior] which holds various offset values that will
 * be applied by this bottom app bar to set up its height. A scroll behavior is designed to
 * work in conjunction with a scrolled content to change the bottom app bar appearance as the
 * content scrolls. See [BottomAppBarScrollBehavior.nestedScrollConnection].
 */
@ExperimentalMaterial3Api
@Composable
internal fun BottomAppBar(
	actions: @Composable RowScope.() -> Unit,
	modifier: Modifier = Modifier,
	containerColor: Color = BottomAppBarDefaults.containerColor,
	contentColor: Color = contentColorFor(containerColor),
	tonalElevation: Dp = BottomAppBarDefaults.ContainerElevation,
	contentPadding: PaddingValues = BottomAppBarDefaults.ContentPadding,
	windowInsets: WindowInsets = BottomAppBarDefaults.windowInsets,
	scrollBehavior: BottomAppBarScrollBehavior? = null,
) = BottomAppBar(
	modifier = modifier,
	containerColor = containerColor,
	contentColor = contentColor,
	tonalElevation = tonalElevation,
	windowInsets = windowInsets,
	contentPadding = contentPadding,
	scrollBehavior = scrollBehavior
) {
	Row(
		modifier = Modifier.weight(1f),
		horizontalArrangement = Arrangement.Start,
		verticalAlignment = Alignment.CenterVertically,
		content = actions,
	)
}

/**
 * <a href="https://m3.material.io/components/bottom-app-bar/overview" class="external" target="_blank">Material Design bottom app bar</a>.
 *
 * A bottom app bar displays navigation and key actions at the bottom of mobile screens.
 *
 * ![Bottom app bar image](https://developer.android.com/images/reference/androidx/compose/material3/bottom-app-bar.png)
 *
 * If you are interested in displaying a [FloatingActionButton], consider using another overload.
 *
 * Also see [NavigationBar].
 *
 * @param modifier the [Modifier] to be applied to this BottomAppBar
 * @param containerColor the color used for the background of this BottomAppBar. Use
 * [Color.Transparent] to have no color.
 * @param contentColor the preferred color for content inside this BottomAppBar. Defaults to either
 * the matching content color for [containerColor], or to the current [LocalContentColor] if
 * [containerColor] is not a color from the theme.
 * @param tonalElevation when [containerColor] is [ColorScheme.surface], a translucent primary color
 * overlay is applied on top of the container. A higher tonal elevation value will result in a
 * darker color in light theme and lighter color in dark theme. See also: [Surface].
 * @param contentPadding the padding applied to the content of this BottomAppBar
 * @param windowInsets a window insets that app bar will respect.
 * @param scrollBehavior a [BottomAppBarScrollBehavior] which holds various offset values that will
 * be applied by this bottom app bar to set up its height. A scroll behavior is designed to
 * work in conjunction with a scrolled content to change the bottom app bar appearance as the
 * content scrolls. See [BottomAppBarScrollBehavior.nestedScrollConnection].
 * @param content the content of this BottomAppBar. The default layout here is a [Row],
 * so content inside will be placed horizontally.
 */
@ExperimentalMaterial3Api
@Composable
private fun BottomAppBar(
	modifier: Modifier = Modifier,
	containerColor: Color = BottomAppBarDefaults.containerColor,
	contentColor: Color = contentColorFor(containerColor),
	tonalElevation: Dp = BottomAppBarDefaults.ContainerElevation,
	contentPadding: PaddingValues = BottomAppBarDefaults.ContentPadding,
	windowInsets: WindowInsets = BottomAppBarDefaults.windowInsets,
	scrollBehavior: BottomAppBarScrollBehavior? = null,
	content: @Composable RowScope.() -> Unit,
) {
	// Set up support for resizing the bottom app bar when vertically dragging the bar itself.
	val appBarDragModifier = if (scrollBehavior != null && !scrollBehavior.isPinned) {
		Modifier.draggable(
			orientation = Orientation.Vertical,
			state = rememberDraggableState { delta ->
				scrollBehavior.state.heightOffset -= delta
			},
			onDragStopped = { velocity ->
				settleAppBarBottom(
					scrollBehavior.state,
					velocity,
					scrollBehavior.flingAnimationSpec,
					scrollBehavior.snapAnimationSpec
				)
			}
		)
	} else {
		Modifier
	}

	// Compose a Surface with a Row content.
	// The height of the app bar is determined by subtracting the bar's height offset from the
	// app bar's defined constant height value (i.e. the ContainerHeight token).
	Surface(
		color = containerColor,
		contentColor = contentColor,
		tonalElevation = tonalElevation,
		modifier = modifier
			.layout { measurable, constraints ->
				val placeable = measurable.measure(constraints)

				// Sets the app bar's height offset to collapse the entire bar's height when content
				// is scrolled.
				scrollBehavior?.state?.heightOffsetLimit = -placeable.height.toFloat()

				val height = placeable.height + (scrollBehavior?.state?.heightOffset ?: 0f)
				layout(placeable.width, height.roundToInt()) {
					placeable.place(0, 0)
				}
			}
			.then(appBarDragModifier)
	) {
		Row(
			Modifier
				.fillMaxWidth()
				.windowInsetsPadding(windowInsets)
				.height(80.0.dp) //BottomAppBarTokens.ContainerHeight
				.padding(contentPadding),
			horizontalArrangement = Arrangement.Start,
			verticalAlignment = Alignment.CenterVertically,
			content = content
		)
	}
}

/**
 * Settles the app bar by flinging, in case the given velocity is greater than zero, and snapping
 * after the fling settles.
 */
@OptIn(ExperimentalMaterial3Api::class)
private suspend fun settleAppBarBottom(
	state: BottomAppBarState,
	velocity: Float,
	flingAnimationSpec: DecayAnimationSpec<Float>?,
	snapAnimationSpec: AnimationSpec<Float>?,
): Velocity {
	// Check if the app bar is completely collapsed/expanded. If so, no need to settle the app bar,
	// and just return Zero Velocity.
	// Note that we don't check for 0f due to float precision with the collapsedFraction
	// calculation.
	if (state.collapsedFraction < 0.01f || state.collapsedFraction == 1f) {
		return Velocity.Zero
	}
	var remainingVelocity = velocity
	// In case there is an initial velocity that was left after a previous user fling, animate to
	// continue the motion to expand or collapse the app bar.
	if (flingAnimationSpec != null && abs(velocity) > 1f) {
		var lastValue = 0f
		AnimationState(
			initialValue = 0f,
			initialVelocity = velocity,
		)
			.animateDecay(flingAnimationSpec) {
				val delta = value - lastValue
				val initialHeightOffset = state.heightOffset
				state.heightOffset = initialHeightOffset + delta
				val consumed = abs(initialHeightOffset - state.heightOffset)
				lastValue = value
				remainingVelocity = this.velocity
				// avoid rounding errors and stop if anything is unconsumed
				if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
			}
	}
	// Snap if animation specs were provided.
	if (snapAnimationSpec != null) {
		if (state.heightOffset < 0 &&
			state.heightOffset > state.heightOffsetLimit
		) {
			AnimationState(initialValue = state.heightOffset).animateTo(
				if (state.collapsedFraction < 0.5f) {
					0f
				} else {
					state.heightOffsetLimit
				},
				animationSpec = snapAnimationSpec
			) { state.heightOffset = value }
		}
	}

	return Velocity(0f, remainingVelocity)
}