package com.tritiumgaming.feature.investigation.ui.common.analysis.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toStringResource
import com.tritiumgaming.feature.investigation.ui.common.analysis.ExpandableCategoryColumn
import com.tritiumgaming.feature.investigation.ui.common.analysis.ExpandableCategoryRow
import com.tritiumgaming.feature.investigation.ui.common.analysis.SubRow
import com.tritiumgaming.feature.investigation.ui.common.analysis.TextCategoryTitle
import com.tritiumgaming.feature.investigation.ui.common.analysis.TextSubTitle
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item.GhostScore
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources

@Composable
fun ActiveGhostModifierDetails(
    ghostOrder: List<GhostResources.GhostIdentifier>,
    ghostScores: List<GhostScore>
) {
    var rememberFilteredGhosts = ghostScores.filter { score ->
            score.score.value >= 0 &&
                    !score.generalRejection.value }

    LaunchedEffect(ghostOrder) {
        rememberFilteredGhosts = ghostScores.filter { score ->
            score.score.value >= 0 &&
                    !score.generalRejection.value }
    }

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
                        text = "${rememberFilteredGhosts.size}"
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
        ) {

            rememberFilteredGhosts.filter { ghost ->
                ghost.score.value >= 0
            }.forEach { ghost ->

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

            if(rememberFilteredGhosts.isEmpty()) {
                TextCategoryTitle(
                    text = "Empty"
                )
            }
        }

    }
}
