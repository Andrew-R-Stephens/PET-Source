package com.tritiumgaming.phasmophobiaevidencepicker.views.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.sanity.carousels.DifficultyCarouselModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.sanity.carousels.MapCarouselModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.sanity.timer.PhaseTimerModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.dsvolatile.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
@Preview
fun ActiveModifierDetails(
    investigationViewModel: InvestigationViewModel? = null,
    onClick: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(getColorFromAttribute(LocalContext.current, R.attr.backgroundColor)))
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            investigationViewModel?.let { investigationViewModel ->
                investigationViewModel.timerModel?.let { PhaseModifierDetails(it) }
                investigationViewModel.mapCarouselModel?.let { MapModifierDetails(it) }
                investigationViewModel.difficultyCarouselModel?.let { DifficultyModifierDetails(it) }
                investigationViewModel.investigationModel?.ghostRepository?.let { ActiveGhostModifierDetails(it) }
            }
        }
    }
}

@Composable
fun PhaseModifierDetails(phase: PhaseTimerModel) {

    val phaseState = phase.currentPhase.collectAsState()

    CategoryRow {
        TextCategoryTitle(text = "Phase:")
        TextSubTitle(text = phaseState.value.name)
    }

}

@Composable
fun MapModifierDetails(mapCarouselModel: MapCarouselModel) {
    val mapName = MutableStateFlow("current-name").collectAsState()

    CategoryColumn {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextCategoryTitle(text = "Map:")
            TextSubTitle(text = mapName.value)
        }
        Column(
            modifier = Modifier.padding(PaddingValues(8.dp)),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SubRow {
                TextSubTitle(text = "Size:")
                TextSubTitle(text = "<size-name>")
            }
            SubRow {
                TextSubTitle(text = "Modifier:")
                TextSubTitle(text = "<size-modifier>")
            }
        }
    }

}

@Composable
fun DifficultyModifierDetails(difficultyCarouselModel: DifficultyCarouselModel) {
    val difficultyIndex = difficultyCarouselModel.currentIndex.collectAsState()

    CategoryColumn {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextSubTitle(text = "Difficulty:")
            TextSubTitle(
                text = stringResource(
                    id = difficultyCarouselModel.getNameAt(difficultyIndex.value)
                )
            )
        }
        Column(
            modifier = Modifier.padding(PaddingValues(8.dp)),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SubRow {
                TextSubTitle(text = "Setup Phase:")
                TextSubTitle(text = "<setup-modifier>")
            }
            SubRow {
                TextSubTitle(text = "Action Phase:")
                TextSubTitle(text = "<action-modifier>")
            }
        }
    }
}

@Composable
fun ActiveGhostModifierDetails(ghostListModel: GhostRepository) {
    CategoryColumn {
        TextCategoryTitle(text = "Ghosts")

        Column(
            modifier = Modifier.padding(PaddingValues(8.dp))
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextSubTitle(text = "Hunt Sanity Threshold:")
            }
            Column(
                modifier = Modifier.padding(PaddingValues(8.dp)),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SubRow {
                    TextSubTitle(text = "Earliest:")
                    TextSubTitle(text = "<setup-modifier>")
                }
                SubRow {
                    TextSubTitle(text = "Latest:")
                    TextSubTitle(text = "<action-modifier>")
                }
            }
        }
    }
}

@Composable
fun CategoryColumn(
    content: @Composable () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(
                Color(
                    getColorFromAttribute(
                        LocalContext.current,
                        R.attr.backgroundColorOnBackground
                    )
                ),
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        content()
    }
}

@Composable
fun CategoryRow(
    content: @Composable () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(
                Color(
                    getColorFromAttribute(
                        LocalContext.current,
                        R.attr.backgroundColorOnBackground
                    )
                ),
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        content()
    }
}

@Composable
fun SubRow(
    content: @Composable () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.wrapContentWidth()
    ) {
        content()
    }
}

@Composable
fun TextCategoryTitle(text: String) {
    Text(
        text = text,
        color = Color(getColorFromAttribute(LocalContext.current, R.attr.textColorBody))
    )
}

@Composable
fun TextSubTitle(text: String) {
    Text(text = text, color = Color(getColorFromAttribute(LocalContext.current, R.attr.textColorBody)))
}