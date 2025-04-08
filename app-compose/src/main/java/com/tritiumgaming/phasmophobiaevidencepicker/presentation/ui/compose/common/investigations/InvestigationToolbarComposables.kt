package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.investigations

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.view.View
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.DampingRatioNoBouncy
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.DisplayOrientation
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.fadingEdges
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getColorFromAttribute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ToolBarItemPair(val content: Any, val onClick: () -> Unit = {})

@Composable
fun InvestigationToolbar(
    items: Array<ToolBarItemPair> = arrayOf()
) {
    val backgroundColor =
        Color(getColorFromAttribute(LocalContext.current, R.attr.backgroundColorOnBackground))

    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            val scrollState = rememberScrollState()
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(backgroundColor, RoundedCornerShape(8.dp))
                    .horizontalScroll(scrollState)
                    .fadingEdges(scrollState, DisplayOrientation.HORIZONTAL, 64.dp, 64.dp)
                    .padding(4.dp)
            ) {
                ToolbarItemList(items)
            }
        }
        else -> {
            val scrollState = rememberScrollState()
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth()
                    .background(backgroundColor, RoundedCornerShape(8.dp))
                    .verticalScroll(scrollState)
                    .fadingEdges(scrollState, DisplayOrientation.VERTICAL, 64.dp, 64.dp)
                    .padding(4.dp)
            ) {
                ToolbarItemList(items)
            }
        }
    }
}

@Composable
fun ToolbarItemList(
    contentArray: Array<ToolBarItemPair> = arrayOf()
) {
    for (index in contentArray.indices) {

        Box(modifier = Modifier.padding(4.dp)) {
            val onClick = contentArray[index].onClick
            when(val content = contentArray[index].content) {
                is Int -> ToolbarItem(content, onClick)
                is View -> ToolbarItem(contentView = content, onClick)
                else -> {
                    (content as? @Composable () -> Unit)?.let { composable ->
                        ToolbarItem(composable = composable, onClick)
                    }

                }
            }
        }
    }
}

@Composable
fun ToolbarItem(
    image: Int = R.drawable.ic_expand_circle_up,
    onClick: () -> Unit = { }
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = "",
        modifier = Modifier
            .size(48.dp)
            .clickable { onClick() }
    )
}

@Composable
fun ToolbarItem(
    contentView: View,
    onClick: () -> Unit = { }
) {
    AndroidView(
        modifier = Modifier
            .size(48.dp)
            .clickable { onClick() },
        factory = {
            contentView
        },
        update = { view ->
            view.invalidate()
        }
    )
}

@Composable
fun ToolbarItem(
    composable: @Composable () -> Unit,
    onClick: () -> Unit = { }
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clickable { onClick() },
    ) {
        composable()
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

    val foregroundColor = getColorFromAttribute(LocalContext.current, R.attr.textColorBody)

    Box(
        modifier = modifier
            .size(48.dp)
            .border(1.5.dp, Color(foregroundColor), RoundedCornerShape(percent = 25))
            .clickable {
                onClick()
                isRunning = true
            }
    )
    {
        Image(
            painterResource(id = R.drawable.ic_control_reset),
            contentDescription = "Reset Drawable",
            colorFilter = ColorFilter.tint(Color(foregroundColor)),
            modifier = Modifier
                .fillMaxSize(.55f)
                .align(Alignment.Center)
                .clip(RectangleShape)
                .rotate(
                    if (isRunning) {
                        rotation * -360f
                    } else {
                        0f
                    }
                )
        )
    }
}

@Composable
@Preview
fun CollapseButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isCollapsedState: StateFlow<Boolean> = MutableStateFlow(false)
) {
    val collapsedState by isCollapsedState.collectAsStateWithLifecycle()

    val rotation by animateFloatAsState(
        targetValue = if(collapsedState) 1f else 0f,
        animationSpec = spring(
            stiffness = StiffnessLow,
            dampingRatio = DampingRatioNoBouncy
        ),
        label = "",
    )

    val foregroundColor = getColorFromAttribute(LocalContext.current, R.attr.textColorBody)

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
            colorFilter = ColorFilter.tint(Color(foregroundColor)),
            modifier = Modifier
                .fillMaxSize(/*.55f*/)
                .align(Alignment.Center)
                .clip(RectangleShape)
                .rotate(orientationRotate + (rotation * 180f))
        )
    }
}

@Composable
@Preview
fun SanityButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isShownState: StateFlow<Boolean> = MutableStateFlow(false)
) {
    val enabledState by isShownState.collectAsStateWithLifecycle()

    val foregroundColor = getColorFromAttribute(LocalContext.current, R.attr.textColorBody)

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
            colorFilter = ColorFilter.tint(Color(foregroundColor)),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .clip(RectangleShape)
        )
    }
}

@Composable
@Preview
fun ModifiersButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isShownState: StateFlow<Boolean> = MutableStateFlow(false)
) {
    val enabledState by isShownState.collectAsStateWithLifecycle()

    val foregroundColor = getColorFromAttribute(LocalContext.current, R.attr.textColorBody)

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
            colorFilter = ColorFilter.tint(Color(foregroundColor)),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .clip(RectangleShape)
        )
    }
}
