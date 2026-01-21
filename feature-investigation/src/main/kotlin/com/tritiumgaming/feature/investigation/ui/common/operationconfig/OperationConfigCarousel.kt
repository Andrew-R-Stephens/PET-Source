package com.tritiumgaming.feature.investigation.ui.common.operationconfig

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.common.other.PETImageButton
import com.tritiumgaming.core.ui.common.other.PETImageButtonType
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography

@Composable
@Preview
private fun OperationCarouselPreview() {
    SelectiveTheme(ClassicPalette, ClassicTypography) {
        OperationConfigCarousel(
            label = stringResource(R.string.map_name_short_prison),
            onClickLeft = {},
            onClickRight = {}
        )
    }

    SelectiveTheme(ClassicPalette, ClassicTypography) {
        OperationConfigCarousel(
            label = stringResource(R.string.map_name_short_prison),
            onClickLeft = {},
            onClickRight = {}
        )
    }
}

@Composable
fun OperationConfigCarousel(
    modifier: Modifier = Modifier,
    @DrawableRes primaryIcon: Int = R.drawable.ic_selector_inc_unsel,
    label: String = stringResource(R.string.difficulty_title_default),
    textStyle: TextStyle = TextStyle.Default,
    color: Color = Color.Unspecified,
    containerColor: Color = Color.Unspecified,
    iconColorFilter: ColorFilter = ColorFilter.tint(Color.Unspecified),
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit
) {

    Surface(
        modifier = modifier,
        color = containerColor
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Image(
                modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp),
                contentScale = ContentScale.Inside,
                alignment = Alignment.Center,
                painter = painterResource(primaryIcon),
                colorFilter = iconColorFilter,
                contentDescription = ""
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = SpaceBetween
            ) {

                PETImageButton(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(onClick = { onClickLeft() }),
                    type = PETImageButtonType.BACK,
                    tint = color
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        text = label,
                        style = textStyle,
                        textAlign = TextAlign.Center,
                        color = color,
                        fontSize = 18.sp
                    )

                }

                PETImageButton(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(onClick = { onClickRight() }),
                    type = PETImageButtonType.FORWARD,
                    tint = color
                )

            }
        }
    }
}
