package com.tritiumgaming.feature.investigation.ui.common.analysis.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.ui.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.common.analysis.ExpandableCategoryColumn
import com.tritiumgaming.feature.investigation.ui.common.analysis.ExpandableCategoryRow
import com.tritiumgaming.feature.investigation.ui.common.analysis.SubRow
import com.tritiumgaming.feature.investigation.ui.common.analysis.TextSubTitle

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
        defaultContent = { modifier, expanded ->
            ExpandableCategoryRow(
                modifier = modifier,
                isExpanded = expanded
            ) {
                Row(
                    modifier = modifier,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextSubTitle(text = "Difficulty:")
                    TextSubTitle(text = stringResource(difficultyName))
                }
            }
        }
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
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
