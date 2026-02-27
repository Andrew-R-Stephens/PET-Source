package com.tritiumgaming.feature.investigation.ui.tool.analysis.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBar
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarBundle
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiColors
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiState
import com.tritiumgaming.core.ui.widgets.progressbar.ProgressBarNotch
import com.tritiumgaming.feature.investigation.app.mappers.evidence.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toHasLosMultiplierBoolean
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toMaximumAsInt
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toMinimumAsInt
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toSanityBounds
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toStringResource
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryColumn
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.SubRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextCategoryTitle
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextSubTitle
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import kotlin.math.max

@Composable
internal fun ActiveGhostModifierDetails(
    state: OperationDetailsUiState.GhostDetails,
) {

    val rememberGhostDetails = state.activeGhosts

    ExpandableCategoryColumn(
        expanded = false,
        containerColor = LocalPalette.current.surfaceContainer,
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
                        color = LocalPalette.current.onSurface,
                        text = "Ghosts Active: ")
                    TextSubTitle(
                        modifier = Modifier.padding(start= 8.dp),
                        color = LocalPalette.current.onSurface,
                        text = "${rememberGhostDetails.size}"
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            rememberGhostDetails.forEach { ghostDetail ->

                val state = ghostDetail.state

                val sanityBounds = state.ghostEvidence.ghost.huntSanityBounds.toSanityBounds()

                ExpandableCategoryColumn(
                    expanded = false,
                    containerColor = LocalPalette.current.surfaceContainerHigh,
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
                                    color = LocalPalette.current.onSurface,
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
                            color = LocalPalette.current.onSurface,
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
                                    color = LocalPalette.current.onSurface,
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
                            color = LocalPalette.current.onSurface,
                            text = "Hunt Sanity"
                        )
                    }

                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(start = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        var highest = 0L

                        val notches = mutableListOf<ProgressBarNotch>()
                        sanityBounds.suppressed?.let {
                            notches.add(ProgressBarNotch(
                                label = "${it.toLong()}",
                                xPos = 100-it.toLong()
                            ))
                            highest = max(highest, it.toLong())
                        }
                        sanityBounds.normal?.let {
                            notches.add(ProgressBarNotch(
                                label = "${it.toLong()}",
                                xPos = 100-it.toLong()
                            ))
                            highest = max(highest, it.toLong())
                        }
                        sanityBounds.empowered?.let {
                            notches.add(ProgressBarNotch(
                                label = "${it.toLong()}",
                                xPos = 100-it.toLong()
                            ))
                            highest = max(highest, it.toLong())
                        }

                        NotchedProgressBar(
                            modifier = Modifier
                                .height(24.dp)
                                .fillMaxWidth(),
                            bundle = NotchedProgressBarBundle(
                                state = NotchedProgressBarUiState(
                                    max = 100L,
                                    origin = 0L,
                                    remaining = highest,
                                    notches = notches,
                                    running = false
                                ),
                                colors = NotchedProgressBarUiColors(
                                    remaining = LocalPalette.current.primary,
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
                            color = LocalPalette.current.onSurface,
                            text = "Movement Speed"
                        )
                    }

                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(start = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val minSpeed = state.ghostEvidence.ghost.speed.toMinimumAsInt().toFloat()
                        var maxSpeed = state.ghostEvidence.ghost.speed.toMaximumAsInt().toFloat()
                        val losMultiplier = state.ghostEvidence.ghost.speed.toHasLosMultiplierBoolean()
                        if(maxSpeed == -1f) maxSpeed = minSpeed
                        if(losMultiplier) { maxSpeed *= 1.65f }

                        val notches = mutableListOf<ProgressBarNotch>()
                        minSpeed.let {
                            notches.add(ProgressBarNotch(
                                label = "%.1f".format(it / 60f),
                                xPos = 360L-it.toLong()
                            ))
                        }
                        maxSpeed.let {
                            notches.add(ProgressBarNotch(
                                label = "%.1f".format(it / 60f),
                                xPos = 360L-it.toLong()
                            ))
                        }

                        NotchedProgressBar(
                            modifier = Modifier
                                .height(24.dp)
                                .fillMaxWidth(),
                            bundle = NotchedProgressBarBundle(
                                state = NotchedProgressBarUiState(
                                    max = 360L,
                                    origin = (minSpeed).toLong(),
                                    remaining = (maxSpeed - minSpeed).toLong(),
                                    notches = notches,
                                    running = false
                                ),
                                colors = NotchedProgressBarUiColors(
                                    remaining = LocalPalette.current.primary,
                                    background = LocalPalette.current.surface,
                                    border = LocalPalette.current.onSurface,
                                    notch = LocalPalette.current.onSurface,
                                    label = LocalPalette.current.onSurface,
                                )
                            )
                        )
                    }
                }
            }

            if(rememberGhostDetails.isEmpty()) {
                TextCategoryTitle(
                    color = LocalPalette.current.onSurface,
                    text = "Empty"
                )
            }
        }

    }
}
