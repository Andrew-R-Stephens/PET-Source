package com.tritiumgaming.feature.investigation.ui.tool.analysis.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.common.util.FormatterUtils.toPercentageString
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.mapper.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.challenge.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.codex.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryColumn
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.analysis.SubRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextSubTitle
import com.tritiumgaming.shared.data.codex.mappers.toEquipmentTitle
import com.tritiumgaming.shared.data.difficultysetting.dto.EquipmentPermission
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather
import com.tritiumgaming.shared.data.difficultysetting.mapper.toFloat
import com.tritiumgaming.shared.data.difficultysetting.mapper.toInt
import com.tritiumgaming.shared.data.difficultysetting.mapper.toLong
import com.tritiumgaming.shared.data.difficultysetting.mapper.toTemperatureRange
import com.tritiumgaming.shared.data.weather.model.celsius
import com.tritiumgaming.shared.data.weather.model.fahrenheit

@Composable
internal fun DifficultyModifierDetails(
    operationDetails: OperationDetailsUiState
) {
    val difficultyState = operationDetails.difficultyDetails
    val mapState = operationDetails.mapDetails
    val weatherDetails = operationDetails.weatherDetails

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
                        text = "${stringResource(R.string.investigation_timer_difficulty_label)}:"
                    )

                    var title = stringResource(difficultyState.difficultyTitle.toStringResource())
                    difficultyState.challengeTitle?.let { challengeTitle ->
                        title += " [ ${stringResource(challengeTitle.toStringResource())} ]"
                    }

                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
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
                                text = stringResource(R.string.difficulty_category_player)
                            )
                        }
                    }
                }
            ) {

                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .STARTING_SANITY.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = difficultyState.settings.startingSanity.toFloat().toPercentageString(false)
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .SANITY_PILL_RESTORATION.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = difficultyState.settings.sanityPillRestoration.toFloat().toPercentageString(false)
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .SANITY_DRAIN_SPEED.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = difficultyState.settings.sanityDrainSpeed.toFloat().toPercentageString(false)
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .SPRINTING.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(difficultyState.settings.sprinting.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .PLAYER_SPEED.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = difficultyState.settings.playerSpeed.toFloat().toPercentageString(false)
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .FLASHLIGHTS.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(difficultyState.settings.flashlights.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .LOSE_ITEMS_AND_CONSUMABLES.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(difficultyState.settings.loseItemsAndConsumables.toStringResource())
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
                                text = stringResource(R.string.difficulty_category_ghost)
                            )
                        }
                    }
                }
            ) {

                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(R.string.objectives_title_response_type)}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(difficultyState.responseType.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .GHOST_SPEED.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = difficultyState.settings.ghostSpeed.toFloat().toPercentageString(false)
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .ROAMING_FREQUENCY.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(difficultyState.settings.roamingFrequency.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .CHANGING_FAVOURITE_ROOM.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(difficultyState.settings.changingFavouriteRoom.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .ACTIVITY_LEVEL.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(difficultyState.settings.activityLevel.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .EVENT_FREQUENCY.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(difficultyState.settings.eventFrequency.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .FRIENDLY_GHOST.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(difficultyState.settings.friendlyGhost.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .GRACE_PERIOD.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${ (difficultyState.settings.gracePeriod.toLong() / 1000f).toLong() }s"
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .HUNT_DURATION.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${stringResource(difficultyState.settings.huntDuration.toStringResource())} " +
                                "( ${(difficultyState.settings.huntDuration.toLong(mapState.size)/1000f).toLong()}s )"
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .KILLS_EXTEND_HUNTS.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${stringResource(difficultyState.settings.killsExtendHunts.toStringResource())} " +
                                "( +${(difficultyState.settings.killsExtendHunts.toLong(mapState.size)/1000f).toLong()}s )"
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .EVIDENCE_GIVEN.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${ difficultyState.settings.evidenceGiven.toInt() }"
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .FINGERPRINT_CHANCE.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = difficultyState.settings.fingerprintChance.toFloat().toPercentageString(false)
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .FINGERPRINT_DURATION.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${ (difficultyState.settings.fingerprintDuration.toLong() / 1000f).toLong() }s"
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
                                text = stringResource(R.string.difficulty_category_contract)
                            )
                        }
                    }
                }
            ) {

                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .SETUP_TIME.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${ (difficultyState.settings.setupTime.toLong() / 1000f).toLong() }s"
                    )
                }

                val difficultyWeather = difficultyState.settings.weather
                val overrideWeather = weatherDetails.weather
                val weatherActual = if(overrideWeather != Weather.RANDOM) overrideWeather
                    else difficultyWeather
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .WEATHER.toStringResource())}:"
                    )

                    val weatherActualText = stringResource(difficultyWeather.toStringResource())
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "$weatherActualText${
                            if(difficultyWeather == Weather.RANDOM && overrideWeather != Weather.RANDOM) 
                                " [${stringResource(overrideWeather.toStringResource())}]" 
                            else ""
                        }"
                    )
                }
                if(weatherActual != Weather.RANDOM) {
                    SubRow {
                        TextSubTitle(
                            color = LocalPalette.current.onSurface,
                            text = "${stringResource(R.string.difficulty_setting_title_weather_temperature_range)}:"
                        )
                        val range = weatherActual.toTemperatureRange()
                        val celsius = range.celsius()
                        val fahrenheit = range.fahrenheit()
                        TextSubTitle(
                            color = LocalPalette.current.onSurfaceVariant,
                            text = "${celsius.low.toInt()}°C - ${celsius.high.toInt()}°C" +
                                    " [${fahrenheit.low}°F - ${fahrenheit.high}°F]"
                        )
                    }
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .DOORS_STARTING_OPEN.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(difficultyState.settings.doorsStartingOpen.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .NUMBER_OF_HIDING_PLACES.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(difficultyState.settings.numberOfHidingPlaces.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .SANITY_MONITOR.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(difficultyState.settings.sanityMonitor.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .ACTIVITY_MONITOR.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(difficultyState.settings.activityMonitor.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .FUSE_BOX_AT_START_OF_CONTRACT.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(difficultyState.settings.fuseBoxAtStartOfContract.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .FUSE_BOX_VISIBLE_ON_MAP.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(difficultyState.settings.fuseBoxVisibleOnMap.toStringResource())
                    )
                }
                SubRow {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(DifficultySettingResources.DifficultySetting
                            .CURSED_POSSESSIONS_QUANTITY.toStringResource())}:"
                    )
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = "${ difficultyState.settings.cursedPossessionsQuantity.toInt() }"
                    )
                }
                SubRow {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextSubTitle(
                            color = LocalPalette.current.onSurface,
                            text = "${stringResource(DifficultySettingResources.DifficultySetting
                                .CURSED_POSSESSIONS.toStringResource())}:"
                        )

                        difficultyState.settings.cursedPossessions.forEachIndexed { index, possession ->
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
                        if(difficultyState.settings.equipmentPermission.isEmpty()) return@SubRow

                        TextSubTitle(
                            color = LocalPalette.current.onSurface,
                            text = "Equipment Restrictions:"
                        )

                        difficultyState.settings.equipmentPermission.forEachIndexed { index, permission ->
                            val perm =
                                if(permission.permission == EquipmentPermission.Permission.REVOKED)
                                    stringResource(R.string.difficulty_permission_revoked)
                                else stringResource(R.string.difficulty_permission_permitted)
                            val quantity =
                                if(permission.quantity == EquipmentPermission.ALL)
                                    stringResource(R.string.difficulty_permission_quantity_all)
                                else permission.quantity
                            val item = stringResource(permission.identifier.toEquipmentTitle().toStringResource())

                            TextSubTitle(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                color = LocalPalette.current.onSurfaceVariant,
                                text = "$item [ $quantity $perm ]"
                            )
                        }

                    }
                }
            }

        }
    }
}
