package com.tritiumgaming.feature.investigation.ui.tool.sanity


import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.icon.impl.base.SanityMedicationIcon
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.vector.color.IconVectorColors


@Composable
internal fun SanityMedicationButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val fillColor = LocalPalette.current.onSurfaceVariant
    val strokeColor = LocalPalette.current.onSurface
    var color = if(isPressed) { fillColor } else { strokeColor }

    // This effect runs whenever isPressed changes
    LaunchedEffect(isPressed) {
        color = if(isPressed) { fillColor } else { strokeColor }
    }

    Button(
        modifier = modifier,
        interactionSource = interactionSource,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = Color.Transparent
        ),
        onClick = { onClick() },
        contentPadding = PaddingValues(8.dp)
    ) {
        SanityMedicationIcon(
            modifier = Modifier,
            colors = IconVectorColors.defaults().copy(
                fillColor = color,
                strokeColor = color
            )
        )
    }
}
