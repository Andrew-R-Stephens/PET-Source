package com.tritiumgaming.feature.customdifficulty.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.common.util.FormatterUtils.toPercentageString
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.mapper.toStringResource
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.widgets.dropdownlist.DropdownList
import com.tritiumgaming.feature.customdifficulty.ui.CustomDifficultyUiState
import com.tritiumgaming.feature.customdifficulty.ui.CustomDifficultyViewModel
import com.tritiumgaming.shared.data.customdifficulty.CustomDifficultyResources
import com.tritiumgaming.shared.data.customdifficulty.model.CustomDifficultyModel
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources
import com.tritiumgaming.shared.data.difficultysetting.mapper.toFloat
import com.tritiumgaming.shared.data.difficultysetting.mapper.toInt
import com.tritiumgaming.shared.data.difficultysetting.mapper.toLong

@Composable
fun CustomDifficultyScreen(
    viewModel: CustomDifficultyViewModel = viewModel(factory = CustomDifficultyViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    Box(modifier = Modifier
        .fillMaxSize()
        .background(LocalPalette.current.surface)) {
        when (deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> {
                PortraitContent(
                    uiState = uiState,
                    onSelectDifficulty = viewModel::selectDifficulty,
                    onUpdateDifficulty = viewModel::updateSelectedDifficulty,
                    onSave = viewModel::saveChanges,
                    onRevert = viewModel::revertChanges
                )
            }
            else -> {
                LandscapeContent(
                    uiState = uiState,
                    onSelectDifficulty = viewModel::selectDifficulty,
                    onUpdateDifficulty = viewModel::updateSelectedDifficulty,
                    onSave = viewModel::saveChanges,
                    onRevert = viewModel::revertChanges
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
    onSave: () -> Unit,
    onRevert: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {
        DifficultySelector(
            options = uiState.difficulties,
            selectedDifficulty = uiState.selectedDifficulty,
            onSelect = onSelectDifficulty
        )

        if (uiState.selectedDifficulty != null) {
            SettingsEditor(
                modifier = Modifier.weight(1f),
                difficulty = uiState.selectedDifficulty,
                onUpdate = onUpdateDifficulty
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onRevert,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = LocalPalette.current.surfaceContainerHigh),
                    enabled = !uiState.isSaving
                ) {
                    Text(stringResource(R.string.general_label_revert), color = LocalPalette.current.onSurface)
                }

                Button(
                    onClick = onSave,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = LocalPalette.current.primary),
                    enabled = !uiState.isSaving
                ) {
                    Text(stringResource(R.string.general_label_save), color = LocalPalette.current.onPrimary)
                }
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
    onSave: () -> Unit,
    onRevert: () -> Unit
) {
    Row(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Column(modifier = Modifier.width(200.dp)) {
            DifficultySelector(
                options = uiState.difficulties,
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onRevert,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = LocalPalette.current.surfaceContainerHigh),
                        enabled = !uiState.isSaving
                    ) {
                        Text(stringResource(R.string.general_label_revert), color = LocalPalette.current.onSurface)
                    }

                    Button(
                        onClick = onSave,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = LocalPalette.current.primary),
                        enabled = !uiState.isSaving
                    ) {
                        Text(stringResource(R.string.general_label_save), color = LocalPalette.current.onPrimary)
                    }
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
    options: List<CustomDifficultyModel>,
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
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(
                        type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                        enabled = true
                    ),
                value = selectedDifficulty?.name ?: "",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = LocalPalette.current.surfaceContainer,
                    unfocusedContainerColor = LocalPalette.current.surfaceContainer,
                    focusedTextColor = LocalPalette.current.onSurface,
                    unfocusedTextColor = LocalPalette.current.onSurface,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp)
            )

            ExposedDropdownMenu(
                modifier = Modifier.background(LocalPalette.current.surfaceContainerHigh),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                scrollState = rememberScrollState()
            ) {
                options.forEach { difficulty ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = difficulty.name ?: "${ stringResource(CustomDifficultyResources.Title.CUSTOM.toStringResource()) } ${difficulty.id}" ,
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
        item {
            Surface(
                color = LocalPalette.current.surfaceContainerLow,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CategoryHeader(stringResource(R.string.difficulty_category_player))

                    SettingDropdown(
                        label = R.string.difficulty_setting_title_starting_sanity,
                        options = DifficultySettingResources.StartingSanity.entries.map { it.toFloat().toPercentageString(false) },
                        selectedOption = difficulty.settings.startingSanity.toFloat().toPercentageString(false),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(startingSanity = DifficultySettingResources.StartingSanity.entries[index])) }
                        }
                    )

                    SettingDropdown(
                        label = R.string.difficulty_setting_title_sanity_pill_restoration,
                        options = DifficultySettingResources.SanityPillRestoration.entries.map { it.toFloat().toPercentageString(false) },
                        selectedOption = difficulty.settings.sanityPillRestoration.toFloat().toPercentageString(false),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(sanityPillRestoration = DifficultySettingResources.SanityPillRestoration.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_sanity_drain_speed,
                        options = DifficultySettingResources.SanityDrainSpeed.entries.map { it.toFloat().toPercentageString(false) },
                        selectedOption = difficulty.settings.sanityDrainSpeed.toFloat().toPercentageString(false),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(sanityDrainSpeed = DifficultySettingResources.SanityDrainSpeed.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_sprinting,
                        options = DifficultySettingResources.Sprinting.entries.map { stringResource(it.toStringResource()) },
                        selectedOption = stringResource(difficulty.settings.sprinting.toStringResource()),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(sprinting = DifficultySettingResources.Sprinting.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_player_speed,
                        options = DifficultySettingResources.PlayerSpeed.entries.map { it.toFloat().toPercentageString(false) },
                        selectedOption = difficulty.settings.playerSpeed.toFloat().toPercentageString(false),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(playerSpeed = DifficultySettingResources.PlayerSpeed.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_flashlights,
                        options = DifficultySettingResources.Flashlights.entries.map { stringResource(it.toStringResource()) },
                        selectedOption = stringResource(difficulty.settings.flashlights.toStringResource()),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(flashlights = DifficultySettingResources.Flashlights.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_lose_items_and_consumables,
                        options = DifficultySettingResources.LoseItemsAndConsumables.entries.map { stringResource(it.toStringResource()) },
                        selectedOption = stringResource(difficulty.settings.loseItemsAndConsumables.toStringResource()),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(loseItemsAndConsumables = DifficultySettingResources.LoseItemsAndConsumables.entries[index])) }
                        }
                    )
                }
            }
        }

        item {
            Surface(
                color = LocalPalette.current.surfaceContainerLow,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CategoryHeader(stringResource(R.string.difficulty_category_ghost))


                    SettingDropdown(
                        label = R.string.difficulty_setting_title_ghost_speed,
                        options = DifficultySettingResources.GhostSpeed.entries.map { it.toFloat().toPercentageString(false) },
                        selectedOption = difficulty.settings.ghostSpeed.toFloat().toPercentageString(false),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(ghostSpeed = DifficultySettingResources.GhostSpeed.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_roaming_frequency,
                        options = DifficultySettingResources.RoamingFrequency.entries.map { stringResource(it.toStringResource()) },
                        selectedOption = stringResource(difficulty.settings.roamingFrequency.toStringResource()),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(roamingFrequency = DifficultySettingResources.RoamingFrequency.entries[index])) }
                        }
                    )

                    SettingDropdown(
                        label = R.string.difficulty_setting_title_changing_favourite_room,
                        options = DifficultySettingResources.ChangingFavoriteRoom.entries.map { stringResource(it.toStringResource()) },
                        selectedOption = stringResource(difficulty.settings.changingFavouriteRoom.toStringResource()),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(changingFavouriteRoom = DifficultySettingResources.ChangingFavoriteRoom.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_activity_level,
                        options = DifficultySettingResources.ActivityLevel.entries.map { stringResource(it.toStringResource()) },
                        selectedOption = stringResource(difficulty.settings.activityLevel.toStringResource()),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(activityLevel = DifficultySettingResources.ActivityLevel.entries[index])) }
                        }
                    )

                    SettingDropdown(
                        label = R.string.difficulty_setting_title_event_frequency,
                        options = DifficultySettingResources.EventFrequency.entries.map { stringResource(it.toStringResource()) },
                        selectedOption = stringResource(difficulty.settings.eventFrequency.toStringResource()),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(eventFrequency = DifficultySettingResources.EventFrequency.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_friendly_ghost,
                        options = DifficultySettingResources.FriendlyGhost.entries.map { stringResource(it.toStringResource()) },
                        selectedOption = stringResource(difficulty.settings.friendlyGhost.toStringResource()),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(friendlyGhost = DifficultySettingResources.FriendlyGhost.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_grace_period,
                        options = DifficultySettingResources.GracePeriod.entries.map { "${(it.toLong() / 1000f).toLong()}s" },
                        selectedOption = "${(difficulty.settings.gracePeriod.toLong() / 1000f).toLong()}s",
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(gracePeriod = DifficultySettingResources.GracePeriod.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_hunt_duration,
                        options = DifficultySettingResources.HuntDuration.entries.map { stringResource(it.toStringResource()) },
                        selectedOption = stringResource(difficulty.settings.huntDuration.toStringResource()),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(huntDuration = DifficultySettingResources.HuntDuration.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_kills_extend_hunts,
                        options = DifficultySettingResources.KillsExtendHunts.entries.map { stringResource(it.toStringResource()) },
                        selectedOption = stringResource(difficulty.settings.killsExtendHunts.toStringResource()),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(killsExtendHunts = DifficultySettingResources.KillsExtendHunts.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_evidence_given,
                        options = DifficultySettingResources.EvidenceGiven.entries.map { it.toInt().toString() },
                        selectedOption = difficulty.settings.evidenceGiven.toInt().toString(),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(evidenceGiven = DifficultySettingResources.EvidenceGiven.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_fingerprint_chance,
                        options = DifficultySettingResources.FingerprintChance.entries.map { it.toFloat().toPercentageString(false) },
                        selectedOption = difficulty.settings.fingerprintChance.toFloat().toPercentageString(false),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(fingerprintChance = DifficultySettingResources.FingerprintChance.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_fingerprint_duration,
                        options = DifficultySettingResources.FingerprintDuration.entries.map {
                            val duration = it.toLong()
                            if (duration == -1L) stringResource(R.string.difficulty_setting_state_infinite)
                            else "${(duration / 1000f).toLong()}s"
                        },
                        selectedOption = difficulty.settings.fingerprintDuration.toLong().let {
                            if (it == -1L) stringResource(R.string.difficulty_setting_state_infinite)
                            else "${(it / 1000f).toLong()}s"
                        },
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(fingerprintDuration = DifficultySettingResources.FingerprintDuration.entries[index])) }
                        }
                    )
                }
            }
        }

        item {
            Surface(
                color = LocalPalette.current.surfaceContainerLow,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    CategoryHeader(stringResource(R.string.difficulty_category_contract))

                    SettingDropdown(
                        label = R.string.difficulty_setting_title_setup_time,
                        options = DifficultySettingResources.SetupTime.entries.map { "${(it.toLong() / 1000f).toLong()}s" },
                        selectedOption = "${(difficulty.settings.setupTime.toLong() / 1000f).toLong()}s",
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(setupTime = DifficultySettingResources.SetupTime.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_weather,
                        options = DifficultySettingResources.Weather.entries.map { stringResource(it.toStringResource()) },
                        selectedOption = stringResource(difficulty.settings.weather.toStringResource()),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(weather = DifficultySettingResources.Weather.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_doors_starting_open,
                        options = DifficultySettingResources.DoorsStartingOpen.entries.map { stringResource(it.toStringResource()) },
                        selectedOption = stringResource(difficulty.settings.doorsStartingOpen.toStringResource()),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(doorsStartingOpen = DifficultySettingResources.DoorsStartingOpen.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_number_of_hiding_places,
                        options = DifficultySettingResources.NumberOfHidingPlaces.entries.map { stringResource(it.toStringResource()) },
                        selectedOption = stringResource(difficulty.settings.numberOfHidingPlaces.toStringResource()),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(numberOfHidingPlaces = DifficultySettingResources.NumberOfHidingPlaces.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_sanity_monitor,
                        options = DifficultySettingResources.SanityMonitor.entries.map { stringResource(it.toStringResource()) },
                        selectedOption = stringResource(difficulty.settings.sanityMonitor.toStringResource()),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(sanityMonitor = DifficultySettingResources.SanityMonitor.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_activity_monitor,
                        options = DifficultySettingResources.ActivityMonitor.entries.map { stringResource(it.toStringResource()) },
                        selectedOption = stringResource(difficulty.settings.activityMonitor.toStringResource()),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(activityMonitor = DifficultySettingResources.ActivityMonitor.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_fuse_box_at_start_of_contract,
                        options = DifficultySettingResources.FuseBoxAtStartOfContract.entries.map { stringResource(it.toStringResource()) },
                        selectedOption = stringResource(difficulty.settings.fuseBoxAtStartOfContract.toStringResource()),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(fuseBoxAtStartOfContract = DifficultySettingResources.FuseBoxAtStartOfContract.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_fuse_box_visible_on_map,
                        options = DifficultySettingResources.FuseBoxVisibleOnMap.entries.map { stringResource(it.toStringResource()) },
                        selectedOption = stringResource(difficulty.settings.fuseBoxVisibleOnMap.toStringResource()),
                        onSelect = { index ->
                            onUpdate { it.copy(settings = it.settings.copy(fuseBoxVisibleOnMap = DifficultySettingResources.FuseBoxVisibleOnMap.entries[index])) }
                        }
                    )
                    SettingDropdown(
                        label = R.string.difficulty_setting_title_cursed_possessions_quantity,
                        options = DifficultySettingResources.CursedPossessionsQuantity.entries.map { it.toInt().toString() },
                        selectedOption = difficulty.settings.cursedPossessionsQuantity.toInt().toString(),
                        onSelect = { index ->
                            onUpdate { it.copy(
                                settings = it.settings.copy(
                                    cursedPossessionsQuantity = DifficultySettingResources.CursedPossessionsQuantity.entries[index])) }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryHeader(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        text = text.uppercase(),
        style = LocalTypography.current.quaternary.bold.copy(
            fontSize = 16.sp
        ),
        color = LocalPalette.current.primary,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun SettingDropdown(
    label: Int,
    options: List<String>,
    selectedOption: String,
    onSelect: (Int) -> Unit
) {
    Surface(
        color = LocalPalette.current.surfaceContainer,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(label).uppercase(),
                style = LocalTypography.current.quaternary.bold.copy(
                    fontSize = 14.sp
                ),
                color = LocalPalette.current.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            DropdownList(
                options = options,
                label = selectedOption.uppercase(),
                onSelect = onSelect,
                color = LocalPalette.current.surfaceContainerHigh,
                onColor = LocalPalette.current.onSurface,
                textStyle = LocalTypography.current.quaternary.bold,
                selectionFontSize = 14.sp,
                optionsFontSize = 14.sp
            )
        }
    }
}
