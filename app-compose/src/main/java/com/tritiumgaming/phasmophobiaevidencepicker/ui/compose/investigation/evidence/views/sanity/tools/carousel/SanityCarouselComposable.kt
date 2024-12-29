package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.evidence.views.sanity.tools.carousel

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Non_Colorblind
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.Classic
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.AutoResizedStyleType
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.AutoResizedText
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.navigation.PETImageButton
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.navigation.PETImageButtonType

@Composable
@Preview
private fun SanityCarouselPreview(
) {
    SelectiveTheme(Non_Colorblind, Classic) {
        SanityCarousel(onClickLeft = {}, onClickRight = {})
        DifficultyCarousel()
        MapCarousel()
    }
}

@Composable
fun SanityCarousel(
    @DrawableRes primaryIcon: Int = R.drawable.ic_selector_inc_unsel,
    @StringRes labelRes: Int = R.string.investigation_timer_difficulty_default,
    label: String = stringResource(labelRes),
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit
) {

    Row(
        modifier = Modifier
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
                    .clickable(onClick = {
                        onClickLeft()
                    }),
                type = PETImageButtonType.BACK,
            )
            AutoResizedText(
                modifier = Modifier
                    .heightIn(0.dp, 48.dp)
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                style = LocalTypography.current.secondary.regular,
                autoResizeStyle = AutoResizedStyleType.SQUEEZE,
                text = label,
                color = LocalPalette.current.textFamily.body
            )
            PETImageButton(
                modifier = Modifier
                    .size(48.dp)
                    .clickable(onClick = {
                        onClickRight()
                    }),
                type = PETImageButtonType.FORWARD
            )
        }
    }

}

@Composable
fun DifficultyCarousel(
    investigationViewModel: InvestigationViewModel? = null
) {
    val index = remember { investigationViewModel?.difficultyCarouselModel?.currentIndex }

    SanityCarousel(
        onClickLeft = {
            investigationViewModel?.difficultyCarouselModel?.decrementIndex()
        }, onClickRight = {
            investigationViewModel?.difficultyCarouselModel?.incrementIndex()
        }
    )
}

@Composable
fun MapCarousel(
    investigationViewModel: InvestigationViewModel? = null
) {
    val index = remember { investigationViewModel?.mapCarouselModel?.currentIndex }

    SanityCarousel(
        onClickLeft = {
            investigationViewModel?.mapCarouselModel?.decrementIndex()
        }, onClickRight = {
            investigationViewModel?.mapCarouselModel?.incrementIndex()
        }
    )
}