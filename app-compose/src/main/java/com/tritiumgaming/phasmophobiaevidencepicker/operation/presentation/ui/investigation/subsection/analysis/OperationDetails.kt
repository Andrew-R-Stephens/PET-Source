package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.subsection.analysis

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.util.ColorUtils.getColorFromAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.viewmodel.InvestigationViewModel
import org.jetbrains.annotations.TestOnly

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
        OperationDetails()
    }
}

@Composable
fun OperationDetails() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(LocalPalette.current.surface.color)
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            PhaseModifierDetails()
            MapModifierDetails()
            DifficultyModifierDetails()
            ActiveGhostModifierDetails()
        }
    }
}

@Composable
fun PhaseModifierDetails(
    investigationViewModel: InvestigationViewModel =
        viewModel(factory = InvestigationViewModel.Factory)
) {

    val phaseState = investigationViewModel.currentPhase.collectAsStateWithLifecycle()
    val rememberPhase by remember { mutableStateOf(phaseState.value) }

    CategoryRow {
        TextCategoryTitle(text = "Phase:")
        TextSubTitle(text = rememberPhase.name)
    }

}

@Composable
fun MapModifierDetails(
    investigationViewModel: InvestigationViewModel =
        viewModel(factory = InvestigationViewModel.Factory)
) {

    val mapNameState = investigationViewModel.currentMapName.collectAsStateWithLifecycle()
    val rememberMapName by remember { mutableIntStateOf(mapNameState.value) }

    CategoryColumn {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextCategoryTitle(text = "Map:")
            TextSubTitle(text = stringResource(rememberMapName))
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
fun DifficultyModifierDetails(
    investigationViewModel: InvestigationViewModel =
        viewModel(factory = InvestigationViewModel.Factory)
) {

    val difficultyNameState = investigationViewModel.currentDifficultyName.collectAsStateWithLifecycle()
    val rememberDifficultyName by remember { mutableIntStateOf(difficultyNameState.value) }

    CategoryColumn {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextSubTitle(text = "Difficulty:")
            TextSubTitle(text = stringResource(rememberDifficultyName))
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
fun ActiveGhostModifierDetails(
    investigationViewModel: InvestigationViewModel =
        viewModel(factory = InvestigationViewModel.Factory)
) {
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
        color = LocalPalette.current.textFamily.body
    )
}

@Composable
fun TextSubTitle(text: String) {
    Text(
        text = text,
        color = LocalPalette.current.textFamily.body
    )
}