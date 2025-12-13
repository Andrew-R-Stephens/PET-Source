package com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toStringResource
import com.tritiumgaming.feature.investigation.ui.journal.lists.item.GhostScore
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.ExpandableCategoryColumn
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.ExpandableCategoryRow
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.SubRow
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.TextCategoryTitle
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.TextSubTitle
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

    var rememberCount by remember { mutableIntStateOf(0) }

    ExpandableCategoryColumn(
        expanded = false,
        defaultContent = { modifier, expanded ->
            ExpandableCategoryRow(
                modifier = modifier,
                isExpanded = expanded
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextCategoryTitle(text = "Ghosts Active: ")
                    TextSubTitle(
                        modifier = Modifier.padding(start= 8.dp),
                        text = "$rememberCount"
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
        ) {

            rememberGhosts.filter { ghost ->
                ghost.score.value >= 0
            }.forEach { ghost ->

                val collectScore = ghost.score.collectAsStateWithLifecycle()

                TextCategoryTitle(
                    modifier = Modifier,
                    text = stringResource(ghost.ghostEvidence.ghost.name.toStringResource())
                )

                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextSubTitle(
                        modifier = Modifier,
                        text = "Hunt Sanity Threshold:"
                    )
                }
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SubRow(
                        modifier = Modifier
                    ) {
                        TextSubTitle(
                            modifier = Modifier,
                            text = "Earliest:")
                        TextSubTitle(
                            modifier = Modifier,
                            text = "<setup-modifier>")
                    }
                    SubRow(
                        modifier = Modifier
                    ) {
                        TextSubTitle(
                            modifier = Modifier,
                            text = "Latest:"
                        )
                        TextSubTitle(
                            modifier = Modifier,
                            text = "<action-modifier>"
                        )
                    }
                }
            }

            if(rememberCount == 0) {
                TextCategoryTitle(
                    text = "Empty"
                )
            }
        }

    }
}
