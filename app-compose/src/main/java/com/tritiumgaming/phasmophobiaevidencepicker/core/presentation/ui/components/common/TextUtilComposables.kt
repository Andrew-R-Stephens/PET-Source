package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common

import androidx.compose.foundation.basicMarquee
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.red_M100
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.JournalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.yellow_M100
import com.tritiumgaming.phasmophobiaevidencepicker.core.util.TextCase
import org.jetbrains.annotations.TestOnly

@Composable
@Preview
@TestOnly
@Deprecated("Replaced by BasicText Composable")
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
                    containerModifier = Modifier
                        .weight(1f),
                    text = "Hello there",
                    textAlign = TextAlign.Center,
                    autoResizeStyle = AutoResizedStyleType.SQUEEZE,
                    color = LocalPalette.current.textFamily.body,
                    style = LocalTypography.current.primary.regular
                )
                AutoResizedText(
                    containerModifier = Modifier
                        .weight(1f),
                    text = "Hello there",
                    textAlign = TextAlign.Center,
                    autoResizeStyle = AutoResizedStyleType.CONSTRAIN,
                    color = LocalPalette.current.textFamily.body,
                    style = LocalTypography.current.primary.regular
                )
                AutoResizedText(
                    containerModifier = Modifier
                        .weight(1f),
                    text = "Hello there",
                    textAlign = TextAlign.Center,
                    color = LocalPalette.current.textFamily.body,
                    style = LocalTypography.current.primary.regular
                )

                AutoResizedText(
                    containerModifier = Modifier
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
@Deprecated("Replaced by BasicText Composable")
fun AutoResizedText(
    containerModifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    text: String = "Test Haha",
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    borderStyle: TextStyle? = null,
    autoResizeStyle: AutoResizedStyleType = AutoResizedStyleType.CONSTRAIN,
    minFontSize: TextUnit = autoResizeStyle.min,
    maxFontSize: TextUnit = autoResizeStyle.max,
    stepSize: Float = autoResizeStyle.step,
    textCase: TextCase = TextCase.Unspecified,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Unspecified,
    behavior: AutoResizedBehavior = AutoResizedBehavior.DEFAULT,
    constrainWidth: Boolean = behavior != AutoResizedBehavior.MARQUEE,
    constrainHeight: Boolean = true,
    onFontSizeChanged: (TextUnit) -> Unit = {}
) {

    val rememberMaxFontSize by remember { mutableStateOf(maxFontSize) }

    Box(
        modifier = containerModifier,
        contentAlignment = Alignment.Center
    ) {

        val contentModifier = contentModifier
            .align(Alignment.Center)

        borderStyle?.let {

            AutoResizedText(
                modifier = contentModifier,
                text = text,
                color = it.color,
                style = it,
                textCase = textCase,
                textAlign = textAlign,
                minFontSize = minFontSize,
                maxFontSize = rememberMaxFontSize,
                stepSize = stepSize,
                behavior = behavior,
                constrainWidth = constrainWidth,
                constrainHeight = constrainHeight,
                onFontSizeChanged = onFontSizeChanged
            )

        }

        AutoResizedText(
            modifier = contentModifier,
            text = text,
            style = style,
            textCase = textCase,
            color = color,
            textAlign = textAlign,
            minFontSize = minFontSize,
            maxFontSize = rememberMaxFontSize,
            stepSize = stepSize,
            behavior = behavior,
            constrainWidth = constrainWidth,
            constrainHeight = constrainHeight,
            onFontSizeChanged = onFontSizeChanged
        )

    }

}

@Composable
@Deprecated("Replaced by BasicText Composable")
private fun AutoResizedText(
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
    behavior: AutoResizedBehavior = AutoResizedBehavior.DEFAULT,
    constrainWidth: Boolean = behavior != AutoResizedBehavior.MARQUEE,
    constrainHeight: Boolean = true,
    onFontSizeChanged: (TextUnit) -> Unit = {}
) {
    val minFontSize =
        if( minFontSize.value > maxFontSize.value) { maxFontSize } else { minFontSize }

    var currentTextSize by remember { mutableStateOf(maxFontSize) }
    var shouldDraw by remember { mutableStateOf(false) }
    var shouldMarquee by remember { mutableStateOf(false) }
    var maxLines by remember { mutableIntStateOf(1) }

    val convertedText = TextCase.convertCase(text, textCase)

    Row(
        modifier = modifier
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        val textModifier = if(shouldMarquee) {
            modifier.basicMarquee(Int.MAX_VALUE)
        } else {
            modifier
        }

        Text(
            modifier = textModifier
                .drawWithContent {
                    if (shouldDraw) drawContent()
                },
            text = convertedText,
            fontSize = currentTextSize,
            textAlign = textAlign,
            style = style,
            color = color,
            maxLines = maxLines,
            softWrap = false,
            onTextLayout = { result ->

                val requiresWidth = constrainWidth && result.didOverflowWidth
                val requiresHeight = constrainHeight && result.didOverflowHeight

                if (requiresWidth || requiresHeight ||
                    (result.didOverflowWidth && currentTextSize.value >= minFontSize.value)) {

                    val decrement = (currentTextSize.value * .25f).coerceAtLeast(stepSize)
                    var textSize = (currentTextSize.value - decrement).sp

                    if(currentTextSize.value < minFontSize.value) {
                        textSize = minFontSize
                    }

                    currentTextSize = textSize

                    onFontSizeChanged(currentTextSize)

                } else {

                    if(result.didOverflowWidth) {

                        if(currentTextSize.value >= minFontSize.value) {
                            currentTextSize = minFontSize

                            onFontSizeChanged(currentTextSize)
                        }

                        when(behavior) {
                            AutoResizedBehavior.DEFAULT -> {}
                            AutoResizedBehavior.MARQUEE -> shouldMarquee = true
                            AutoResizedBehavior.MULTI_LINE -> maxLines += 1
                        }
                    }

                    shouldDraw = true
                }
            }
        )
    }
}

@Deprecated("Replaced by BasicText Composable")
enum class AutoResizedStyleType(
    val min: TextUnit = 18.sp,
    val max: TextUnit = 50.sp,
    val step: Float = 1f,
) {
    CONSTRAIN(18.sp, 50.sp, 5f),
    SQUEEZE(10.sp, 200.sp, 5f),
}

@Deprecated("Replaced by BasicText Composable")
enum class AutoResizedBehavior {
    DEFAULT,
    MARQUEE,
    MULTI_LINE,
}
