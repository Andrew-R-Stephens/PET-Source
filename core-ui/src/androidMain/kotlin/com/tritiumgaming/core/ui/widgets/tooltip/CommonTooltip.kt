package com.tritiumgaming.core.ui.widgets.tooltip

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalTypography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTooltip(
    modifier: Modifier = Modifier,
    tooltipText: String,
    onClick: (() -> Unit)? = null,
    content: @Composable (Modifier) -> Unit
) {
    val tooltipState = rememberTooltipState()
    val scope = rememberCoroutineScope()

    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            positioning = TooltipAnchorPosition.Above
        ),
        tooltip = {
            PlainTooltip(
                containerColor = LocalPalette.current.surfaceContainerHighest,
                contentColor = LocalPalette.current.onSurface
            ) {
                Text(
                    text = tooltipText,
                    style = LocalTypography.current.quaternary.regular,
                    fontSize = 12.sp
                )
            }
        },
        state = tooltipState
    ) {
        Box(
            modifier = modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    scope.launch { tooltipState.show() }
                    onClick?.invoke()
                }
        ) {
            content(Modifier)
        }
    }
}
