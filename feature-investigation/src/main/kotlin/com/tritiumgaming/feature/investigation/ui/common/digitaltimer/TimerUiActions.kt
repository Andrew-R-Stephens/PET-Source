package com.tritiumgaming.feature.investigation.ui.common.digitaltimer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class TimerUiActions(
    val onToggleTimer: () -> Unit = {},
    val playContent: @Composable (modifier: Modifier) -> Unit = {},
    val pauseContent: @Composable (modifier: Modifier) -> Unit = {}
)