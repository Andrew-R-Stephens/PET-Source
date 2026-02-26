package com.tritiumgaming.feature.settings.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.widgets.other.PETImageButton
import com.tritiumgaming.core.ui.widgets.other.PETImageButtonType
import com.tritiumgaming.feature.settings.ui.components.CarouselUiActions

@Composable
fun CarouselComposable(
    modifier: Modifier = Modifier,
    @StringRes title: Int = 0,
    state: Any,
    label: String = "",
    iconComponent: @Composable (Modifier) -> Unit = {},
    containerColor: Color = Color.White,
    primaryTextColor: Color = Color.White,
    secondaryTextColor: Color = Color.White,
    actions: CarouselUiActions = CarouselUiActions()
) {
    Card(
        modifier = modifier
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {

        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(4.dp)
        ) {

            iconComponent(
                Modifier
                    .size(48.dp)
                    .padding(4.dp)
                    .align(Alignment.CenterVertically)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally)
                        .padding(4.dp),
                    text = if(title != 0) stringResource(title) else "",
                    color = primaryTextColor,
                    style = LocalTypography.current.primary.regular,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    fontSize = 16.sp
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    PETImageButton(
                        modifier = Modifier
                            .clickable(onClick = { actions.onPrevious() }),
                        type = PETImageButtonType.BACK,
                        tint = LocalPalette.current.onSurface
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                            .padding(4.dp),
                        text = if(state != 0) { label } else "",
                        color = secondaryTextColor,
                        style = LocalTypography.current.primary.bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        fontSize = 16.sp
                    )

                    PETImageButton(
                        modifier = Modifier
                            .clickable(onClick = { actions.onNext() }),
                        type = PETImageButtonType.FORWARD,
                        tint = LocalPalette.current.onSurface
                    )

                }

            }
        }


    }

}
