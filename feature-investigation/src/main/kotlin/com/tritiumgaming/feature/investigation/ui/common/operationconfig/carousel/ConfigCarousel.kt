package com.tritiumgaming.feature.investigation.ui.common.operationconfig.carousel

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R

@Composable
fun OperationConfigCarousel(
    modifier: Modifier = Modifier,
    label: Int,
    enabled: Boolean = true,
    leadingIcon: @Composable (Modifier) -> Unit = {},
    textStyle: TextStyle = TextStyle.Default,
    onColor: Color = Color.Unspecified,
    containerColor: Color = Color.Unspecified,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit
) {

    Surface(
        modifier = modifier,
        color = containerColor
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            leadingIcon(
                Modifier
                    .size(48.dp)
                    .padding(12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                if(enabled) {

                    Image(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(onClick = { onLeftClick() })
                            .padding(8.dp),
                        painter = painterResource(R.drawable.ic_arrow_60_left),
                        colorFilter = ColorFilter.tint(onColor),
                        contentDescription = ""
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(8.dp),
                        text = stringResource(label),
                        style = textStyle,
                        textAlign = TextAlign.Center,
                        color = onColor,
                        autoSize = TextAutoSize.StepBased(12.sp, 18.sp, 2.sp)
                    )

                }

                if(enabled) {
                    Image(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(onClick = { onRightClick() })
                            .padding(8.dp),
                        painter = painterResource(R.drawable.ic_arrow_60_right),
                        colorFilter = ColorFilter.tint(onColor),
                        contentDescription = ""
                    )
                }

            }

        }
    }
}
