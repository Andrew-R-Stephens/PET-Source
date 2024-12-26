package com.tritiumgaming.phasmophobiaevidencepicker.views.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.sp
import com.google.common.net.InetAddresses.decrement
import com.tritiumgaming.phasmophobiaevidencepicker.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Non_Colorblind
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.utils.TextCase
import org.jetbrains.annotations.TestOnly

@Composable
@Preview
@TestOnly
fun AutoResizedTextPreview() {
    SelectiveTheme(
        theme = Non_Colorblind
    ) {

        Surface(
            modifier = Modifier
                .width(200.dp)
                .height(20.dp),
                //.wrapContentHeight(),
            color = LocalPalette.current.surface.color
        ){
            AutoResizedText(
                modifier = Modifier
                    .fillMaxSize(),
                text = "Hello there",
                textAlign = TextAlign.Center,
                minFontSize = 5.sp,
                maxFontSize = 50.sp,
                color = LocalPalette.current.textFamily.body,
                font = LocalTypography.current.primary.regular
            )
        }
    }
}

@Composable
fun AutoResizedText(
    modifier: Modifier = Modifier,
    text: String = "Test Haha",
    font: TextStyle = MaterialTheme.typography.bodyLarge,
    minFontSize: TextUnit = 2.sp,
    maxFontSize: TextUnit = font.fontSize,
    textCase: TextCase = TextCase.Unspecified,
    stepSize: Float = 5f,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Unspecified,
    constrainWidth: Boolean = true,
    constrainHeight: Boolean = true
) {
    var currentTextSize by remember { mutableStateOf(maxFontSize) }
    var shouldDraw by remember { mutableStateOf(false) }

    val convertedText = TextCase.convertCase(text, textCase)

    Text(
        modifier = modifier
            .drawWithContent {
                if (shouldDraw) drawContent()
            },
        text = convertedText,
        fontSize = currentTextSize,
        textAlign = textAlign,
        style = font,
        color = color,
        maxLines = 1,
        softWrap = false,
        onTextLayout = { result ->
            if (((constrainWidth && result.didOverflowWidth)
                        || (constrainHeight && result.didOverflowHeight))) {

                if (maxFontSize.isUnspecified) {
                    currentTextSize = 12.sp
                }

                var decrement = (currentTextSize.value * .25f).coerceAtLeast(stepSize)
                currentTextSize = (currentTextSize.value - decrement).sp

                if(currentTextSize.value < minFontSize.value) {
                    currentTextSize = minFontSize
                }

            } else {
                shouldDraw = true
            }
        }
    )

}
