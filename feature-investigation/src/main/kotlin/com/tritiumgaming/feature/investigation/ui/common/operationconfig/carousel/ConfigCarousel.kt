package com.tritiumgaming.feature.investigation.ui.common.operationconfig.carousel

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.widgets.other.PETImageButton
import com.tritiumgaming.core.ui.widgets.other.PETImageButtonType

@Composable
@Preview
private fun OperationCarouselPreview() {
    SelectiveTheme(ClassicPalette, ClassicTypography) {
        OperationConfigCarousel(
            state = ConfigCarouselUiState(
                label = R.string.map_name_short_prison
            ),
            actions = CarouselUiActions()
        )
    }

    SelectiveTheme(ClassicPalette, ClassicTypography) {
        OperationConfigCarousel(
            state = ConfigCarouselUiState(
                label = R.string.map_name_short_prison
            ),
            actions = CarouselUiActions()
        )
    }
}

@Composable
fun OperationConfigCarousel(
    modifier: Modifier = Modifier,
    state: ConfigCarouselUiState,
    icon: @Composable (Modifier) -> Unit = {},
    textStyle: TextStyle = TextStyle.Default,
    onColor: Color = Color.Unspecified,
    containerColor: Color = Color.Unspecified,
    actions: CarouselUiActions
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

            icon(
                Modifier
                    .size(48.dp)
                    .padding(12.dp)
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
                        .clickable(onClick = { actions.onLeftClick() }),
                    type = PETImageButtonType.BACK,
                    tint = onColor
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
                        text = stringResource(state.label),
                        style = textStyle,
                        textAlign = TextAlign.Center,
                        color = onColor,
                        fontSize = 18.sp
                    )

                }

                PETImageButton(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(onClick = { actions.onRightClick() }),
                    type = PETImageButtonType.FORWARD,
                    tint = onColor
                )

            }
        }
    }
}
