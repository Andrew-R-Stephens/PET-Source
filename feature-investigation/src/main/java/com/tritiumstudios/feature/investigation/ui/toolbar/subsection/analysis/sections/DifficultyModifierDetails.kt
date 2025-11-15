package com.tritiumstudios.feature.investigation.ui.toolbar.subsection.analysis.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumstudios.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumstudios.feature.investigation.ui.toolbar.subsection.analysis.ExpandableCategoryColumn
import com.tritiumstudios.feature.investigation.ui.toolbar.subsection.analysis.ExpandableCategoryRow
import com.tritiumstudios.feature.investigation.ui.toolbar.subsection.analysis.SubRow
import com.tritiumstudios.feature.investigation.ui.toolbar.subsection.analysis.TextSubTitle
import com.tritiumstudios.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.operationconfig.difficulty.DifficultyUiState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DifficultyModifierDetails(
    state: StateFlow<DifficultyUiState>,
) {

    val collectState = state.collectAsStateWithLifecycle()
    val rememberState by remember { mutableStateOf(collectState.value) }

    val difficultyName = rememberState.name.toStringResource()
    val difficultyTime = rememberState.time
    val difficultyModifier = rememberState.modifier
    val difficultyResponseType = rememberState.responseType

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
