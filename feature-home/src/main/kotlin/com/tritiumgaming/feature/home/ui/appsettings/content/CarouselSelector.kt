package com.tritiumgaming.feature.home.ui.appsettings.content

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.common.other.PETImageButton
import com.tritiumgaming.core.ui.common.other.PETImageButtonType
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun CarouselComposable(
    @StringRes title: Int = 0,
    state: StateFlow<Any?> = MutableStateFlow(null),
    label: String = "",
    painterResource: Painter = painterResource(R.drawable.ic_font_family),
    containerColor: Color = Color.White,
    imageTint: Color? = null,
    primaryTextColor: Color = Color.White,
    secondaryTextColor: Color = Color.White,
    leftOnClick: () -> Unit = {},
    rightOnClick: () -> Unit = {}
) {

    val rememberState = state.collectAsStateWithLifecycle()

    Card(
        modifier = Modifier
            .fillMaxWidth()
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

            Image(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp)
                    .align(Alignment.CenterVertically),
                alignment = Alignment.Center,
                painter = painterResource,
                colorFilter = imageTint?.let {ColorFilter.tint(it)},
                contentDescription = "",
                contentScale = ContentScale.Inside
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
                            .clickable(onClick = { leftOnClick() }),
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
                        text = if(rememberState.value != 0) { label } else "",
                        color = secondaryTextColor,
                        style = LocalTypography.current.primary.bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        fontSize = 16.sp
                    )

                    PETImageButton(
                        modifier = Modifier
                            .clickable(onClick = { rightOnClick() }),
                        type = PETImageButtonType.FORWARD,
                        tint = LocalPalette.current.onSurface
                    )

                }

            }
        }


    }

}
