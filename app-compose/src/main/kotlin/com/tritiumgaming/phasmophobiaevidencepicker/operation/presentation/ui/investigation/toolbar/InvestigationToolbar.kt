package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.DampingRatioNoBouncy
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.modifiers.DisplayOrientation
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.modifiers.fadingEdges
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun InvestigationToolbar(
    modifier: Modifier = Modifier,
    stickyContentStart: @Composable () -> Unit = {},
    stickyContentEnd: @Composable () -> Unit = {},
    scrollContent: @Composable () -> Unit,
) {
    val scrollState = rememberScrollState()

    when (LocalConfiguration.current.orientation) {
        ORIENTATION_PORTRAIT -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .wrapContentSize()
                        .background(LocalPalette.current.surface.onColor, CircleShape)
                ) {
                    stickyContentStart()
                }

                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f, fill = true)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(LocalPalette.current.surface.onColor, RoundedCornerShape(8.dp))
                        .horizontalScroll(scrollState)
                        .padding(4.dp)
                        .fadingEdges(
                            scrollState = scrollState,
                            orientation =
                                DisplayOrientation.HORIZONTAL,
                            topEdgeHeight = 64.dp,
                            bottomEdgeHeight = 64.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    scrollContent()
                }

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .wrapContentSize()
                        .background(LocalPalette.current.surface.onColor, CircleShape)
                ) {
                    stickyContentEnd()
                }
            }

        }
        else -> {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .wrapContentSize()
                        .background(LocalPalette.current.surface.onColor, CircleShape)
                ) {
                    stickyContentStart()
                }

                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f, fill = true)
                        .fillMaxHeight()
                        .wrapContentWidth()
                        .background(LocalPalette.current.surface.onColor, RoundedCornerShape(8.dp))
                        .verticalScroll(scrollState)
                        .fadingEdges(scrollState, DisplayOrientation.VERTICAL, 64.dp, 64.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    scrollContent()
                }

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .wrapContentSize()
                        .background(LocalPalette.current.surface.onColor, CircleShape)
                ) {
                    stickyContentEnd()
                }
            }

        }
    }
}

@Composable
fun ToolbarItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clickable { onClick() }
            .padding(8.dp),
    ) {
        content()
    }
}

@Composable
fun ResetButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    var isRunning by remember{ mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if(isRunning) 1f else 0f,
        animationSpec = spring(
            stiffness = StiffnessLow,
            dampingRatio = DampingRatioLowBouncy
        ),
        finishedListener = {
            isRunning = false
        },
        label = "",
    )

    val foregroundColor = LocalPalette.current.textFamily.body

    Box(
        modifier = modifier
            .size(48.dp)
            .clickable {
                onClick()
                isRunning = true
            }
    ) {
        Image(
            painterResource(id = R.drawable.ic_control_reset),
            contentDescription = "Reset Drawable",
            colorFilter = ColorFilter.tint(foregroundColor),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .clip(RectangleShape)
                .rotate(
                    if (isRunning) {
                        rotation * -360f
                    } else {
                        0f
                    }
                )
                .padding(6.dp)
        )
    }
}

@Composable
fun CollapseButton(
    modifier: Modifier = Modifier,
    isCollapsed: Boolean = false,
    onClick: () -> Unit = {}
) {

    val rotation by animateFloatAsState(
        targetValue = if(isCollapsed) 1f else 0f,
        animationSpec = spring(
            stiffness = StiffnessLow,
            dampingRatio = DampingRatioNoBouncy
        ),
        label = "",
    )

    val foregroundColor = LocalPalette.current.textFamily.body

    Box(
        modifier = modifier
            .size(48.dp)
            //.border(1.5.dp, Color(foregroundColor), RoundedCornerShape(percent = 25))
            .clickable {
                onClick()
            }
    ) {

        val orientationRotate = when(LocalConfiguration.current.orientation) {
            ORIENTATION_PORTRAIT -> 90
            ORIENTATION_LANDSCAPE -> 180
            else -> 0
        }

        Image(
            painterResource(id = R.drawable.ic_arrow_chevron_right),
            contentDescription = "Reset Drawable",
            colorFilter = ColorFilter.tint(foregroundColor),
            modifier = Modifier
                .fillMaxSize(/*.55f*/)
                .align(Alignment.Center)
                .clip(RectangleShape)
                .rotate(orientationRotate + (rotation * 180f))
        )
    }
}

@Composable
fun SanityButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isShownState: StateFlow<Boolean> = MutableStateFlow(false)
) {
    val enabledState by isShownState.collectAsStateWithLifecycle()

    val foregroundColor = LocalPalette.current.textFamily.body

    Box(
        modifier = modifier
            .size(48.dp)
            //.border(1.5.dp, Color(foregroundColor), RoundedCornerShape(percent = 25))
            .clickable {
                onClick()
            }
    ) {

        val orientationRotate = when(LocalConfiguration.current.orientation) {
            ORIENTATION_PORTRAIT -> 90
            ORIENTATION_LANDSCAPE -> 180
            else -> 0
        }

        Image(
            painterResource(id = R.drawable.icon_sanityhead_skull),
            contentDescription = "Sanity Drawable",
            colorFilter = ColorFilter.tint(foregroundColor),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .clip(RectangleShape)
        )
    }
}

@Composable
fun ModifiersButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isShownState: StateFlow<Boolean> = MutableStateFlow(false)
) {
    val enabledState by isShownState.collectAsStateWithLifecycle()

    val foregroundColor = LocalPalette.current.textFamily.body

    Box(
        modifier = modifier
            .size(48.dp)
            //.border(1.5.dp, Color(foregroundColor), RoundedCornerShape(percent = 25))
            .clickable {
                onClick()
            }
    ) {

        val orientationRotate = when(LocalConfiguration.current.orientation) {
            ORIENTATION_PORTRAIT -> 90
            ORIENTATION_LANDSCAPE -> 180
            else -> 0
        }

        Image(
            painterResource(id = R.drawable.ic_eraser),
            contentDescription = "Modifiers Drawable",
            colorFilter = ColorFilter.tint(foregroundColor),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .clip(RectangleShape)
        )
    }
}

@Composable
@Preview
fun IconPreview() {

    SelectiveTheme {

        Column{

            SanityButton()
            ResetButton()
            CollapseButton()
        }
    }

}