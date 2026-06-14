package com.tritiumgaming.feature.investigation.ui.tool.phase


import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalTypography
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
    Surface(
        modifier = modifier,
        color = LocalPalette.current.surfaceContainerLowest,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .padding(8.dp)
                .basicMarquee(Integer.MAX_VALUE, MarqueeAnimationMode.Immediately),
            text = stringResource(state.type.toPhaseTitle().toStringResource()),
            color = LocalPalette.current.onSurface,
            style = LocalTypography.current.tertiary.regular.copy(
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
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
