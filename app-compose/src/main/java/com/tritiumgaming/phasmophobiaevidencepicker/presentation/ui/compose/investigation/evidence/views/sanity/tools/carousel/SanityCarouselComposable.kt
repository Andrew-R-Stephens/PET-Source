package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.investigation.evidence.views.sanity.tools.carousel

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
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
import androidx.compose.ui.unit.sp
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.AutoResizedStyleType
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.AutoResizedText
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.navigation.PETImageButton
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel

@Composable
@Preview
private fun SanityCarouselPreview(
) {
    SelectiveTheme(ClassicPalette, ClassicTypography) {
        SanityCarousel(labelRes = R.string.map_name_prison, onClickLeft = {}, onClickRight = {})
        DifficultyCarousel()
        MapCarousel()
    }

    SelectiveTheme(ClassicPalette, ClassicTypography) {
        SanityCarousel(labelRes = R.string.map_name_prison, onClickLeft = {}, onClickRight = {})
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

            Box(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                contentAlignment = Alignment.Center
            ) {

                BasicText(
                    text = label,
                    style = LocalTypography.current.secondary.regular.copy(
                        color = LocalPalette.current.textFamily.body,
                        textAlign = TextAlign.Center
                    ),
                    autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, maxFontSize = 18.sp, stepSize = 2.sp)
                )
            }

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