package com.tritiumgaming.feature.operation.ui.investigation.toolbar.subsection.sanitytracker.controller.timer

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.common.util.FormatterUtils
import com.tritiumgaming.core.ui.theme.DigitalDreamTextStyle
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette

@Composable
fun TimerDisplay(
    modifier: Modifier = Modifier,
    timeMillis: Long
) {

    Text(
        modifier = modifier,
        text = FormatterUtils.formatMillisToTime(timeMillis),
        style = DigitalDreamTextStyle,
        color = LocalPalette.current.onSurface,
        autoSize = TextAutoSize.StepBased(12.sp, maxFontSize = 24.sp, stepSize = 1.sp)
    )

}

@Preview
@Composable
private fun TimerDisplayPreview() {

    SelectiveTheme {
        TimerDisplay(
            modifier = Modifier
                .wrapContentWidth()
                .height(48.dp)
                .padding(4.dp),
            timeMillis = 0
        )
    }
}