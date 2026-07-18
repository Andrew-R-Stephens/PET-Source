package com.tritiumgaming.core.ui.widgets.walkthrough

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.theme.LocalPalette
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun WalkthroughHost(
    state: WalkthroughState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier
        .fillMaxSize()
        .onGloballyPositioned { state.registerHost(it) }
    ) {
        content()

        AnimatedVisibility(
            visible = state.isShowing,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            WalkthroughOverlay(state)
        }
    }
}

@Composable
private fun PagerIndicator(
    count: Int,
    index: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(count) { i ->
            val color = if (i == index) {
                LocalPalette.current.primary
            } else {
                LocalPalette.current.onSurfaceVariant.copy(alpha = 0.3f)
            }
            val size = if (i == index) 8.dp else 6.dp
            Box(
                modifier = Modifier
                    .size(size)
                    .background(color, CircleShape)
            )
        }
    }
}

@Composable
private fun WalkthroughOverlay(
    state: WalkthroughState
) {
    val density = LocalDensity.current
    val paddingPx = with(density) { 8.dp.toPx() }
    val step = state.currentStep ?: return
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(state.currentStepIndex, state.currentPageIndex) {
        state.triggerTargetInteraction()
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenWidthPx = constraints.maxWidth.toFloat()
        val screenHeightPx = constraints.maxHeight.toFloat()
        val boundInfos = state.getTargetBounds(paddingPx)

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { state.finish() }
        ) {
            drawRect(color = Color.Black.copy(alpha = 0.8f))

            boundInfos.forEach { info ->
                val outline = info.shape.createOutline(
                    size = info.rect.size,
                    layoutDirection = layoutDirection,
                    density = this
                )
                
                drawIntoCanvas { canvas ->
                    canvas.save()
                    canvas.translate(info.rect.left, info.rect.top)
                    drawOutline(
                        outline = outline,
                        color = Color.Transparent,
                        blendMode = BlendMode.Clear
                    )
                    canvas.restore()
                }
            }
        }

        val aggregateRect = remember(boundInfos) {
            if (boundInfos.isEmpty()) {
                Rect(0f, screenHeightPx, screenWidthPx, screenHeightPx)
            } else {
                var minTop = Float.MAX_VALUE
                var maxBottom = Float.MIN_VALUE
                var minLeft = Float.MAX_VALUE
                var maxRight = Float.MIN_VALUE

                boundInfos.forEach { info ->
                    minTop = minOf(minTop, info.rect.top)
                    maxBottom = maxOf(maxBottom, info.rect.bottom)
                    minLeft = minOf(minLeft, info.rect.left)
                    maxRight = maxOf(maxRight, info.rect.right)
                }
                Rect(minLeft, minTop, maxRight, maxBottom)
            }
        }

        var totalDragX by remember { mutableStateOf(0f) }
        val swipeThreshold = with(density) { 40.dp.toPx() }

        Layout(
            content = {
                Surface(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .animateContentSize()
                        .pointerInput(state) {
                            detectHorizontalDragGestures(
                                onDragEnd = {
                                    if (abs(totalDragX) > swipeThreshold) {
                                        if (totalDragX > 0) state.previous() else state.next()
                                    }
                                    totalDragX = 0f
                                },
                                onHorizontalDrag = { _, dragAmount ->
                                    totalDragX += dragAmount
                                }
                            )
                        },
                    shape = RoundedCornerShape(12.dp),
                    color = LocalPalette.current.surfaceContainerHigh,
                    tonalElevation = 8.dp
                ) {
                    AnimatedContent(
                        targetState = state.currentStepIndex to state.currentPageIndex,
                        transitionSpec = {
                            if (targetState.first == initialState.first) {
                                // Within same step: slide based on page index
                                if (targetState.second > initialState.second) {
                                    (slideInHorizontally { it } + fadeIn()) togetherWith
                                            (slideOutHorizontally { -it } + fadeOut())
                                } else {
                                    (slideInHorizontally { -it } + fadeIn()) togetherWith
                                            (slideOutHorizontally { it } + fadeOut())
                                }
                            } else {
                                // Between steps: slide based on step index
                                if ((targetState.first ?: 0) > (initialState.first ?: 0)) {
                                    (slideInHorizontally { it } + fadeIn()) togetherWith
                                            (slideOutHorizontally { -it } + fadeOut())
                                } else {
                                    (slideInHorizontally { -it } + fadeIn()) togetherWith
                                            (slideOutHorizontally { it } + fadeOut())
                                }
                            }
                        },
                        label = "WalkthroughContent"
                    ) { (_, pageIndex) ->
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            step.titleRes?.let {
                                Text(
                                    text = stringResource(it),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = LocalPalette.current.primary
                                )
                            }

                            val page = step.pages.getOrNull(pageIndex)
                            page?.subtitleRes?.let {
                                Text(
                                    text = stringResource(it),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = LocalPalette.current.secondary
                                )
                            }

                            page?.descriptionRes?.let {
                                Text(
                                    text = stringResource(it),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = LocalPalette.current.onSurface
                                )
                            }

                            if (state.pageCount > 1) {
                                PagerIndicator(
                                    count = state.pageCount,
                                    index = state.pageIndex,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        ) { measurables, constraints ->
            val padding = 16.dp.roundToPx()
            val gap = 12.dp.roundToPx()

            val spaceAbove = aggregateRect.top
            val spaceBelow = screenHeightPx - aggregateRect.bottom
            val spaceLeft = aggregateRect.left
            val spaceRight = screenWidthPx - aggregateRect.right

            val verticalWeight = 1.2f
            val scores = listOf(
                "TOP" to spaceAbove * screenWidthPx * verticalWeight,
                "BOTTOM" to spaceBelow * screenWidthPx * verticalWeight,
                "LEFT" to spaceLeft * screenHeightPx,
                "RIGHT" to spaceRight * screenHeightPx
            )

            val best = scores.maxBy { it.second }.first

            val maxAllowedWidth = with(density) { 400.dp.roundToPx() }

            val availableWidth = when (best) {
                "TOP", "BOTTOM" -> (constraints.maxWidth - padding * 2).coerceAtMost(maxAllowedWidth)
                "LEFT" -> (spaceLeft - gap - padding).roundToInt().coerceAtMost(maxAllowedWidth).coerceAtLeast(0)
                "RIGHT" -> (spaceRight - gap - padding).roundToInt().coerceAtMost(maxAllowedWidth).coerceAtLeast(0)
                else -> (constraints.maxWidth - padding * 2).coerceAtMost(maxAllowedWidth)
            }

            val placeable = measurables.first().measure(
                constraints.copy(
                    maxWidth = availableWidth
                )
            )

            var x: Float
            var y: Float

            when (best) {
                "TOP" -> {
                    y = aggregateRect.top - placeable.height - gap
                    x = aggregateRect.center.x - placeable.width / 2f
                }
                "BOTTOM" -> {
                    y = aggregateRect.bottom + gap
                    x = aggregateRect.center.x - placeable.width / 2f
                }
                "LEFT" -> {
                    x = aggregateRect.left - placeable.width - gap
                    y = aggregateRect.center.y - placeable.height / 2f
                }
                else -> { // RIGHT
                    x = aggregateRect.right + gap
                    y = aggregateRect.center.y - placeable.height / 2f
                }
            }

            // Clamp to screen
            x = x.coerceIn(padding.toFloat(), screenWidthPx - placeable.width - padding)
            y = y.coerceIn(padding.toFloat(), screenHeightPx - placeable.height - padding)

            layout(constraints.maxWidth, constraints.maxHeight) {
                placeable.placeRelative(x.roundToInt(), y.roundToInt())
            }
        }
    }
}
