package com.tritiumgaming.feature.customdifficulty.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.common.util.FormatterUtils.toPercentageString
import com.tritiumgaming.core.common.util.ValidationUtils
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.MarkCheckIcon
import com.tritiumgaming.core.ui.mapper.toStringResource
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.dropdownlist.DropdownList
import com.tritiumgaming.core.ui.widgets.indicator.InfiniteThrobber
import com.tritiumgaming.core.ui.widgets.menus.NavigationHeaderCenter
import com.tritiumgaming.core.ui.widgets.menus.NavigationHeaderComposable
import com.tritiumgaming.core.ui.widgets.menus.NavigationHeaderSideButton
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
    navController: NavController,
    viewModel: CustomDifficultyViewModel = viewModel(factory = CustomDifficultyViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    val onNavigateBack = {
        navController.popBackStack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalPalette.current.surface)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        NavigationHeader(
            onLeftClick = { onNavigateBack() }
        )

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
    var isEditingName by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {
        DifficultySelector(
            modifier = Modifier
                .fillMaxWidth(),
            options = uiState.difficulties,
            selectedDifficulty = uiState.selectedDifficulty,
            onSelect = onSelectDifficulty,
            onEditStateChange = { isEditingName = it },
            onNameChange = { newName ->
                onUpdateDifficulty { it.copy(name = newName.ifBlank { null }) }
            }
        )

        if (uiState.selectedDifficulty != null) {
            SettingsEditor(
                modifier = Modifier.weight(1f),
                difficulty = uiState.selectedDifficulty,
                onUpdate = onUpdateDifficulty
            )

            ActionButtons(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                orientation = Orientation.Horizontal,
                hasChanges = uiState.hasChanges && !isEditingName,
                isSaving = uiState.isSaving,
                onSave = onSave,
                onRevert = onRevert
            )
        } else {
            LoadingState(modifier = Modifier
                .weight(1f)
                .fillMaxWidth())
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
    var isEditingName by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Min),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Min),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DifficultySelector(
                    modifier = Modifier
                        .fillMaxWidth(),
                    options = uiState.difficulties,
                    selectedDifficulty = uiState.selectedDifficulty,
                    onSelect = onSelectDifficulty,
                    onEditStateChange = { isEditingName = it },
                    onNameChange = { newName ->
                        onUpdateDifficulty { it.copy(name = newName.ifBlank { null }) }
                    }
                )

                if (uiState.selectedDifficulty != null) {
                    val selected = uiState.selectedDifficulty
                    val defaultName =
                        "${stringResource(CustomDifficultyResources.Title.CUSTOM.toStringResource())} ${selected.id}"

                    PresetSlotInfo(defaultName = defaultName)
                } else {
                    LoadingState(modifier = Modifier.fillMaxSize())
                }
            }

            ActionButtons(
                modifier = Modifier
                    .fillMaxWidth(),
                orientation = Orientation.Vertical,
                hasChanges = uiState.hasChanges && !isEditingName,
                isSaving = uiState.isSaving,
                onSave = onSave,
                onRevert = onRevert
            )
        }

        Column(modifier = Modifier.weight(1f)) {

            if(uiState.selectedDifficulty != null) {
                val selected = uiState.selectedDifficulty
                val defaultName = "${stringResource(CustomDifficultyResources.Title.CUSTOM.toStringResource())} ${selected.id}"

                Text(
                    text = defaultName.uppercase(),
                    style = LocalTypography.current.quaternary.bold.copy(
                        fontSize = 20.sp
                    ),
                    color = LocalPalette.current.onSurface,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )

                selected.name?.let {
                    Text(
                        text = it,
                        style = LocalTypography.current.quaternary.bold.copy(
                            fontSize = 16.sp
                        ),
                        color = LocalPalette.current.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                SettingsEditor(
                    modifier = Modifier.weight(1f),
                    difficulty = selected,
                    showPresetInfo = false,
                    onUpdate = onUpdateDifficulty,
                )

            } else {
                LoadingState(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DifficultySelector(
    modifier: Modifier,
    options: List<CustomDifficultyModel>,
    selectedDifficulty: CustomDifficultyModel?,
    onSelect: (CustomDifficultyModel) -> Unit,
    onEditStateChange: (Boolean) -> Unit = {},
    onNameChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var isError by remember(selectedDifficulty?.id) { mutableStateOf(false) }
    var errorMessage by remember(selectedDifficulty?.id) { mutableStateOf("") }
    var everFocused by remember(isEditing) { mutableStateOf(false) }

    LaunchedEffect(isEditing) {
        onEditStateChange(isEditing)
    }

    val currentDefaultName = selectedDifficulty?.let {
        "${stringResource(CustomDifficultyResources.Title.CUSTOM.toStringResource())} ${it.id}"
    } ?: ""

    val currentDisplayName = selectedDifficulty?.let {
        it.name ?: currentDefaultName
    } ?: ""

    var editedName by remember(selectedDifficulty?.id) {
        mutableStateOf(selectedDifficulty?.name ?: "")
    }

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val onCommitName = {
        try {
            val validatedName = ValidationUtils.validateUserString(editedName, maxLength = 32)
            onNameChange(validatedName)
            editedName = validatedName
            isEditing = false
            isError = false
            focusManager.clearFocus()
        } catch (e: IllegalArgumentException) {
            isError = true
            errorMessage = e.message ?: ""
        }
    }

    LaunchedEffect(currentDisplayName) {
        editedName = selectedDifficulty?.name ?: ""
        isEditing = false
        isError = false
    }

    LaunchedEffect(isEditing) {
        if (isEditing) {
            focusRequester.requestFocus()
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        everFocused = true
                    }
                    if (!focusState.isFocused && isEditing && everFocused) {
                        editedName = currentDisplayName
                        isEditing = false
                        isError = false
                    }
                },
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            if (isEditing) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    value = editedName,
                    onValueChange = {
                        editedName = it
                        try {
                            ValidationUtils.validateUserString(it, maxLength = 32)
                            isError = false
                        } catch (e: IllegalArgumentException) {
                            isError = true
                            errorMessage = e.message ?: ""
                        }
                    },
                    isError = isError,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { onCommitName() }),
                    supportingText = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (isError) {
                                Text(
                                    text = errorMessage,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Text(
                                text = "${editedName.length} / 32",
                                textAlign = TextAlign.End,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = if (isError) Modifier else Modifier.fillMaxWidth()
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = LocalPalette.current.surfaceContainer,
                        unfocusedContainerColor = LocalPalette.current.surfaceContainer,
                        focusedTextColor = LocalPalette.current.onSurface,
                        unfocusedTextColor = LocalPalette.current.onSurface,
                        focusedBorderColor = LocalPalette.current.primary,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
            } else {
                ExposedDropdownMenuBox(
                    modifier = Modifier.weight(1f),
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(
                                type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                                enabled = true
                            ),
                        value = currentDisplayName,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
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
                        options.filter { it.id != selectedDifficulty?.id }.forEach { difficulty ->
                            val defaultName = "${stringResource(CustomDifficultyResources.Title.CUSTOM.toStringResource())} ${difficulty.id}"
                            val displayName = if (difficulty.name != null) "${difficulty.name} [$defaultName]" else defaultName

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = displayName,
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

            if (selectedDifficulty != null) {
                IconButton(
                    modifier = Modifier
                        .padding(top = 4.dp),
                    onClick = {
                        if (isEditing) {
                            onCommitName()
                        } else {
                            editedName = selectedDifficulty.name ?: ""
                            expanded = false
                            isEditing = true
                        }
                    }
                ) {
                    if (isEditing) {
                        MarkCheckIcon(
                            modifier = Modifier.size(24.dp),
                            colors = IconVectorColors.defaults(
                                fillColor = if (isError) MaterialTheme.colorScheme.error else LocalPalette.current.primary
                            )
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Name",
                            tint = LocalPalette.current.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsEditor(
    modifier: Modifier = Modifier,
    difficulty: CustomDifficultyModel,
    showPresetInfo: Boolean = true,
    onUpdate: ((CustomDifficultyModel) -> CustomDifficultyModel) -> Unit
) {
    val defaultName = "${stringResource(CustomDifficultyResources.Title.CUSTOM.toStringResource())} ${difficulty.id}"

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if(showPresetInfo) {
            item {
                PresetSlotInfo(defaultName = defaultName)
            }
        }

        item {
            SettingCategory(title = stringResource(R.string.difficulty_category_player)) {
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

        item {
            SettingCategory(title = stringResource(R.string.difficulty_category_ghost)) {
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

        item {
            SettingCategory(title = stringResource(R.string.difficulty_category_contract)) {
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

@Composable
private fun ActionButtons(
    modifier: Modifier = Modifier,
    hasChanges: Boolean,
    isSaving: Boolean,
    orientation: Orientation,
    onSave: () -> Unit,
    onRevert: () -> Unit,
) {
    if (!hasChanges) return

    when(orientation) {
        Orientation.Vertical -> {
            Column(
                modifier = modifier
                    .width(IntrinsicSize.Min),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onRevert,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    colors = ButtonDefaults.buttonColors(containerColor = LocalPalette.current.surfaceContainerHigh),
                    enabled = !isSaving
                ) {
                    Text(stringResource(R.string.general_label_discard), color = LocalPalette.current.onSurface)
                }

                Button(
                    onClick = onSave,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    colors = ButtonDefaults.buttonColors(containerColor = LocalPalette.current.primary),
                    enabled = !isSaving
                ) {
                    Text(stringResource(R.string.general_label_save), color = LocalPalette.current.onPrimary)
                }
            }
        }
        Orientation.Horizontal -> {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onRevert,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = LocalPalette.current.surfaceContainerHigh),
                    enabled = !isSaving
                ) {
                    Text(stringResource(R.string.general_label_discard), color = LocalPalette.current.onSurface)
                }

                Button(
                    onClick = onSave,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = LocalPalette.current.primary),
                    enabled = !isSaving
                ) {
                    Text(stringResource(R.string.general_label_save), color = LocalPalette.current.onPrimary)
                }
            }
        }
    }

}

@Composable
private fun SettingCategory(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        color = LocalPalette.current.surfaceContainerLow,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategoryHeader(title)
            content()
        }
    }
}

@Composable
private fun PresetSlotInfo(
    modifier: Modifier = Modifier,
    defaultName: String,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = LocalPalette.current.surfaceContainerLow,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = "${stringResource(R.string.general_label_preset_slot)}: ${defaultName.uppercase()}",
            style = LocalTypography.current.quaternary.bold.copy(
                fontSize = 16.sp
            ),
            color = LocalPalette.current.onSurface
        )
    }
}

@Composable
private fun LoadingState(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        InfiniteThrobber(
            color1 = LocalPalette.current.onSurface,
            color2 = LocalPalette.current.primary,
            isLoading = true
        )
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

@Composable
private fun NavigationHeader(
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {}
) {
    NavigationHeaderComposable(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 64.dp),
        leftContent = { outerModifier ->
            NavigationHeaderSideButton(
                modifier = outerModifier,
                iconContent = { iconModifier ->
                    Image(
                        modifier = iconModifier,
                        painter = painterResource(R.drawable.ic_arrow_60_left),
                        colorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
                        contentDescription = ""
                    )
                }
            ) { onLeftClick() }
        },
        rightContent = { outerModifier ->
            NavigationHeaderSideButton(
                modifier = outerModifier,
            )
        },
        centerContent = { outerModifier ->
            NavigationHeaderCenter(
                modifier = outerModifier,
                textContent = { modifier ->
                    BasicText(
                        modifier = modifier,
                        text = stringResource(R.string.general_navigation_customdifficultyeditor),
                        style = LocalTypography.current.primary.regular.copy(
                            color = LocalPalette.current.primary,
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 1,
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = 2.sp, maxFontSize = 36.sp, stepSize = 2.sp)
                    )
                }
            )
        }
    )
}
