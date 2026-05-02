package com.tritiumgaming.feature.customdifficulty.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.widgets.dropdownlist.DropdownList
import com.tritiumgaming.feature.customdifficulty.app.mappers.*
import com.tritiumgaming.feature.customdifficulty.ui.CustomDifficultyUiState
import com.tritiumgaming.feature.customdifficulty.ui.CustomDifficultyViewModel
import com.tritiumgaming.shared.data.customdifficulty.model.CustomDifficultyModel
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources

@Composable
fun CustomDifficultyScreen(
    viewModel: CustomDifficultyViewModel = viewModel(factory = CustomDifficultyViewModel.Companion.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    Box(modifier = Modifier.fillMaxSize().background(LocalPalette.current.surface)) {
        when (deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> {
                PortraitContent(
                    uiState = uiState,
                    onSelectDifficulty = viewModel::selectDifficulty,
                    onUpdateDifficulty = viewModel::updateSelectedDifficulty,
                    onSave = viewModel::saveChanges
                )
            }
            else -> {
                LandscapeContent(
                    uiState = uiState,
                    onSelectDifficulty = viewModel::selectDifficulty,
                    onUpdateDifficulty = viewModel::updateSelectedDifficulty,
                    onSave = viewModel::saveChanges
                )
            }
        }
    }
}

@Composable
private fun PortraitContent(
    uiState: CustomDifficultyUiState,
    onSelectDifficulty: (CustomDifficultyModel) -> Unit,
    onUpdateDifficulty: ((CustomDifficultyModel) -> CustomDifficultyModel) -> Unit,
    onSave: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        DifficultySelector(
            difficulties = uiState.difficulties,
            selectedDifficulty = uiState.selectedDifficulty,
            onSelect = onSelectDifficulty
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.selectedDifficulty != null) {
            SettingsEditor(
                modifier = Modifier.weight(1f),
                difficulty = uiState.selectedDifficulty,
                onUpdate = onUpdateDifficulty
            )

            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LocalPalette.current.primary),
                enabled = !uiState.isSaving
            ) {
                Text(stringResource(R.string.settings_confirm), color = LocalPalette.current.onPrimary)
            }
        } else {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text("Select a slot to edit", color = LocalPalette.current.onSurface)
            }
        }
    }
}

@Composable
private fun LandscapeContent(
    uiState: CustomDifficultyUiState,
    onSelectDifficulty: (CustomDifficultyModel) -> Unit,
    onUpdateDifficulty: ((CustomDifficultyModel) -> CustomDifficultyModel) -> Unit,
    onSave: () -> Unit
) {
    Row(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column(modifier = Modifier.width(200.dp)) {
            DifficultySelector(
                difficulties = uiState.difficulties,
                selectedDifficulty = uiState.selectedDifficulty,
                onSelect = onSelectDifficulty
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        if (uiState.selectedDifficulty != null) {
            Column(modifier = Modifier.weight(1f)) {
                SettingsEditor(
                    modifier = Modifier.weight(1f),
                    difficulty = uiState.selectedDifficulty,
                    onUpdate = onUpdateDifficulty
                )

                Button(
                    onClick = onSave,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LocalPalette.current.primary),
                    enabled = !uiState.isSaving
                ) {
                    Text(stringResource(R.string.settings_confirm), color = LocalPalette.current.onPrimary)
                }
            }
        } else {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text("Select a slot to edit", color = LocalPalette.current.onSurface)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DifficultySelector(
    difficulties: List<CustomDifficultyModel>,
    selectedDifficulty: CustomDifficultyModel?,
    onSelect: (CustomDifficultyModel) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Select Preset",
            style = MaterialTheme.typography.labelLarge,
            color = LocalPalette.current.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedDifficulty?.name ?: "",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth().menuAnchor(
                    type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                    enabled = true
                ),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = LocalPalette.current.surfaceContainerHigh,
                    unfocusedContainerColor = LocalPalette.current.surfaceContainerHigh,
                    focusedTextColor = LocalPalette.current.onSurface,
                    unfocusedTextColor = LocalPalette.current.onSurface,
                    focusedBorderColor = LocalPalette.current.primary,
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(LocalPalette.current.surfaceContainerHigh),
                scrollState = rememberScrollState()
            ) {
                difficulties.forEach { difficulty ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = difficulty.name,
                                color = LocalPalette.current.onSurface,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        onClick = {
                            onSelect(difficulty)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsEditor(
    modifier: Modifier = Modifier,
    difficulty: CustomDifficultyModel,
    onUpdate: ((CustomDifficultyModel) -> CustomDifficultyModel) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item { CategoryHeader(stringResource(R.string.difficulty_category_player)) }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_starting_sanity,
                options = DifficultySettingResources.StartingSanity.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.startingSanity.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(startingSanity = DifficultySettingResources.StartingSanity.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_sanity_pill_restoration,
                options = DifficultySettingResources.SanityPillRestoration.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.sanityPillRestoration.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(sanityPillRestoration = DifficultySettingResources.SanityPillRestoration.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_sanity_drain_speed,
                options = DifficultySettingResources.SanityDrainSpeed.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.sanityDrainSpeed.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(sanityDrainSpeed = DifficultySettingResources.SanityDrainSpeed.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_sprinting,
                options = DifficultySettingResources.Sprinting.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.sprinting.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(sprinting = DifficultySettingResources.Sprinting.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_player_speed,
                options = DifficultySettingResources.PlayerSpeed.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.playerSpeed.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(playerSpeed = DifficultySettingResources.PlayerSpeed.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_flashlights,
                options = DifficultySettingResources.Flashlights.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.flashlights.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(flashlights = DifficultySettingResources.Flashlights.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_lose_items_and_consumables,
                options = DifficultySettingResources.LoseItemsAndConsumables.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.loseItemsAndConsumables.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(loseItemsAndConsumables = DifficultySettingResources.LoseItemsAndConsumables.entries[index])) }
                }
            )
        }

        item { CategoryHeader(stringResource(R.string.difficulty_category_ghost)) }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_ghost_speed,
                options = DifficultySettingResources.GhostSpeed.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.ghostSpeed.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(ghostSpeed = DifficultySettingResources.GhostSpeed.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_roaming_frequency,
                options = DifficultySettingResources.RoamingFrequency.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.roamingFrequency.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(roamingFrequency = DifficultySettingResources.RoamingFrequency.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_changing_favourite_room,
                options = DifficultySettingResources.ChangingFavoriteRoom.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.changingFavouriteRoom.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(changingFavouriteRoom = DifficultySettingResources.ChangingFavoriteRoom.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_activity_level,
                options = DifficultySettingResources.ActivityLevel.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.activityLevel.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(activityLevel = DifficultySettingResources.ActivityLevel.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_event_frequency,
                options = DifficultySettingResources.EventFrequency.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.eventFrequency.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(eventFrequency = DifficultySettingResources.EventFrequency.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_friendly_ghost,
                options = DifficultySettingResources.FriendlyGhost.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.friendlyGhost.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(friendlyGhost = DifficultySettingResources.FriendlyGhost.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_grace_period,
                options = DifficultySettingResources.GracePeriod.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.gracePeriod.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(gracePeriod = DifficultySettingResources.GracePeriod.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_hunt_duration,
                options = DifficultySettingResources.HuntDuration.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.huntDuration.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(huntDuration = DifficultySettingResources.HuntDuration.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_kills_extend_hunts,
                options = DifficultySettingResources.KillsExtendHunts.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.killsExtendHunts.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(killsExtendHunts = DifficultySettingResources.KillsExtendHunts.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_evidence_given,
                options = DifficultySettingResources.EvidenceGiven.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.evidenceGiven.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(evidenceGiven = DifficultySettingResources.EvidenceGiven.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_fingerprint_chance,
                options = DifficultySettingResources.FingerprintChance.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.fingerprintChance.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(fingerprintChance = DifficultySettingResources.FingerprintChance.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_fingerprint_duration,
                options = DifficultySettingResources.FingerprintDuration.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.fingerprintDuration.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(fingerprintDuration = DifficultySettingResources.FingerprintDuration.entries[index])) }
                }
            )
        }

        item { CategoryHeader(stringResource(R.string.difficulty_category_contract)) }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_setup_time,
                options = DifficultySettingResources.SetupTime.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.setupTime.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(setupTime = DifficultySettingResources.SetupTime.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_weather,
                options = DifficultySettingResources.Weather.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.weather.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(weather = DifficultySettingResources.Weather.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_doors_starting_open,
                options = DifficultySettingResources.DoorsStartingOpen.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.doorsStartingOpen.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(doorsStartingOpen = DifficultySettingResources.DoorsStartingOpen.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_number_of_hiding_places,
                options = DifficultySettingResources.NumberOfHidingPlaces.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.numberOfHidingPlaces.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(numberOfHidingPlaces = DifficultySettingResources.NumberOfHidingPlaces.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_sanity_monitor,
                options = DifficultySettingResources.SanityMonitor.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.sanityMonitor.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(sanityMonitor = DifficultySettingResources.SanityMonitor.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_activity_monitor,
                options = DifficultySettingResources.ActivityMonitor.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.activityMonitor.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(activityMonitor = DifficultySettingResources.ActivityMonitor.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_fuse_box_at_start_of_contract,
                options = DifficultySettingResources.FuseBoxAtStartOfContract.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.fuseBoxAtStartOfContract.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(fuseBoxAtStartOfContract = DifficultySettingResources.FuseBoxAtStartOfContract.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_fuse_box_visible_on_map,
                options = DifficultySettingResources.FuseBoxVisibleOnMap.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.fuseBoxVisibleOnMap.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(fuseBoxVisibleOnMap = DifficultySettingResources.FuseBoxVisibleOnMap.entries[index])) }
                }
            )
        }

        item {
            SettingDropdown(
                label = R.string.difficulty_setting_title_cursed_possessions_quantity,
                options = DifficultySettingResources.CursedPossessionsQuantity.entries.map { it.toValueStringResource() },
                selectedOption = difficulty.settings.cursedPossessionsQuantity.toValueStringResource(),
                onSelect = { index ->
                    onUpdate { it.copy(settings = it.settings.copy(cursedPossessionsQuantity = DifficultySettingResources.CursedPossessionsQuantity.entries[index])) }
                }
            )
        }
    }
}

@Composable
private fun CategoryHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = LocalPalette.current.primary,
        modifier = Modifier.padding(vertical = 8.dp),
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun SettingDropdown(
    label: Int,
    options: List<Int>,
    selectedOption: Int,
    onSelect: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(label),
            style = MaterialTheme.typography.labelLarge,
            color = LocalPalette.current.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        DropdownList(
            options = options,
            label = selectedOption,
            onSelect = onSelect,
            color = LocalPalette.current.surfaceContainerHigh,
            onColor = LocalPalette.current.onSurface,
            textStyle = MaterialTheme.typography.bodyLarge
        )
    }
}
