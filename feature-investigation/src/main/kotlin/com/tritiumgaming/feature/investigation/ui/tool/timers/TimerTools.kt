package com.tritiumgaming.feature.investigation.ui.tool.timers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.icon.impl.composite.HuntDurationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.HuntGapDurationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.PreventHuntIcon
import com.tritiumgaming.core.ui.icon.impl.composite.SmudgeBlindnessIcon
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
    smudgeHuntPreventionBundle: NotchedProgressBarBundle,
    smudgeBlindingBundle: NotchedProgressBarBundle,
    huntDurationBundle: NotchedProgressBarBundle,
    huntGapBundle: NotchedProgressBarBundle
) {

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SmudgeHuntProtectionTimer(smudgeHuntPreventionBundle)

        SmudgeBlindProtectionTimer(smudgeBlindingBundle)

        HuntDurationTimer(huntDurationBundle)

        HuntGapTimer(huntGapBundle)
    }
}

@Composable
private fun HuntGapTimer(huntGapBundle: NotchedProgressBarBundle) {
    NotchedProgressBarTimer(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        bundle = huntGapBundle,
        icon = {
            HuntGapDurationIcon(
                modifier = Modifier
                    .size(36.dp),
                colors = IconVectorColors.defaults().copy(
                    fillColor = LocalPalette.current.onSurface
                )
            )
        }
    )
}

@Composable
private fun HuntDurationTimer(huntDurationBundle: NotchedProgressBarBundle) {
    NotchedProgressBarTimer(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        bundle = huntDurationBundle,
        icon = {
            HuntDurationIcon(
                modifier = Modifier
                    .size(36.dp),
                colors = IconVectorColors.defaults().copy(
                    fillColor = LocalPalette.current.onSurface
                )
            )
        }
    )
}

@Composable
private fun SmudgeBlindProtectionTimer(smudgeBlindingBundle: NotchedProgressBarBundle) {
    NotchedProgressBarTimer(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        bundle = smudgeBlindingBundle,
        icon = {
            SmudgeBlindnessIcon(
                modifier = Modifier
                    .size(36.dp),
                colors = IconVectorColors.defaults().copy(
                    fillColor = LocalPalette.current.onSurface
                )
            )
        }
    )
}

@Composable
private fun SmudgeHuntProtectionTimer(smudgeHuntPreventionBundle: NotchedProgressBarBundle) {
    NotchedProgressBarTimer(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        bundle = smudgeHuntPreventionBundle,
        icon = { modifier ->
            PreventHuntIcon(
                modifier = modifier
                    .size(36.dp),
                colors = IconVectorColors.defaults().copy(
                    fillColor = LocalPalette.current.onSurface
                )
            )
        }
    )
}

@Preview
@Composable
private fun Preview() {

    SelectiveTheme {
        SmudgeHuntProtectionTimer(
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
        )
    }

}
