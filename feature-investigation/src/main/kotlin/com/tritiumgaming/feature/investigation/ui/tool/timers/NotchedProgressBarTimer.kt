package com.tritiumgaming.feature.investigation.ui.tool.timers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.ui.theme.DigitalDreamTextStyle
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBar
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarBundle
import java.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun NotchedProgressBarTimer(
    modifier: Modifier = Modifier,
    bundle: NotchedProgressBarBundle,
    icon: @Composable (Modifier) -> Unit = {}
) {

    val minutes = bundle.state.remaining.milliseconds.inWholeMinutes
    val seconds = (bundle.state.remaining.milliseconds.inWholeSeconds % (minutes.toFloat())).toLong()

    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            text = bundle.title,
            color = bundle.colors.label,
            fontSize = 12.sp
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                icon(Modifier)

                Text(
                    text = "$minutes:${"%02d".format(seconds)}",
                    color = bundle.colors.label,
                    style = DigitalDreamTextStyle.copy(
                        textAlign = TextAlign.Center
                    ),
                    fontSize = 12.sp
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
}
