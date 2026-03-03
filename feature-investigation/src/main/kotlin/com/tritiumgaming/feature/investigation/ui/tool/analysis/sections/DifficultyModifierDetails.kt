package com.tritiumgaming.feature.investigation.ui.tool.analysis.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.feature.investigation.app.mappers.challenge.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.codex.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.difficultysettings.toFloat
import com.tritiumgaming.feature.investigation.app.mappers.difficultysettings.toInt
import com.tritiumgaming.feature.investigation.app.mappers.difficultysettings.toLong
import com.tritiumgaming.feature.investigation.app.mappers.difficultysettings.toStringResource
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryColumn
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.analysis.SubRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextSubTitle
import com.tritiumgaming.shared.data.difficultysetting.dto.EquipmentPermission
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources

@Composable
internal fun DifficultyModifierDetails(
    state: OperationDetailsUiState.DifficultyDetails
) {

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
                        text = "Difficulty:"
                    )

                    var title = stringResource(state.difficultyTitle.toStringResource())
                    state.challengeTitle?.let { challengeTitle ->
                        title += " [ ${stringResource(challengeTitle.toStringResource())} ]"
                    }

                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = title
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ExpandableCategoryColumn(
                expanded = false,
                containerColor = LocalPalette.current.surfaceContainerHigh,
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
                                color = LocalPalette.current.primary,
                                text = "Player"
                            )
                        }
                    }
                }
            ) {

                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Starting Sanity: "
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${ state.settings.startingSanity.toFloat() * 100f }%"
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Sanity Pill Restoration:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${ state.settings.sanityDrainSpeed.toFloat() * 100f }%"
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Sanity Drain Speed:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${ state.settings.sanityDrainSpeed.toFloat() * 100f }%"
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Sprinting:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.settings.sprinting.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Player Speed:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${ state.settings.playerSpeed.toFloat() * 100f }%"
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Flashlights:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.settings.flashlights.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Lose Items and Consumables:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.settings.loseItemsAndConsumables.toStringResource())
                    )
                }
            }

            ExpandableCategoryColumn(
                expanded = false,
                containerColor = LocalPalette.current.surfaceContainerHigh,
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
                                color = LocalPalette.current.primary,
                                text = "Ghost"
                            )
                        }
                    }
                }
            ) {

                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Ghost Speed:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${ state.settings.ghostSpeed.toFloat() * 100f }%"
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Roaming Frequency:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.settings.roamingFrequency.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Changing Favorite Room Frequency:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.settings.changingFavouriteRoom.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Activity Level:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.settings.activityLevel.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Event Frequency:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.settings.eventFrequency.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Friendly Ghost:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.settings.friendlyGhost.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Grace Period:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${ (state.settings.gracePeriod.toLong() / 1000f).toLong() } s"
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Hunt Duration:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.settings.huntDuration.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Kills Extend Hunts:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.settings.killsExtendHunts.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Evidence Given:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${ state.settings.evidenceGiven.toInt() }"
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Fingerprint Chance:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${ state.settings.fingerprintChance.toFloat() * 100f }%"
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Fingerprint Duration:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${ (state.settings.fingerprintDuration.toLong() / 1000f).toLong() } s"
                    )
                }
            }

            ExpandableCategoryColumn(
                expanded = false,
                containerColor = LocalPalette.current.surfaceContainerHigh,
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
                                color = LocalPalette.current.primary,
                                text = "Contract"
                            )
                        }
                    }
                }
            ) {

                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Setup Time:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${ (state.settings.setupTime.toLong() / 1000f).toLong() } s"
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Weather:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.settings.weather.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Doors Start Open:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.settings.doorsStartingOpen.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Number of Hiding Places:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.settings.numberOfHidingPlaces.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Sanity Monitor:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.settings.sanityMonitor.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Activity Monitor:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.settings.activityMonitor.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Fuse Box at Start of Contract:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.settings.fuseBoxAtStartOfContract.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Fuse Box Visible on Map:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.settings.fuseBoxVisibleOnMap.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Cursed Possessions Quantity:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${ state.settings.cursedPossessionsQuantity.toInt() }"
                    )
                }
                SubRow {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextSubTitle(
                            color = LocalPalette.current.onSurface,
                            text = "Cursed Possessions:"
                        )

                        state.settings.cursedPossessions.forEachIndexed { index, possession ->
                            if(index == 0 ||
                                possession != DifficultySettingResources.CursedPossession.RANDOM) {

                                TextSubTitle(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    color = LocalPalette.current.onSurfaceVariant,
                                    text = stringResource(possession.toStringResource())
                                )
                            }
                        }
                    }
                }
                SubRow {

                    Column(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if(state.settings.equipmentPermission.isEmpty()) return@SubRow

                        TextSubTitle(
                            color = LocalPalette.current.onSurface,
                            text = "Equipment Revoked:"
                        )

                        state.settings.equipmentPermission.forEachIndexed { index, permission ->
                            val perm = if(permission.permission == EquipmentPermission.Permission.REVOKED)
                                "Revoked" else "Permitted"
                            val quantity = if(permission.quantity == EquipmentPermission.ALL)
                                "All" else permission.quantity
                            val item = stringResource(permission.equipmentTitle.toStringResource())

                            TextSubTitle(
                                color = LocalPalette.current.onSurfaceVariant,
                                text = "$item [$quantity $perm]"
                            )
                        }

                    }
                }
            }

        }
    }
}
