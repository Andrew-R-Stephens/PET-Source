package com.tritiumgaming.feature.investigation.ui.tool.sanity

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.widgets.tooltip.CommonTooltip

@Composable
internal fun PlayerDeathButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val fillColor = LocalPalette.current.onSurfaceVariant
    val strokeColor = LocalPalette.current.onSurface
    var color = if(isPressed) { fillColor } else { strokeColor }

    LaunchedEffect(isPressed) {
        color = if(isPressed) { fillColor } else { strokeColor }
    }

    CommonTooltip(
        modifier = Modifier,
        tooltipText = stringResource(R.string.investigation_sanity_player_death),
        onClick = onClick
    ) {
        Button(
            modifier = modifier,
            interactionSource = interactionSource,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Color.Transparent
            ),
            onClick = onClick,
            contentPadding = PaddingValues(8.dp)
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(R.drawable.ic_skull),
                contentDescription = null,
                tint = color
            )
        }
    }
}
