package com.tritiumgaming.feature.investigation.ui.section

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.FrameRateCategory
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.preferredFrameRate
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.feature.investigation.ui.common.footstep.FootstepVisualizer
import kotlin.time.Duration.Companion.seconds


@Composable
fun ToolbarFootstepsVisualizerSection(
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier
    ) {

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .preferredFrameRate(FrameRateCategory.Normal)
        ) {

            var rememberSmoothedBPM by remember {
                mutableFloatStateOf(0f)
            }

            var rememberInstantBPM by remember {
                mutableFloatStateOf(0f)
            }

            var rememberSampledBPM by remember {
                mutableFloatStateOf(0f)
            }

            FootstepVisualizer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                graphBackgroundColor = Color.Unspecified,
                graphXAxisColor = LocalPalette.current.onSurface,
                graphYAxisColor = Color.Unspecified,
                sampleBackgroundColor = LocalPalette.current.surfaceContainer.copy(alpha = .5f),
                labelColor = LocalPalette.current.onSurface,
                endpointColor = LocalPalette.current.primary,
                lineSegmentColor = LocalPalette.current.primary,
                meterBeatLineColor = LocalPalette.current.onSurface,
                meterColor = LocalPalette.current.onSurface,
                meterOnColor = LocalPalette.current.tertiary,
                alpha = .01f,
                viewportBPM = 360,
                viewportDuration = 10.seconds,
                durationSplit = 10f,
                bpmSplit = 120f,
                samplingInterval = 3.seconds
            ) { instantBPM, smoothedBPM, sampleAverage ->
                rememberSmoothedBPM = smoothedBPM
                rememberInstantBPM = instantBPM
                rememberSampledBPM = sampleAverage
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "BPM: ${rememberSmoothedBPM.toInt()}; IPM: ${rememberInstantBPM.toInt()}",
                color = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "MSPS: ${rememberSmoothedBPM/60f}; MIPM: ${rememberInstantBPM/60f}",
                color = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "AVG BPM: $rememberSampledBPM",
                color = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "AVG MPS: ${rememberSampledBPM/60f}",
                color = LocalPalette.current.onSurface
            )

        }
    }

}
