package com.tritiumgaming.feature.investigation.ui.tool.statusbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.common.util.FormatterUtils.toPercentageString
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.app.mappers.phase.toPhaseTitle
import com.tritiumgaming.feature.investigation.app.mappers.phase.toStringResource
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimer
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimerUiState
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.phase.PhaseUiState

@Composable
internal fun OperationStatusBar(
    modifier: Modifier = Modifier,
    bundle: StatusBarComponentStateBundle
) {
    val sanityPercentString = bundle.sanityUiState.sanityLevel.toPercentageString()

    FlowRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        itemVerticalAlignment = Alignment.CenterVertically
    ) {

        Surface(
            modifier = Modifier,
            color = LocalPalette.current.surfaceContainerLow,
            shape = RoundedCornerShape(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = "${stringResource(R.string.investigation_label_phase)}:",
                    color = LocalPalette.current.onSurface,
                    style = LocalTypography.current.tertiary.regular.copy(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    modifier = Modifier,
                    text = stringResource(
                        bundle.phaseUiState.type.toPhaseTitle().toStringResource()),
                    color = LocalPalette.current.onSurfaceVariant,
                    style = LocalTypography.current.tertiary.regular.copy(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                )

                if(bundle.digitalTimerUiState.remainingTime > 0L) {
                    DigitalTimer(
                        modifier = Modifier,
                        state = bundle.digitalTimerUiState,
                        fontSize = 12.sp,
                        color = LocalPalette.current.onSurfaceVariant
                    )
                }
            }
        }

        Surface(
            modifier = Modifier,
            color = LocalPalette.current.surfaceContainerLow,
            shape = RoundedCornerShape(8.dp),
        ) {

            Row(
                modifier = Modifier
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = "${stringResource(R.string.investigation_sanitymeter_title)}:",
                    color = LocalPalette.current.onSurface,
                    style = LocalTypography.current.tertiary.regular.copy(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    modifier = Modifier,
                    text = sanityPercentString,
                    color = LocalPalette.current.onSurfaceVariant,
                    style = LocalTypography.current.tertiary.regular.copy(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }

    }
}

internal data class StatusBarComponentStateBundle(
    val sanityUiState: PlayerSanityUiState,
    val digitalTimerUiState: DigitalTimerUiState,
    val phaseUiState: PhaseUiState
)