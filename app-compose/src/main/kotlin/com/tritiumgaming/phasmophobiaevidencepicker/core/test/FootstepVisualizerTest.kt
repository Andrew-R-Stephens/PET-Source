package com.tritiumgaming.phasmophobiaevidencepicker.core.test

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.feature.footstepvisualizer.ui.FootstepVisualizer
import kotlin.time.Duration.Companion.seconds

@Composable
@Preview
private fun Preview() {
    SelectiveTheme {
        FootstepVisualizer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
                .height(200.dp),
            alpha = .01f,
            maxDisplayBPM = 400f,
            viewportSeconds = 10.seconds,
            timeSplit = 10f,
            bpmSplit = 100f
        )
    }
}
