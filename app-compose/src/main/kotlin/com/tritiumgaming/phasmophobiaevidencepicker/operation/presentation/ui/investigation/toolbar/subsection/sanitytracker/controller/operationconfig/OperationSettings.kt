package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.sanitytracker.controller.operationconfig

import android.util.Log
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
import androidx.compose.material3.Text
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.PETImageButton
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources.MapTitleLength
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toStringResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.InvestigationViewModel

@Composable
@Preview
private fun OperationCarouselPreview() {
    SelectiveTheme(ClassicPalette, ClassicTypography) {
        OperationConfigCarousel(
            label = stringResource(R.string.map_name_short_prison),
            onClickLeft = {},
            onClickRight = {}
        )
        DifficultyConfigCarousel(
            investigationViewModel = viewModel(factory = InvestigationViewModel.Factory))
        MapConfigCarousel(
            investigationViewModel = viewModel(factory = InvestigationViewModel.Factory))
    }

    SelectiveTheme(ClassicPalette, ClassicTypography) {
        OperationConfigCarousel(
            label = stringResource(R.string.map_name_short_prison),
            onClickLeft = {},
            onClickRight = {}
        )
        DifficultyConfigCarousel(
            investigationViewModel = viewModel(factory = InvestigationViewModel.Factory))
        MapConfigCarousel(
            investigationViewModel = viewModel(factory = InvestigationViewModel.Factory))
    }
}

@Composable
private fun OperationConfigCarousel(
    modifier: Modifier = Modifier,
    @DrawableRes primaryIcon: Int = R.drawable.ic_selector_inc_unsel,
    label: String = stringResource(R.string.difficulty_title_default),
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit
) {

    Log.d("SanityConfig", "Label: $label")

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
                    style = LocalTypography.current.secondary.regular,
                    textAlign = TextAlign.Center,
                    color = LocalPalette.current.textFamily.body,
                    fontSize = 18.sp
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
fun DifficultyConfigCarousel(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel
) {

    val difficultyUiState = investigationViewModel.difficultyUiState.collectAsStateWithLifecycle()
    val difficultyName = difficultyUiState.value.name.toStringResource()

    OperationConfigCarousel(
        modifier = modifier,
        primaryIcon = R.drawable.ic_puzzle,
        label = stringResource(difficultyName),
        onClickLeft = {
            investigationViewModel.decrementDifficultyIndex()
        },
        onClickRight = {
            investigationViewModel.incrementDifficultyIndex()
        }
    )
}

@Composable
fun MapConfigCarousel(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel
) {

    val mapUiState = investigationViewModel.mapUiState.collectAsStateWithLifecycle()
    val mapName = mapUiState.value.name.toStringResource(MapTitleLength.ABBREVIATED)

    OperationConfigCarousel(
        modifier = modifier,
        primaryIcon = R.drawable.icon_nav_mapmenu2,
        label = stringResource(mapName),
        onClickLeft = {
            investigationViewModel.decrementMapIndex()
        },
        onClickRight = {
            investigationViewModel.incrementMapIndex()
        }
    )
}