package com.tritiumgaming.core.ui.widgets.menus

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R

@Composable
fun IconDropdownMenu(
    modifier: Modifier = Modifier,
    primaryContent: @Composable () -> Unit,
    dropdownContent: @Composable ColumnScope.() -> Unit,
    colors: IconDropdownMenuColors = IconDropdownMenuColors(),
    showExpandIcon: () -> Boolean = { true },
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
            .background(Color.Transparent)
    ) {

        Box {
            PrimarySelector(
                modifier = Modifier.size(48.dp),
                content = { primaryContent() },
                onClick = { expanded = true },
            )

            if(showExpandIcon()) {
                val arrowImage: Int =
                    if (expanded) R.drawable.ic_expand_circle_up
                    else R.drawable.ic_expand_circle_down

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(colors.primaryContentBackground)
                        .fillMaxSize(fraction = .4f)
                        .align(Alignment.BottomEnd)
                ) {
                    Image(
                        painter = painterResource(id = arrowImage),
                        contentDescription = "Arrow"
                    )
                }
            }
        }

        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme.copy(
                surface = colors.dropdownContentBackground,
                background = colors.dropdownContentBackground,
                onBackground = colors.dropdownContentBackground,
                surfaceContainer = colors.dropdownContentBackground,
                onPrimary = colors.dropdownContentBackground,
                surfaceContainerLowest = colors.dropdownContentBackground,
            ),
            shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(20))
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(48.dp)
                    .padding(4.dp)
                    .align(Alignment.Center),
                scrollState = rememberScrollState()
            ) {
                DropdownColumn(
                    content = dropdownContent
                )
            }
        }
    }
}

@Composable
fun DropdownColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        content()
    }
}

@Composable
fun PrimarySelector(
    modifier: Modifier,
    content: @Composable () -> Unit,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .aspectRatio(1f),
        onClick = { onClick() }
    ) { content() }
}

@Composable
fun SecondarySelector(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    IconButton(
        modifier = modifier
            .aspectRatio(1f),
        onClick = { onClick() }
    ) { content() }
}

data class IconDropdownMenuColors(
    val primaryContentBackground: Color = Color.Transparent,
    val dropdownContentBackground: Color = Color.Transparent
)