package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.startscreen.reviewtracker

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypographiesList
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import org.jetbrains.annotations.TestOnly

@Preview
@Composable
@TestOnly
private fun ReviewPopupComposablePreview() {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(count = LocalTypographiesList.size) { index ->

            SelectiveTheme(
                palette = ClassicPalette,
                typography = LocalTypographiesList[index].second
            ) {
                ReviewPopupComposable()
            }
        }

    }
}

@Preview
@Composable
@TestOnly
private fun OptionButtonPreview() {

        SelectiveTheme(
            palette = ClassicPalette,
            typography = ClassicTypography
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                OptionButton(R.string.review_accept) {

                }
                OptionButton(R.string.review_decline) {

                }

            }
        }
}

@Composable
fun ReviewPopupComposable() {

    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .wrapContentHeight(),
        shape = RoundedCornerShape(corner = CornerSize(30.dp)),
        colors = CardDefaults.cardColors(
            containerColor = LocalPalette.current.surface.color
        ),
        border = BorderStroke(5.dp, LocalPalette.current.windowStrokeColor)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            modifier = Modifier
                .padding(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                contentAlignment = Alignment.Center
            ) {

                BasicText(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(R.string.review_requesttitle),
                    style = LocalTypography.current.primary.regular.copy(
                        color = LocalPalette.current.textFamily.secondary,
                        textAlign = TextAlign.Center
                    ),
                    autoSize = TextAutoSize.StepBased(minFontSize = 12.sp, maxFontSize = 48.sp, stepSize = 5.sp)
                )

            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                items(count = 5) { index ->
                    Image(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.FillHeight,
                        painter = painterResource(android.R.drawable.btn_star_big_off),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(LocalPalette.current.textFamily.emphasis)
                    )
                }

            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                OptionButton(R.string.review_accept) {

                }
                OptionButton(R.string.review_decline) {

                }

            }
        }
    }
}

@Composable
private fun RowScope.OptionButton(
    @StringRes text: Int = R.string.review_accept,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .weight(1f),
        contentPadding = PaddingValues(4.dp),
        onClick = { onClick() },
        shape = RectangleShape
    ) {
        OptionContent(text)
    }
}

@Composable
private fun OptionContent(
    @StringRes text: Int = R.string.review_accept
) {
    Box(
        Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(R.drawable.icon_square_alt),
            colorFilter = ColorFilter.tint(LocalPalette.current.textFamily.body),
            contentDescription = stringResource(text),
            contentScale = ContentScale.FillBounds
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {

            BasicText(
                text = stringResource(text),
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.textFamily.body,
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(minFontSize = 12.sp, maxFontSize = 48.sp, stepSize = 5.sp)
            )

        }

    }
}