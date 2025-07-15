package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.subsection.analysis

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.util.ColorUtils.getColorFromAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toStringResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.InvestigationViewModel
import org.jetbrains.annotations.TestOnly
import java.util.Locale

@Composable
@Preview
@TestOnly
private fun OperationDetailsPreview(
    investigationViewModel: InvestigationViewModel =
        viewModel(factory = InvestigationViewModel.Factory),
    onClick: () -> Unit = {}
) {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        OperationDetails(investigationViewModel = investigationViewModel)
    }
}

@Composable
fun OperationDetails(
    investigationViewModel: InvestigationViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(LocalPalette.current.surface.color)
            .verticalScroll(rememberScrollState())
    ) {
        PhaseModifierDetails(investigationViewModel = investigationViewModel)
        MapModifierDetails(investigationViewModel = investigationViewModel)
        DifficultyModifierDetails(investigationViewModel = investigationViewModel)
        ActiveGhostModifierDetails(investigationViewModel = investigationViewModel)
    }
}

@Composable
fun PhaseModifierDetails(
    investigationViewModel: InvestigationViewModel
) {

    val phaseState = investigationViewModel.phaseUiState.collectAsStateWithLifecycle()
    val rememberPhase by remember { mutableStateOf(phaseState.value) }

    CategoryRow {
        TextCategoryTitle(text = "Phase:")
        TextSubTitle(text = rememberPhase.currentPhase.name)
    }

}

@Composable
fun MapModifierDetails(
    investigationViewModel: InvestigationViewModel
) {

    val mapConfigUiState = investigationViewModel.mapUiState.collectAsStateWithLifecycle()
    val mapName = mapConfigUiState.value.name.toStringResource()
    val mapSize = mapConfigUiState.value.size.toStringResource()
    val mapModifiers = mapConfigUiState.value.modifier

    CategoryColumn {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextCategoryTitle(text = "Map:")
            TextSubTitle(text = stringResource(mapName))
        }
        Column(
            modifier = Modifier.padding(PaddingValues(8.dp)),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SubRow {
                TextSubTitle(text = "Size:")
                TextSubTitle(text = stringResource(mapSize))
            }
            SubRow {
                TextSubTitle(text = "Setup Modifier:")
                TextSubTitle(
                    text = String.format(Locale.getDefault(), "%.2f", mapModifiers.setupModifier)
                )
            }
            SubRow {
                TextSubTitle(text = "Action Modifier:")
                TextSubTitle(
                    text = String.format(Locale.getDefault(), "%.2f", mapModifiers.normalModifier)
                )
            }
        }
    }

}

@Composable
fun DifficultyModifierDetails(
    investigationViewModel: InvestigationViewModel
) {

    val difficultyUiState = investigationViewModel.difficultyUiState.collectAsStateWithLifecycle()
    val difficultyName = difficultyUiState.value.name.toStringResource()
    val difficultyTime = difficultyUiState.value.time
    val difficultyModifier = difficultyUiState.value.modifier
    val difficultyResponseType = difficultyUiState.value.responseType

    CategoryColumn {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextSubTitle(text = "Difficulty:")
            TextSubTitle(text = stringResource(difficultyName))
        }
        Column(
            modifier = Modifier.padding(PaddingValues(8.dp)),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SubRow {
                TextSubTitle(text = "Sanity Drain Modifier:")
                TextSubTitle(text = "$difficultyModifier")
            }
            SubRow {
                TextSubTitle(text = "Setup Time:")
                TextSubTitle(text = "${difficultyTime / 60000} minutes")
            }
            SubRow {
                TextSubTitle(text = "Ghost Response Type:")
                TextSubTitle(text = stringResource(difficultyResponseType.toStringResource()))
            }
        }
    }
}

@Composable
fun ActiveGhostModifierDetails(
    investigationViewModel: InvestigationViewModel
) {
    val ghosts = investigationViewModel.ghostScores.collectAsStateWithLifecycle()

    CategoryColumn {

        TextCategoryTitle(text = "Ghosts")

        Column(
            modifier = Modifier.padding(PaddingValues(8.dp))
        ) {
            ghosts.value.filter { score ->
                    score.score.value >= 0 &&
                            !score.forcefullyRejected.value
            }.forEach { ghost ->
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
}

@Composable
private fun CategoryColumn(
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
private fun CategoryRow(
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
private fun SubRow(
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
private fun TextCategoryTitle(text: String) {
    Text(
        text = text,
        color = LocalPalette.current.textFamily.body
    )
}

@Composable
private fun TextSubTitle(text: String) {
    Text(
        text = text,
        color = LocalPalette.current.textFamily.body
    )
}