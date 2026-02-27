package com.tritiumgaming.feature.investigation.ui.tool.analysis.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBar
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarBundle
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiColors
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiState
import com.tritiumgaming.core.ui.widgets.progressbar.ProgressBarNotch
import com.tritiumgaming.feature.investigation.app.mappers.evidence.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toLong
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toSanityBounds
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toStringResource
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryColumn
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.SubRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextCategoryTitle
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextSubTitle
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState

@Composable
internal fun ActiveGhostModifierDetails(
    state: OperationDetailsUiState.GhostDetails,
) {

    val rememberGhostDetails = state.activeGhosts

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
                        text = "${rememberGhostDetails.size}"
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
        ) {

            rememberGhostDetails.forEach { ghostDetail ->

                val state = ghostDetail.state

                val sanityBounds = state.ghostEvidence.ghost.huntSanityBounds.toSanityBounds()

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
                                TextCategoryTitle(
                                    modifier = Modifier,
                                    text = stringResource(state.ghostEvidence.ghost.name.toStringResource())
                                )
                            }
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextSubTitle(
                            modifier = Modifier,
                            text = "Evidence"
                        )
                    }
                    Column(
                        modifier = Modifier.padding(start = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SubRow(
                            modifier = Modifier
                        ) {
                            state.ghostEvidence.normalEvidenceList.forEach { evidence ->
                                val isStrict = state.ghostEvidence.strictEvidenceList.find {
                                    it.id == evidence.id }
                                TextSubTitle(
                                    modifier = Modifier,
                                    text = stringResource(evidence.name.toStringResource()) +
                                            if(isStrict != null) " *" else ""
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextSubTitle(
                            modifier = Modifier,
                            text = "Hunt Sanity:"
                        )
                    }

                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(start = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val notches = mutableListOf<ProgressBarNotch>()
                        sanityBounds.normal?.let {
                            notches.add(ProgressBarNotch(
                                label = "${it.toLong()}",
                                xPos = 100-it.toLong()
                            ))
                        }
                        sanityBounds.empowered?.let {
                            notches.add(ProgressBarNotch(
                                label = "${it.toLong()}",
                                xPos = 100-it.toLong()
                            ))
                        }
                        sanityBounds.suppressed?.let {
                            notches.add(ProgressBarNotch(
                                label = "${it.toLong()}",
                                xPos = 100-it.toLong()
                            ))
                        }

                        NotchedProgressBar(
                            modifier = Modifier
                                .height(24.dp)
                                .fillMaxWidth(),
                            bundle = NotchedProgressBarBundle(
                                state = NotchedProgressBarUiState(
                                    max = 100L,
                                    origin = 0L,
                                    remaining = 0L,
                                    notches = notches,
                                    running = false
                                ),
                                colors = NotchedProgressBarUiColors(
                                    remaining = LocalPalette.current.surface,
                                    background = LocalPalette.current.surface,
                                    border = LocalPalette.current.onSurface,
                                    notch = LocalPalette.current.onSurface,
                                    label = LocalPalette.current.onSurface,
                                )
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextSubTitle(
                            modifier = Modifier,
                            text = "Hunt Cooldown: ${state.ghostEvidence.ghost.huntCooldown.toLong() / 1000} sec"
                        )
                    }
                }
            }

            if(rememberGhostDetails.isEmpty()) {
                TextCategoryTitle(
                    text = "Empty"
                )
            }
        }

    }
}
