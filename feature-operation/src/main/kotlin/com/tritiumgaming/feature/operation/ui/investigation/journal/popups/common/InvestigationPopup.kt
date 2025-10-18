package com.tritiumgaming.feature.operation.ui.investigation.journal.popups.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.theme.palette.LocalPalette

@Composable
fun InvestigationPopup(
    modifier: Modifier,
    shown: Boolean = false,
    content: @Composable () -> Unit
) {
    if(!shown) return

    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = LocalPalette.current.surface.color.copy(alpha = .75f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            content()

            // TODO Adview
        }
    }
}

