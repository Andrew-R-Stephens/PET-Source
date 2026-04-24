package com.tritiumgaming.feature.investigation.ui.tool.phase


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.app.mappers.phase.toPhaseTitle
import com.tritiumgaming.feature.investigation.app.mappers.phase.toStringResource
import com.tritiumgaming.shared.data.investigation.model.PhaseData.Companion.DEFAULT
import com.tritiumgaming.shared.data.investigation.model.PhaseData.Companion.DURATION_30_SECONDS
import com.tritiumgaming.shared.data.phase.model.PhaseResources.PhaseIdentifier


@Composable
internal fun PhaseComponent(
    modifier: Modifier = Modifier,
    state: PhaseUiState
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .wrapContentHeight(),
            text = stringResource(state.type.toPhaseTitle().toStringResource()),
            color = LocalPalette.current.onSurface,
            style = LocalTypography.current.tertiary.regular.copy(
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
            ),
            maxLines = 1
        )
    }
}

internal data class PhaseUiState(
    internal val type: PhaseIdentifier = PhaseIdentifier.SETUP,
    internal val canAlertAudio: Boolean = false,
    internal val canFlash: Boolean = true,
    internal val startFlashTime: Long = DEFAULT,
    internal val elapsedFlashTime: Long = DEFAULT,
    internal val maxFlashTime: Long = DURATION_30_SECONDS,
)
