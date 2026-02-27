package com.tritiumgaming.feature.investigation.ui.tool.analysis.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryColumn
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.SubRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextSubTitle

@Composable
internal fun DifficultyModifierDetails(
    state: OperationDetailsUiState.DifficultyDetails
) {

    val difficultyName = state.name.toStringResource()
    val difficultyTime = state.setupTime
    val difficultyModifier = state.modifier
    val difficultyResponseType = state.responseType

    ExpandableCategoryColumn(
        expanded = false,
        containerColor = LocalPalette.current.surfaceContainer,
        defaultContent = { modifier, expanded ->
            ExpandableCategoryRow(
                modifier = modifier,
                isExpanded = expanded
            ) {
                Row(
                    modifier = modifier,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Difficulty:")
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = stringResource(difficultyName))
                }
            }
        }
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SubRow {
                TextSubTitle(
                    color = LocalPalette.current.onSurface,
                    text = "Sanity Drain Modifier:")
                TextSubTitle(
                    color = LocalPalette.current.onSurface,
                    text = "$difficultyModifier")
            }
            SubRow {
                TextSubTitle(
                    color = LocalPalette.current.onSurface,
                    text = "Setup Time:")
                TextSubTitle(
                    color = LocalPalette.current.onSurface,
                    text = "${difficultyTime / 60000} minutes")
            }
            SubRow {
                TextSubTitle(
                    color = LocalPalette.current.onSurface,
                    text = "Ghost Response Type:")
                TextSubTitle(
                    color = LocalPalette.current.onSurface,
                    text = stringResource(difficultyResponseType.toStringResource()))
            }
        }
    }
}
