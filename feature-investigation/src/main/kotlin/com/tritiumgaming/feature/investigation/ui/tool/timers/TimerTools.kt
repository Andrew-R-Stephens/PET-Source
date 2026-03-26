package com.tritiumgaming.feature.investigation.ui.tool.timers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.icon.impl.composite.HuntCooldownDurationIcon
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarBundle
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiColors
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiState
import com.tritiumgaming.core.ui.widgets.progressbar.ProgressBarNotch

@Composable
internal fun TimerTools(
    modifier: Modifier = Modifier,
    component: @Composable ColumnScope.(Modifier) -> Unit = {},
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        component(Modifier)
    }
}

@Composable
fun ProgressBarTimer(
    modifier: Modifier = Modifier,
    bundle: NotchedProgressBarBundle,
    iconComponent: @Composable (Modifier) -> Unit = {}
) {
    NotchedProgressBarTimer(
        modifier = modifier,
        bundle = bundle,
        icon = { modifier -> iconComponent(modifier) }
    )
}

@Preview
@Composable
private fun Preview() {

    SelectiveTheme {

        ProgressBarTimer(
            modifier = Modifier,
            NotchedProgressBarBundle(
                title = "Smudge Hunt Protection",
                state = NotchedProgressBarUiState(
                    max = 180000,
                    remaining = 120000,
                    notches = listOf(
                        ProgressBarNotch("Yurei", 180000),
                        ProgressBarNotch("Normal", 90000),
                        ProgressBarNotch("Demon", 60000)
                    )
                ),
                colors = NotchedProgressBarUiColors(
                    remaining = LocalPalette.current.primary,
                    background = LocalPalette.current.surface,
                    border = LocalPalette.current.onSurface,
                    notch = LocalPalette.current.onSurface,
                    label = LocalPalette.current.onSurface,
                )
            )
        ) { modifier ->
            HuntCooldownDurationIcon(
                modifier = modifier,
                colors = IconVectorColors.defaults().copy(
                    fillColor = LocalPalette.current.onSurface
                )
            )
        }

    }

}
