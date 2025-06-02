package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.sp
import com.tritiumgaming.phasmophobiaevidencepicker.util.TextCase

@Composable
@Preview
fun AutoResizedText(
    modifier: Modifier = Modifier,
    text: String = "Test Haha",
    textCase: TextCase = TextCase.Unspecified,
    maxFontSize: TextUnit = 12.sp,
    color: Color = Color.White,
    textAlign: TextAlign = TextAlign.Unspecified,
    onUpdateMaxFontSize: () -> Unit = {  }
) {
    var resizedTextSize by remember { mutableStateOf(maxFontSize) }
    var shouldDraw by remember { mutableStateOf(false) }

    val convertedText = TextCase.convertCase(text, textCase)

    Text(
        text = convertedText,
        textAlign = textAlign,
        color = color,
        modifier = modifier
            .fillMaxSize()
            .drawWithContent {
                if (shouldDraw) drawContent()
            },
        softWrap = false,
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                if (maxFontSize.isUnspecified) {
                    resizedTextSize = 12.sp
                }
                resizedTextSize = (resizedTextSize.value - 1).sp
                onUpdateMaxFontSize()
            } else {
                shouldDraw = true
            }
        },
        fontSize = resizedTextSize
    )

}