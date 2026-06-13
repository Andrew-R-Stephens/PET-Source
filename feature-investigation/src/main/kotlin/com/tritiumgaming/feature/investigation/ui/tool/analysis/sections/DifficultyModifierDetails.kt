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
import com.tritiumgaming.core.ui.mapper.toStringResource
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.feature.investigation.app.mappers.challenge.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.codex.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryColumn
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextDataRow
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
    difficultyState: OperationDetailsUiState.DifficultyDetails,
    mapState: OperationDetailsUiState.MapDetails,
    weatherDetails: OperationDetailsUiState.WeatherDetails
) {
    ExpandableCategoryColumn(
        expanded = false,
        containerColor = LocalPalette.current.surfaceContainer,
        defaultContent = { modifier, expanded ->
            ExpandableCategoryRow(
                modifier = modifier,
                isExpanded = expanded
            ) { rowModifier ->
                Row(
                    modifier = rowModifier,
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
        ExpandableCategoryColumn(
            expanded = false,
            containerColor = LocalPalette.current.surfaceContainerHigh,
            defaultContent = { modifier, expanded ->
                ExpandableCategoryRow(
                    modifier = modifier,
                    isExpanded = expanded
                ) { rowModifier ->
                    TextSubTitle(
                        modifier = rowModifier,
                        color = LocalPalette.current.primary,
                        text = stringResource(R.string.difficulty_category_player)
                    )
                }
            }
        ) {
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.STARTING_SANITY.toStringResource())}:",
                data = difficultyState.settings.startingSanity.toFloat().toPercentageString(false)
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.SANITY_PILL_RESTORATION.toStringResource())}:",
                data = difficultyState.settings.sanityPillRestoration.toFloat().toPercentageString(false)
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.SANITY_DRAIN_SPEED.toStringResource())}:",
                data = difficultyState.settings.sanityDrainSpeed.toFloat().toPercentageString(false)
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.SPRINTING.toStringResource())}:",
                data = stringResource(difficultyState.settings.sprinting.toStringResource())
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.PLAYER_SPEED.toStringResource())}:",
                data = difficultyState.settings.playerSpeed.toFloat().toPercentageString(false)
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.FLASHLIGHTS.toStringResource())}:",
                data = stringResource(difficultyState.settings.flashlights.toStringResource())
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.LOSE_ITEMS_AND_CONSUMABLES.toStringResource())}:",
                data = stringResource(difficultyState.settings.loseItemsAndConsumables.toStringResource())
            )
        }

        ExpandableCategoryColumn(
            expanded = false,
            containerColor = LocalPalette.current.surfaceContainerHigh,
            defaultContent = { modifier, expanded ->
                ExpandableCategoryRow(
                    modifier = modifier,
                    isExpanded = expanded
                ) { rowModifier ->
                    TextSubTitle(
                        modifier = rowModifier,
                        color = LocalPalette.current.primary,
                        text = stringResource(R.string.difficulty_category_ghost)
                    )
                }
            }
        ) {
            TextDataRow(
                title = "${stringResource(R.string.objectives_title_response_type)}:",
                data = stringResource(difficultyState.responseType.toStringResource())
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.GHOST_SPEED.toStringResource())}:",
                data = difficultyState.settings.ghostSpeed.toFloat().toPercentageString(false)
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.ROAMING_FREQUENCY.toStringResource())}:",
                data = stringResource(difficultyState.settings.roamingFrequency.toStringResource())
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.CHANGING_FAVOURITE_ROOM.toStringResource())}:",
                data = stringResource(difficultyState.settings.changingFavouriteRoom.toStringResource())
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.ACTIVITY_LEVEL.toStringResource())}:",
                data = stringResource(difficultyState.settings.activityLevel.toStringResource())
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.EVENT_FREQUENCY.toStringResource())}:",
                data = stringResource(difficultyState.settings.eventFrequency.toStringResource())
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.FRIENDLY_GHOST.toStringResource())}:",
                data = stringResource(difficultyState.settings.friendlyGhost.toStringResource())
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.GRACE_PERIOD.toStringResource())}:",
                data = "${(difficultyState.settings.gracePeriod.toLong() / 1000f).toLong()}s"
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.HUNT_DURATION.toStringResource())}:",
                data = "${stringResource(difficultyState.settings.huntDuration.toStringResource())} " +
                        "( ${(difficultyState.settings.huntDuration.toLong(mapState.size) / 1000f).toLong()}s )"
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.KILLS_EXTEND_HUNTS.toStringResource())}:",
                data = "${stringResource(difficultyState.settings.killsExtendHunts.toStringResource())} " +
                        "( +${(difficultyState.settings.killsExtendHunts.toLong(mapState.size) / 1000f).toLong()}s )"
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.EVIDENCE_GIVEN.toStringResource())}:",
                data = "${difficultyState.settings.evidenceGiven.toInt()}"
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.FINGERPRINT_CHANCE.toStringResource())}:",
                data = difficultyState.settings.fingerprintChance.toFloat().toPercentageString(false)
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.FINGERPRINT_DURATION.toStringResource())}:",
                data = "${(difficultyState.settings.fingerprintDuration.toLong() / 1000f).toLong()}s"
            )
        }

        ExpandableCategoryColumn(
            expanded = false,
            containerColor = LocalPalette.current.surfaceContainerHigh,
            defaultContent = { modifier, expanded ->
                ExpandableCategoryRow(
                    modifier = modifier,
                    isExpanded = expanded
                ) { rowModifier ->
                    TextSubTitle(
                        modifier = rowModifier,
                        color = LocalPalette.current.primary,
                        text = stringResource(R.string.difficulty_category_contract)
                    )
                }
            }
        ) {
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.SETUP_TIME.toStringResource())}:",
                data = "${(difficultyState.settings.setupTime.toLong() / 1000f).toLong()}s"
            )

            val difficultyWeather = difficultyState.settings.weather
            val overrideWeather = weatherDetails.weather
            val weatherActual = if (overrideWeather != Weather.RANDOM) overrideWeather
            else difficultyWeather

            val weatherActualText = stringResource(difficultyWeather.toStringResource())
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.WEATHER.toStringResource())}:",
                data = "$weatherActualText${
                    if (difficultyWeather == Weather.RANDOM && overrideWeather != Weather.RANDOM)
                        " [${stringResource(overrideWeather.toStringResource())}]"
                    else ""
                }"
            )

            if (weatherActual != Weather.RANDOM) {
                val range = weatherActual.toTemperatureRange()
                val celsius = range.celsius()
                val fahrenheit = range.fahrenheit()
                TextDataRow(
                    title = "${stringResource(R.string.difficulty_setting_title_weather_temperature_range)}:",
                    data = "${celsius.low.toInt()}°C - ${celsius.high.toInt()}°C" +
                            " [${fahrenheit.low}°F - ${fahrenheit.high}°F]"
                )
            }

            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.DOORS_STARTING_OPEN.toStringResource())}:",
                data = stringResource(difficultyState.settings.doorsStartingOpen.toStringResource())
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.NUMBER_OF_HIDING_PLACES.toStringResource())}:",
                data = stringResource(difficultyState.settings.numberOfHidingPlaces.toStringResource())
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.SANITY_MONITOR.toStringResource())}:",
                data = stringResource(difficultyState.settings.sanityMonitor.toStringResource())
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.ACTIVITY_MONITOR.toStringResource())}:",
                data = stringResource(difficultyState.settings.activityMonitor.toStringResource())
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.FUSE_BOX_AT_START_OF_CONTRACT.toStringResource())}:",
                data = stringResource(difficultyState.settings.fuseBoxAtStartOfContract.toStringResource())
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.FUSE_BOX_VISIBLE_ON_MAP.toStringResource())}:",
                data = stringResource(difficultyState.settings.fuseBoxVisibleOnMap.toStringResource())
            )
            TextDataRow(
                title = "${stringResource(DifficultySettingResources.DifficultySetting.CURSED_POSSESSIONS_QUANTITY.toStringResource())}:",
                data = "${difficultyState.settings.cursedPossessionsQuantity.toInt()}"
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextSubTitle(
                    color = LocalPalette.current.onSurface,
                    text = "${stringResource(DifficultySettingResources.DifficultySetting.CURSED_POSSESSIONS.toStringResource())}:"
                )

                difficultyState.settings.cursedPossessions.forEachIndexed { index, possession ->
                    if (index == 0 ||
                        possession != DifficultySettingResources.CursedPossession.RANDOM
                    ) {
                        TextSubTitle(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            color = LocalPalette.current.onSurfaceVariant,
                            text = stringResource(possession.toStringResource())
                        )
                    }
                }
            }
            if (difficultyState.settings.equipmentPermission.isNotEmpty()) {
                Column(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Equipment Restrictions:"
                    )

                    difficultyState.settings.equipmentPermission.forEach { permission ->
                        val perm =
                            if (permission.permission == EquipmentPermission.Permission.REVOKED)
                                stringResource(R.string.difficulty_permission_revoked)
                            else stringResource(R.string.difficulty_permission_permitted)
                        val quantity =
                            if (permission.quantity == EquipmentPermission.ALL)
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
