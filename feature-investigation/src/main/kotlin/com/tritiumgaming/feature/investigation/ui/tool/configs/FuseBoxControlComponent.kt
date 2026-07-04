package com.tritiumgaming.feature.investigation.ui.tool.configs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.widgets.tooltip.CommonTooltip
import com.tritiumgaming.shared.data.operation.model.OperationOverrideData.Companion.FuseBoxFlag

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
        FuseBoxFlag.FUSEBOX_BROKEN -> {
            FuseButtonTheme(
                R.drawable.ic_power_broken,
                LocalPalette.current.onSurface
            )
        }

    }

    CommonTooltip(
        modifier = Modifier,
        tooltipText = stringResource(R.string.general_label_power)
    ) {
        Surface(
            onClick = { onTogglePower() },
            modifier = modifier,
            shape = RoundedCornerShape(8.dp),
            color = LocalPalette.current.surfaceContainer,
            contentColor = theme.foreground
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(12.dp),
                    painter = painterResource(theme.icon),
                    contentDescription = "Toggle Power"
                )
            }
        }
    }
}

internal data class FuseBoxUiState(
    val flag: FuseBoxFlag,
)

internal data class FuseBoxUiActions(
    val onTogglePower: () -> Unit
)

