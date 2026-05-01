package com.tritiumgaming.feature.investigation.ui.tool.statusbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.common.util.ColorUtils
import com.tritiumgaming.core.common.util.FormatterUtils.toPercentageString
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.app.mappers.phase.toPhaseTitle
import com.tritiumgaming.feature.investigation.app.mappers.phase.toStringResource
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimer
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimerUiState
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.SanityMeter
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.phase.PhaseUiState
import com.tritiumgaming.shared.data.phase.model.PhaseResources

@Composable
internal fun OperationStatusBar(
    modifier: Modifier = Modifier,
    sanityLevel: Float,
    remainingTime: String,
    phaseType: PhaseResources.PhaseIdentifier
) {
    val sanityPercentString = sanityLevel.toPercentageString()

    val density = LocalDensity.current
    var textHeight by remember { mutableStateOf(16.dp) }

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
                        phaseType.toPhaseTitle().toStringResource()),
                    color = LocalPalette.current.onSurfaceVariant,
                    style = LocalTypography.current.tertiary.regular.copy(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                )

                if(remainingTime.isNotEmpty()) {
                    DigitalTimer(
                        modifier = Modifier
                            .height(16.dp),
                        remainingTime = remainingTime,
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
                    .height(IntrinsicSize.Min)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {

                SanityMeter(
                    modifier = Modifier
                        .heightIn(min = 18.dp)
                        .height(textHeight)
                        .aspectRatio(1f),
                    sanityLevel = sanityLevel,
                    showText = false,
                    showProgress = false
                )

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .onGloballyPositioned {
                            textHeight = with(density) { it.size.height.toDp() }
                        },
                    text = sanityPercentString,
                    color = LocalPalette.current.onSurfaceVariant,
                    style = LocalTypography.current.tertiary.regular.copy(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 1,
                )

                /*Text(
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
                )*/
            }
        }

    }
}

internal data class StatusBarComponentStateBundle(
    val sanityUiState: PlayerSanityUiState,
    val digitalTimerUiState: DigitalTimerUiState,
    val phaseUiState: PhaseUiState
)