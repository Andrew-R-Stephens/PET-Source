package com.tritiumgaming.feature.investigation.ui.tool.timers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBar
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarBundle
import com.tritiumgaming.feature.investigation.ui.TimerUiState
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimer
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryColumn
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextCategoryTitle
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextSubTitle

@Composable
internal fun NotchedProgressBarTimer(
    modifier: Modifier = Modifier,
    bundle: NotchedProgressBarBundle,
    icon: @Composable (Modifier) -> Unit = {}
) {

    ExpandableCategoryColumn(
        expanded = false,
        containerColor = LocalPalette.current.surfaceContainer,
        defaultContent = { modifier, expanded ->
            ExpandableCategoryRow(
                modifier = modifier,
                isExpanded = expanded
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = bundle.title.uppercase(),
                    style = LocalTypography.current.quaternary.bold,
                    color = bundle.colors.label,
                    fontSize = 14.sp
                )
            }
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
            ) {
                icon(Modifier)

                DigitalTimer(
                    modifier = Modifier
                        .height(12.dp),
                    state = TimerUiState(
                        startTime = bundle.state.max,
                        remainingTime = bundle.state.remaining,
                        paused = !bundle.state.running
                    ),
                    color = bundle.colors.label
                )

            }

            Box(
                modifier = modifier
                    .fillMaxHeight(.5f)
                    .fillMaxWidth()
            ) {
                NotchedProgressBar(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    bundle = bundle
                )
            }

        }
    }

    /*Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            text = bundle.title.uppercase(),
            style = LocalTypography.current.quaternary.bold,
            color = bundle.colors.label,
            fontSize = 14.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
            ) {
                icon(Modifier)

                DigitalTimer(
                    modifier = Modifier
                        .height(12.dp),
                    state = TimerUiState(
                        startTime = bundle.state.max,
                        remainingTime = bundle.state.remaining,
                        paused = !bundle.state.running
                    ),
                    color = bundle.colors.label
                )

            }

            Box(
                modifier = modifier
                    .fillMaxHeight(.5f)
                    .fillMaxWidth()
            ) {
                NotchedProgressBar(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    bundle = bundle
                )
            }

        }
    }*/
}
