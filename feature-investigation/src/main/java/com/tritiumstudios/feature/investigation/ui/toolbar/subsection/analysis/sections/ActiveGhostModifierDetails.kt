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
import com.tritiumstudios.feature.investigation.app.mappers.ghost.toStringResource
import com.tritiumstudios.feature.investigation.ui.journal.lists.item.GhostScore
import com.tritiumstudios.feature.investigation.ui.toolbar.subsection.analysis.ExpandableCategoryColumn
import com.tritiumstudios.feature.investigation.ui.toolbar.subsection.analysis.ExpandableCategoryRow
import com.tritiumstudios.feature.investigation.ui.toolbar.subsection.analysis.SubRow
import com.tritiumstudios.feature.investigation.ui.toolbar.subsection.analysis.TextCategoryTitle
import com.tritiumstudios.feature.investigation.ui.toolbar.subsection.analysis.TextSubTitle
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ActiveGhostModifierDetails(
    state: StateFlow<List<GhostScore>>,
) {

    val collectState = state.collectAsStateWithLifecycle()
    val rememberState by remember { mutableStateOf(collectState.value) }

    val filteredGhosts = rememberState.filter { score ->
            score.score.value >= 0 &&
                    !score.forcefullyRejected.value }
    val rememberGhosts by remember { mutableStateOf(filteredGhosts) }

    ExpandableCategoryColumn(
        expanded = false,
        defaultContent = { modifier, expanded ->
            ExpandableCategoryRow(
                modifier = modifier,
                isExpanded = expanded
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextCategoryTitle(text = "Ghosts Active")
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            rememberGhosts.forEach { ghost ->
                TextCategoryTitle(
                    stringResource(ghost.ghostEvidence.ghost.name.toStringResource())
                )
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextSubTitle(text = "Hunt Sanity Threshold:")
                }
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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
