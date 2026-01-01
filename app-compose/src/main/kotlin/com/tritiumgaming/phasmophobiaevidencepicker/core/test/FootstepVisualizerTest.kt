package com.tritiumgaming.phasmophobiaevidencepicker.core.test

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.feature.footstepvisualizer.ui.FootstepVisualizer

@Composable
@Preview
private fun Preview() {
    SelectiveTheme {
        FootstepVisualizer(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
    }
}
