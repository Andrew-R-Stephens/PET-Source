package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.subsection.sanity.tools.operationconfig

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.PETImageButton
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.viewmodel.InvestigationViewModel

@Composable
@Preview
private fun SanityCarouselPreview(
) {
    SelectiveTheme(ClassicPalette, ClassicTypography) {
        SanityConfig(labelRes = R.string.map_name_prison, onClickLeft = {}, onClickRight = {})
        DifficultyConfig()
        MapConfig()
    }

    SelectiveTheme(ClassicPalette, ClassicTypography) {
        SanityConfig(labelRes = R.string.map_name_prison, onClickLeft = {}, onClickRight = {})
        DifficultyConfig()
        MapConfig()
    }
}

@Composable
private fun SanityConfig(
    modifier: Modifier = Modifier,
    @DrawableRes primaryIcon: Int = R.drawable.ic_selector_inc_unsel,
    @StringRes labelRes: Int = R.string.difficulty_title_default,
    label: String = stringResource(labelRes),
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit
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
                    .clickable(onClick = { onClickRight() }),
                type = PETImageButtonType.FORWARD
            )

        }
    }

}

@Composable
fun DifficultyConfig(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel =
        viewModel(factory = InvestigationViewModel.Factory)
) {
    SanityConfig(
        modifier = modifier,
        primaryIcon = R.drawable.ic_puzzle,
        onClickLeft = {
            investigationViewModel.decrementDifficultyIndex()
        }, onClickRight = {
            investigationViewModel.incrementDifficultyIndex()
        }
    )
}

@Composable
fun MapConfig(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel =
        viewModel(factory = InvestigationViewModel.Factory)
) {
    SanityConfig(
        modifier = modifier,
        primaryIcon = R.drawable.icon_nav_mapmenu2,
        onClickLeft = {
            investigationViewModel.decrementMapIndex()
        }, onClickRight = {
            investigationViewModel.incrementMapIndex()
        }
    )
}