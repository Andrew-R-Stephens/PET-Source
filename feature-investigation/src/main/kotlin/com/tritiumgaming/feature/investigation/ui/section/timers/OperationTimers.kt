package com.tritiumgaming.feature.investigation.ui.section.timers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tritiumgaming.feature.investigation.ui.tool.timers.NotchedProgressBarBundle
import com.tritiumgaming.feature.investigation.ui.tool.timers.TimerTools

@Composable
internal fun OperationTimers(
    modifier: Modifier = Modifier,
    smudgeHuntPreventionBundle: NotchedProgressBarBundle,
    smudgeBlindingBundle: NotchedProgressBarBundle,
    huntDurationBundle: NotchedProgressBarBundle,
    huntGapBundle: NotchedProgressBarBundle
) {
    TimerTools(
        modifier = modifier,
        smudgeHuntPreventionBundle = smudgeHuntPreventionBundle,
        smudgeBlindingBundle = smudgeBlindingBundle,
        huntDurationBundle = huntDurationBundle,
        huntGapBundle = huntGapBundle
    )
}