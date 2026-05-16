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
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBar
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiColors
import com.tritiumgaming.core.ui.widgets.progressbar.ProgressBarNotch
import com.tritiumgaming.core.ui.widgets.text.UiText
import com.tritiumgaming.feature.investigation.app.mappers.evidence.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toStringResource
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryColumn
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.analysis.SubRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextCategoryTitle
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextSubTitle
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather
import com.tritiumgaming.shared.data.difficultysetting.mapper.toFloat
import com.tritiumgaming.shared.data.difficultysetting.model.DifficultySettingsModel
import com.tritiumgaming.shared.data.ghost.mapper.toHasLosMultiplierBoolean
import com.tritiumgaming.shared.data.ghost.mapper.toMaximumAsInt
import com.tritiumgaming.shared.data.ghost.mapper.toMinimumAsInt
import com.tritiumgaming.shared.data.ghost.mapper.toSanityBounds
import com.tritiumgaming.shared.data.investigation.model.DifficultyOverridesData
import kotlin.math.max

@Composable
internal fun ActiveGhostModifierDetails(
    state: OperationDetailsUiState.GhostDetails,
    difficultySettings: DifficultySettingsModel? = null,
    overrides: DifficultyOverridesData? = null
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
                        text = "${stringResource(R.string.investigation_section_title_active_ghosts)}:"
                    )
                    TextSubTitle(
                        modifier = Modifier.padding(start= 8.dp),
                        color = LocalPalette.current.onSurfaceVariant,
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
                            text = "${stringResource(R.string.investigation_section_title_evidence) }:"
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
                            text = "${stringResource(R.string.investigation_section_title_hunt_sanity) }:"
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
                                label = UiText.DynamicString("${it.toLong()}"),
                                xPos = 100-it.toLong()
                            ))
                            highest = max(highest, it.toLong())
                        }
                        sanityBounds.normal?.let {
                            notches.add(ProgressBarNotch(
                                label = UiText.DynamicString("${it.toLong()}"),
                                xPos = 100-it.toLong()
                            ))
                            highest = max(highest, it.toLong())
                        }
                        sanityBounds.empowered?.let {
                            notches.add(ProgressBarNotch(
                                label = UiText.DynamicString("${it.toLong()}"),
                                xPos = 100-it.toLong()
                            ))
                            highest = max(highest, it.toLong())
                        }

                        NotchedProgressBar(
                            modifier = Modifier
                                .height(24.dp)
                                .fillMaxWidth(),
                            colors = NotchedProgressBarUiColors(
                                remaining = LocalPalette.current.primary,
                                background = LocalPalette.current.surface,
                                border = LocalPalette.current.onSurface,
                                notch = LocalPalette.current.onSurface,
                                label = LocalPalette.current.onSurface,
                            ),
                            max = 100L,
                            remaining = highest,
                            notches = notches,
                        )
                    }

                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextSubTitle(
                            modifier = Modifier,
                            color = LocalPalette.current.onSurface,
                            text = "${stringResource(R.string.investigation_section_title_movement_speed) }:"
                        )
                    }

                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(start = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val minBase = state.ghostEvidence.ghost.speed.toMinimumAsInt().toFloat()
                        var maxBase = state.ghostEvidence.ghost.speed.toMaximumAsInt().toFloat()
                        val losMultiplier = state.ghostEvidence.ghost.speed.toHasLosMultiplierBoolean()
                        if(maxBase == -1f) maxBase = minBase

                        val difficultyMultiplier = difficultySettings?.ghostSpeed?.toFloat() ?: 1f
                        val weather = if (difficultySettings?.weather == Weather.RANDOM)
                            overrides?.weather ?: Weather.RANDOM else difficultySettings?.weather ?: Weather.RANDOM
                        val weatherMultiplier = if (weather == Weather.BLOOD_MOON) 1.15f else 1f
                        val fuseBoxMultiplier = 1f // Placeholder

                        val minSpeed = minBase * difficultyMultiplier * weatherMultiplier * fuseBoxMultiplier
                        var maxSpeed = maxBase * difficultyMultiplier * weatherMultiplier * fuseBoxMultiplier

                        if(losMultiplier) { maxSpeed *= 1.65f }

                        val notches = mutableListOf<ProgressBarNotch>()
                        minSpeed.let {
                            notches.add(ProgressBarNotch(
                                label = UiText.DynamicString("%.1f".format(it / 60f)),
                                xPos = 360L-it.toLong()
                            ))
                        }
                        maxSpeed.let {
                            notches.add(ProgressBarNotch(
                                label = UiText.DynamicString("%.1f".format(it / 60f)),
                                xPos = 360L-it.toLong()
                            ))
                        }

                        NotchedProgressBar(
                            modifier = Modifier
                                .height(24.dp)
                                .fillMaxWidth(),
                            max = 360L,
                            remaining = (maxSpeed - minSpeed).toLong(),
                            notches = notches,
                            colors = NotchedProgressBarUiColors(
                                remaining = LocalPalette.current.primary,
                                background = LocalPalette.current.surface,
                                border = LocalPalette.current.onSurface,
                                notch = LocalPalette.current.onSurface,
                                label = LocalPalette.current.onSurface,
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
