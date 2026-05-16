package com.tritiumgaming.feature.investigation.ui.tool.configs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.shared.data.investigation.model.DifficultyOverridesData.Companion.FuseBoxFlag

@Composable
internal fun FuseBoxButton(
    modifier: Modifier,
    flag: FuseBoxFlag,
    onTogglePower: () -> Unit
) {
    data class FuseButtonTheme(
        val icon: Int, val foreground: Color)

    val theme = when (flag) {
        FuseBoxFlag.FUSEBOX_ENABLED -> {
            FuseButtonTheme(
                R.drawable.ic_fuse_box_fill,
                LocalPalette.current.primary
            )
        }
        FuseBoxFlag.FUSEBOX_DISABLED -> {
            FuseButtonTheme(
                R.drawable.ic_fuse_box,
                LocalPalette.current.onSurface
            )
        }
    }

    Surface(
        onClick = { onTogglePower() },
        modifier = modifier
            .border(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = LocalPalette.current.surfaceContainerLow
                )
            ),
        shape = RoundedCornerShape(8.dp),
        color = LocalPalette.current.surfaceContainer,
        contentColor = theme.foreground
    ) {
        Icon(
            modifier = Modifier
                .padding(12.dp),
            painter = painterResource(theme.icon),
            contentDescription = "Toggle Power"
        )
    }
}

internal data class FuseBoxUiState(
    val flag: FuseBoxFlag,
)

internal data class FuseBoxUiActions(
    val onTogglePower: () -> Unit
)

