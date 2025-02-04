package com.tritiumgaming.phasmophobiaevidencepicker.ui.mainmenus.appsettings

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.views.composables.PETImageButton
import com.tritiumgaming.phasmophobiaevidencepicker.views.composables.PETImageButtonType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun CarouselComposable(
    @StringRes title: Int = 0,
    state: StateFlow<Int> = MutableStateFlow(0),
    painterResource: Painter = painterResource(R.drawable.ic_font_family),
    colorFilter: ColorFilter? = null,
    leftOnClick: () -> Unit = {},
    rightOnClick: () -> Unit = {}
) {

    val rememberState = state.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = LocalPalette.current.surface.onColor
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
                colorFilter = colorFilter,
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
                    color = LocalPalette.current.textFamily.body,
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
                            .clickable(enabled = true, onClick = { leftOnClick() }),
                        type = PETImageButtonType.BACK
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                            .padding(4.dp),
                        text = if(rememberState.value != 0) { stringResource(rememberState.value) } else "",
                        color = LocalPalette.current.textFamily.emphasis,
                        style = LocalTypography.current.primary.regular,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        fontSize = 16.sp
                    )

                    PETImageButton(
                        modifier = Modifier
                            .clickable(enabled = true, onClick = { rightOnClick() }),
                        type = PETImageButtonType.FORWARD)

                }

            }
        }


    }

}
