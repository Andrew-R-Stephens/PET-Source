package com.tritiumgaming.feature.investigation.ui.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.modifier.DisplayOrientation
import com.tritiumgaming.core.ui.modifier.fadingEdges
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette

@Composable
fun ScrollableToolbar(
    modifier: Modifier = Modifier,
    surfaceColor: Color = LocalPalette.current.surface,
    stickyContentStart: @Composable (Modifier) -> Unit = {},
    stickyContentEnd: @Composable (Modifier) -> Unit = {},
    scrollContent: @Composable (Modifier) -> Unit,
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StickyItemContainer(
            surfaceColor = surfaceColor
        ) { modifier ->
            stickyContentStart(
                modifier
                    .padding(4.dp)
                    .sizeIn(maxHeight = 48.dp)
                    .aspectRatio(1f)
            )
        }

        Row(
            modifier = Modifier
                .weight(1f, true)
                .wrapContentHeight()
                .background(surfaceColor, RoundedCornerShape(8.dp))
                .horizontalScroll(scrollState)
                .fadingEdges(
                    scrollState = scrollState,
                    orientation =
                        DisplayOrientation.HORIZONTAL,
                    topEdgeHeight = 64.dp,
                    bottomEdgeHeight = 64.dp
                ),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            scrollContent(Modifier
                .padding(4.dp)
                .sizeIn(maxHeight = 48.dp)
            )
        }

        StickyItemContainer(
            surfaceColor = surfaceColor
        ) { modifier ->
            stickyContentEnd(
                modifier
                    .padding(4.dp)
                    .sizeIn(maxHeight = 48.dp)
                    .aspectRatio(1f)
            )
        }
    }

}

@Composable
fun InvestigationToolRail(
    modifier: Modifier = Modifier,
    surfaceColor: Color = LocalPalette.current.surface,
    stickyContentStart: @Composable (Modifier) -> Unit = {},
    stickyContentEnd: @Composable (Modifier) -> Unit = {},
    scrollContent: @Composable (Modifier) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxHeight()
            .wrapContentWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StickyItemContainer(
            surfaceColor = surfaceColor
        ) { modifier ->
            stickyContentStart(
                modifier
                    .sizeIn(maxWidth = 48.dp)
                    .aspectRatio(1f)
            )
        }

        Column(
            modifier = Modifier
                .padding(4.dp)
                .weight(1f, fill = true)
                .fillMaxHeight()
                .wrapContentWidth()
                .background(
                    surfaceColor,
                    RoundedCornerShape(8.dp)
                )
                .verticalScroll(scrollState)
                .fadingEdges(
                    scrollState,
                    DisplayOrientation.VERTICAL,
                    64.dp,
                    64.dp
                ),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            scrollContent(Modifier
                .padding(4.dp)
                .sizeIn(maxWidth = 48.dp))
        }

        StickyItemContainer(
            surfaceColor = surfaceColor
        ) {
            stickyContentEnd(
                modifier
                    .sizeIn(maxWidth = 48.dp)
                    .aspectRatio(1f)
            )
        }
    }

}

@Composable
private fun StickyItemContainer(
    modifier: Modifier = Modifier,
    surfaceColor: Color = LocalPalette.current.surface,
    stickyContent: @Composable (Modifier) -> Unit
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .padding(4.dp)
            .background(surfaceColor, CircleShape)
    ) {
        stickyContent(Modifier)
    }
}

@Composable
fun ToolbarItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    content: @Composable (Modifier) -> Unit,
) {
    content(
        modifier
            .clickable { onClick() }
            .padding(8.dp),
    )
}
