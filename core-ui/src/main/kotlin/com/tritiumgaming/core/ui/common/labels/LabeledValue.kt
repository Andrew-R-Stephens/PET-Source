package com.tritiumgaming.core.ui.common.labels

import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.ui.theme.type.LocalTypography

@Composable
fun LabeledValue(
    modifier: Modifier = Modifier,
    title: String = "",
    value: String = "",
    containerColor: Color,
    textColor: Color
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        color = containerColor,
        shape = RoundedCornerShape(8.dp),
    ) {
        DynamicContentRow(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            startComponent = {
                Text(
                    text = title,
                    style = LocalTypography.current.quaternary.bold,
                    color = textColor,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(end = 4.dp)
                )
            },
            endComponent = {
                Text(
                    text = value,
                    style = LocalTypography.current.quaternary.bold,
                    color = textColor,
                    fontSize = 18.sp,
                    maxLines = 1,
                    textAlign = TextAlign.End,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier
                        .basicMarquee(
                            animationMode = MarqueeAnimationMode.Immediately,
                            iterations = Integer.MAX_VALUE,
                            initialDelayMillis = 3000,
                            repeatDelayMillis = 3000
                        )
                        .padding(start = 4.dp)
                )
            }
        )
    }
}
