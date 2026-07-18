package com.tritiumgaming.core.ui.widgets.walkthrough

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.dp

@Stable
class WalkthroughState(
    val steps: List<WalkthroughStep>,
    private val onFinish: () -> Unit = {}
) {
    private data class TargetInfo(
        val coordinates: LayoutCoordinates,
        val shape: Shape
    )

    data class TargetBoundInfo(
        val rect: Rect,
        val shape: Shape
    )

    private val targetInfoMap = mutableStateMapOf<String, TargetInfo>()
    private var hostCoordinates by mutableStateOf<LayoutCoordinates?>(null)
    private var isIsolated by mutableStateOf(false)

    var currentStepIndex by mutableStateOf<Int?>(null)
        private set
    
    var currentPageIndex by mutableStateOf(0)
        private set

    val currentStep: WalkthroughStep? get() = currentStepIndex?.let { steps.getOrNull(it) }

    fun registerHost(coordinates: LayoutCoordinates) {
        hostCoordinates = coordinates
    }

    fun registerTarget(id: String, coordinates: LayoutCoordinates, shape: Shape) {
        targetInfoMap[id] = TargetInfo(coordinates, shape)
    }

    fun start() {
        isIsolated = false
        if (steps.isNotEmpty()) {
            currentStepIndex = 0
            currentPageIndex = 0
        }
    }

    fun start(id: String) {
        isIsolated = true
        val index = steps.indexOfFirst { it.id == id }
        if (index != -1) {
            currentStepIndex = index
            currentPageIndex = 0
        }
    }

    fun next() {
        val step = currentStep ?: return
        if (currentPageIndex < step.pages.size - 1) {
            currentPageIndex++
        } else {
            if (isIsolated) {
                finish()
            } else {
                val nextIndex = (currentStepIndex ?: -1) + 1
                if (nextIndex < steps.size) {
                    currentStepIndex = nextIndex
                    currentPageIndex = 0
                } else {
                    finish()
                }
            }
        }
    }

    fun previous() {
        if (currentPageIndex > 0) {
            currentPageIndex--
        } else {
            if (!isIsolated) {
                val prevIndex = (currentStepIndex ?: 0) - 1
                if (prevIndex >= 0) {
                    currentStepIndex = prevIndex
                    val prevStep = steps[prevIndex]
                    currentPageIndex = (prevStep.pages.size - 1).coerceAtLeast(0)
                }
            }
        }
    }

    fun finish() {
        currentStepIndex = null
        currentPageIndex = 0
        isIsolated = false
        onFinish()
    }

    val isShowing: Boolean get() = currentStepIndex != null

    val isLastStep: Boolean get() = isIsolated || (currentStepIndex == steps.size - 1)

    val canGoPrevious: Boolean get() = currentPageIndex > 0 || (!isIsolated && (currentStepIndex ?: 0) > 0)

    val pageCount: Int get() = if (isIsolated) {
        currentStep?.pages?.size ?: 0
    } else {
        steps.sumOf { it.pages.size }
    }

    val pageIndex: Int get() = if (isIsolated) {
        currentPageIndex
    } else {
        val currentStepIdx = currentStepIndex ?: 0
        var count = 0
        for (i in 0 until currentStepIdx) {
            count += steps[i].pages.size
        }
        count + currentPageIndex
    }

    fun getTargetBounds(paddingPx: Float): List<TargetBoundInfo> {
        val step = currentStep ?: return emptyList()
        val host = hostCoordinates ?: return emptyList()
        if (!host.isAttached) return emptyList()

        val hostBounds = host.boundsInWindow()
        val hostPosition = host.positionInWindow()

        val page = step.pages.getOrNull(currentPageIndex) ?: return emptyList()

        return page.targetIds.mapNotNull { id ->
            val targetInfo = targetInfoMap[id] ?: return@mapNotNull null
            val coordinates = targetInfo.coordinates
            if (coordinates.isAttached) {
                val targetBounds = coordinates.boundsInWindow()
                val visibleBounds = hostBounds.intersect(targetBounds)

                if (visibleBounds.isEmpty) return@mapNotNull null

                val localRect = visibleBounds.translate(-hostPosition.x, -hostPosition.y)

                TargetBoundInfo(
                    rect = localRect.inflate(paddingPx),
                    shape = targetInfo.shape
                )
            } else null
        }
    }
}

@Composable
fun rememberWalkthroughState(
    steps: List<WalkthroughStep>,
    onFinish: () -> Unit = {}
): WalkthroughState {
    return remember(steps) {
        WalkthroughState(steps, onFinish)
    }
}
