package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.common

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.common.compose.DisplayOrientation
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.common.compose.fadingEdges
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getColorFromAttribute
import org.jetbrains.annotations.TestOnly

data class ToolBarItemPair(val content: Any, val onClick: () -> Unit = {})

@Composable
@TestOnly
@Preview(device = "spec:parent=pixel_5")
private fun TestPortrait() {
    val composable: @Composable () -> Unit = { CollapseButton() }

    val contentArray: Array<ToolBarItemPair> = arrayOf(
        ToolBarItemPair(composable),
        ToolBarItemPair(R.drawable.ic_gear)
    )

    InvestigationToolbar(contentArray)
}

@Composable
@TestOnly
@Preview(device = "spec:parent=pixel_5,orientation=landscape")
private fun TestLandscape() {
    val composable: @Composable () -> Unit = { CollapseButton() }

    val contentArray: Array<ToolBarItemPair> = arrayOf(
        ToolBarItemPair(composable),
        ToolBarItemPair(R.drawable.ic_gear)
    )

    InvestigationToolbar(contentArray)
}

@Composable
fun InvestigationToolbar(
    items: Array<ToolBarItemPair> = arrayOf()
) {
    val scrollState = rememberScrollState()

    val backgroundColor =
        Color(getColorFromAttribute(LocalContext.current, R.attr.backgroundColorOnBackground))
    LocalPalette.current.background.onColor

    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        ORIENTATION_PORTRAIT -> {
            val scrollState = rememberScrollState()
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(/*backgroundColor*/LocalPalette.current.surface.onColor, RoundedCornerShape(8.dp))
                    .horizontalScroll(scrollState)
                    .fadingEdges(scrollState, DisplayOrientation.HORIZONTAL, 64.dp, 64.dp)
                    .padding(4.dp)
            ) {
                ToolbarItemList(items)
            }
        }
        else -> {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth()
                    .background(/*backgroundColor*/LocalPalette.current.surface.onColor, RoundedCornerShape(8.dp))
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
private fun ToolbarItemList(
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
private fun ToolbarItem(
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
private fun ToolbarItem(
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
private fun ToolbarItem(
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
fun CollapseButton(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel =
        viewModel ( factory = InvestigationViewModel.Factory ),
    onClick: () -> Unit = {}
) {
    val collapsedState =
        investigationViewModel.isInvestigationToolsDrawerCollapsed.collectAsStateWithLifecycle()


    val rotation by animateFloatAsState(
        targetValue = if(collapsedState.value) 1f else 0f,
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
                investigationViewModel.toggleInvestigationToolsDrawerState()
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
