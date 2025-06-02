package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose

import android.content.res.Configuration
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getColorFromAttribute
import kotlinx.coroutines.flow.MutableStateFlow

data class ToolBarItemPair(val content: Any, val onClick: () -> Unit = {})

@Composable
@Preview(device = "spec:parent=pixel_5")
fun TestPortrait() {
    val composable: @Composable () -> Unit = {
        CollapseButton(isCollapsedState = (MutableStateFlow(false))) }

    val contentArray: Array<ToolBarItemPair> = arrayOf(
        ToolBarItemPair(composable),
        ToolBarItemPair(R.drawable.ic_gear)
    )

    InvestigationToolbar(contentArray)
}

@Composable
@Preview(device = "spec:parent=pixel_5,orientation=landscape")
fun TestLandscape() {
    val composable: @Composable () -> Unit = {
        CollapseButton(isCollapsedState = (MutableStateFlow(false))) }

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

    val backgroundColor =
        Color(getColorFromAttribute(LocalContext.current, R.attr.backgroundColorOnBackground))

    val configuration = LocalConfiguration.current
    when (val orientation = configuration.orientation) {
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