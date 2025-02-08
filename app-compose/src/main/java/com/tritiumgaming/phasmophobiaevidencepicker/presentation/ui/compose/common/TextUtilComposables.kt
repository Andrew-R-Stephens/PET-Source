package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.sp
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.JournalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.theme.red_M100
import com.tritiumgaming.phasmophobiaevidencepicker.theme.transparent
import com.tritiumgaming.phasmophobiaevidencepicker.theme.yellow_M100
import com.tritiumgaming.phasmophobiaevidencepicker.util.TextCase
import org.jetbrains.annotations.TestOnly

@Composable
@Preview
@TestOnly
private fun AutoResizedTextPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = JournalTypography
    ) {

        Surface(
            modifier = Modifier
                .width(200.dp)
                .height(80.dp),
                //.wrapContentHeight(),
            color = LocalPalette.current.surface.color
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                AutoResizedText(
                    modifier = Modifier
                        .weight(1f),
                    text = "Hello there",
                    textAlign = TextAlign.Center,
                    minFontSize = 5.sp,
                    maxFontSize = 50.sp,
                    color = LocalPalette.current.textFamily.body,
                    style = LocalTypography.current.primary.regular
                )
                AutoResizedText(
                    modifier = Modifier
                        .weight(1f),
                    text = "Hello there",
                    textAlign = TextAlign.Center,
                    autoResizeStyle = AutoResizedStyleType.SQUEEZE,
                    color = LocalPalette.current.textFamily.body,
                    style = LocalTypography.current.primary.regular
                )
                AutoResizedText(
                    modifier = Modifier
                        .weight(1f),
                    text = "Hello there",
                    textAlign = TextAlign.Center,
                    autoResizeStyle = AutoResizedStyleType.CONSTRAIN,
                    color = LocalPalette.current.textFamily.body,
                    style = LocalTypography.current.primary.regular
                )
                AutoResizedText(
                    modifier = Modifier
                        .weight(1f),
                    text = "Hello there",
                    textAlign = TextAlign.Center,
                    color = LocalPalette.current.textFamily.body,
                    style = LocalTypography.current.primary.regular
                )

                AutoResizedText(
                    modifier = Modifier
                        .weight(1f),
                    text = "Hello there",
                    textAlign = TextAlign.Center,
                    color = yellow_M100,
                    style = LocalTypography.current.primary.regular,
                    borderStyle = LocalTypography.current.primary.regular.copy(
                        drawStyle = Stroke(
                            miter = 1f,
                            width = 2f,
                            join = StrokeJoin.Round,
                        ),
                        color = red_M100
                    ),
                    autoResizeStyle = AutoResizedStyleType.SQUEEZE
                )
            }
        }
    }
}

@Composable
fun AutoResizedText(
    modifier: Modifier = Modifier,
    text: String = "Test Haha",
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    borderStyle: TextStyle? = null,
    autoResizeStyle: AutoResizedStyleType = AutoResizedStyleType.CONSTRAIN,
    textCase: TextCase = TextCase.Unspecified,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Unspecified,
    constrainWidth: Boolean = true,
    constrainHeight: Boolean = true,
) {

    Box(
        modifier = modifier
    ) {
        
        borderStyle?.let {

            AutoResizedText(
                modifier = modifier,
                text = text,
                color = it.color,
                style = it,
                textCase = textCase,
                textAlign = textAlign,
                minFontSize = autoResizeStyle.min,
                maxFontSize = autoResizeStyle.max,
                stepSize = autoResizeStyle.step,
                constrainWidth = constrainWidth,
                constrainHeight = constrainHeight
            )

        }

        AutoResizedText(
            modifier = modifier,
            text = text,
            style = style,
            textCase = textCase,
            color = color,
            textAlign = textAlign,
            minFontSize = autoResizeStyle.min,
            maxFontSize = autoResizeStyle.max,
            stepSize = autoResizeStyle.step,
            constrainWidth = constrainWidth,
            constrainHeight = constrainHeight
        )

    }

}

@Composable
fun AutoResizedText(
    modifier: Modifier = Modifier
        .fillMaxWidth(),
    text: String = "Test Haha",
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    textCase: TextCase = TextCase.Unspecified,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Center,
    minFontSize: TextUnit = 1.sp,
    maxFontSize: TextUnit = style.fontSize,
    stepSize: Float = 5f,
    constrainWidth: Boolean = true,
    constrainHeight: Boolean = true
) {
    var currentTextSize by remember { mutableStateOf(maxFontSize) }
    var shouldDraw by remember { mutableStateOf(false) }

    val convertedText = TextCase.convertCase(text, textCase)

    Row(
        modifier = modifier
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Text(
            modifier = modifier
                .drawWithContent {
                    if (shouldDraw) drawContent()
                },
            text = convertedText,
            fontSize = currentTextSize,
            textAlign = textAlign,
            style = style,
            color = color,
            maxLines = 1,
            softWrap = false,
            onTextLayout = { result ->
                if (((constrainWidth && result.didOverflowWidth)
                            || (constrainHeight && result.didOverflowHeight))) {

                    if (maxFontSize.isUnspecified) {
                        currentTextSize = 12.sp
                    }

                    val decrement = (currentTextSize.value * .25f).coerceAtLeast(stepSize)
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
}

enum class AutoResizedStyleType(
    val min: TextUnit = 18.sp,
    val max: TextUnit = 50.sp,
    val step: Float = 1f,
) {
    CONSTRAIN(18.sp, 50.sp, 1f),
    SQUEEZE(1.sp, 200.sp, 5f),
}
