package com.tritiumgaming.feature.settings.ui

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.core.common.util.TimeUtils
import com.tritiumgaming.core.ui.mapper.toPaletteResource
import com.tritiumgaming.core.ui.mapper.toTypographyResource
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.feature.settings.app.container.SettingsContainerProvider
import com.tritiumgaming.feature.settings.ui.components.TypographyUiState
import com.tritiumgaming.shared.data.account.model.AccountMarketPalette
import com.tritiumgaming.shared.data.account.model.AccountMarketTypography
import com.tritiumgaming.shared.data.account.model.toAccountMarketPalette
import com.tritiumgaming.shared.data.account.model.toAccountMarketTypography
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.shared.data.market.model.IncrementDirection
import com.tritiumgaming.shared.data.market.palette.mappers.LocalDefaultPalette
import com.tritiumgaming.shared.data.market.palette.mappers.asUuid
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette
import com.tritiumgaming.shared.data.market.palette.model.toAccountMarketPalette
import com.tritiumgaming.shared.data.market.palette.usecase.FetchUnlockedPalettesUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPalettesUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetNextUnlockedPaletteUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.data.market.typography.mappers.LocalDefaultTypography
import com.tritiumgaming.shared.data.market.typography.mappers.asUuid
import com.tritiumgaming.shared.data.market.typography.usecase.FetchUnlockedTypographiesUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetNextUnlockedTypographyUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SaveCurrentTypographyUseCase
import com.tritiumgaming.shared.data.policy.usecase.InitFlowPolicyUseCase
import com.tritiumgaming.shared.data.policy.usecase.IsPrivacyOptionsRequiredUseCase
import com.tritiumgaming.shared.data.policy.usecase.SetAllowAnalyticsUseCase
import com.tritiumgaming.shared.data.policy.usecase.SetAllowPersonalizedAdsUseCase
import com.tritiumgaming.shared.data.policy.usecase.ShowPrivacyOptionsFormUseCase
import com.tritiumgaming.shared.data.preferences.model.properties.DensityType
import com.tritiumgaming.shared.data.preferences.usecase.InitFlowUserPreferencesUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetAllowCellularDataUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetAllowHuntWarnAudioUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetAllowIntroductionUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetDisableScreenSaverUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetEnableGhostReorderUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetEnableRTLUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetMaxHuntWarnFlashTimeUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetUiDensityTypeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.map

class SettingsScreenViewModel(
    // Global Preferences
    private val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase,
    private val setAllowCellularDataUseCase: SetAllowCellularDataUseCase,
    private val setAllowIntroductionUseCase: SetAllowIntroductionUseCase,
    private val setDisableScreenSaverUseCase: SetDisableScreenSaverUseCase,
    private val setAllowHuntWarnAudioUseCase: SetAllowHuntWarnAudioUseCase,
    private val setEnableGhostReorderUseCase: SetEnableGhostReorderUseCase,
    private val setEnableRTLUseCase: SetEnableRTLUseCase,
    private val setUiDensityTypeUseCase: SetUiDensityTypeUseCase,
    private val setMaxHuntWarnFlashTimeUseCase: SetMaxHuntWarnFlashTimeUseCase,
    // Policy Preferences
    private val initFlowPolicyUseCase: InitFlowPolicyUseCase,
    private val setAllowAnalyticsUseCase: SetAllowAnalyticsUseCase,
    private val setAllowPersonalizedAdsUseCase: SetAllowPersonalizedAdsUseCase,
    private val isPrivacyOptionsRequiredUseCase: IsPrivacyOptionsRequiredUseCase,
    private val showPrivacyOptionsFormUseCase: ShowPrivacyOptionsFormUseCase,
    // Typographies
    private val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase,
    private val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase,
    private val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase,
    private val findNextAvailableTypographyUseCase: GetNextUnlockedTypographyUseCase,
    // Palettes
    private val observeAccountUnlockedPalettesUseCase: ObserveAccountUnlockedPalettesUseCase,
    private val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
    private val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase,
    private val findNextAvailablePaletteUseCase: GetNextUnlockedPaletteUseCase,
    // Marketplace
    private val getMarketCatalogPalettesUseCase: GetMarketCatalogPalettesUseCase,
) : ViewModel() {

    private val _userPreferencesState : StateFlow<UserPreferencesState> =
            combine(
                initFlowGlobalPreferencesUseCase(),
                initFlowPolicyUseCase()
            ) { preferences, policy ->

                val paletteUuid = preferences.paletteUuid.ifEmpty { LocalDefaultPalette.asUuid() }
                val typographyUuid =
                    preferences.typographyUuid.ifEmpty { LocalDefaultTypography.asUuid() }

                UserPreferencesState(
                    screensaverPreference = preferences.disableScreenSaver,
                    networkPreference = preferences.allowCellularData,
                    huntWarningAudioPreference = preferences.allowHuntWarnAudio,
                    ghostReorderPreference = preferences.enableGhostReorder,
                    introductionPermissionPreference = preferences.allowIntroduction,
                    rTLPreference = preferences.enableRTL,
                    huntWarnDurationPreference = preferences.maxHuntWarnFlashTime,
                    paletteUuid = paletteUuid,
                    typographyUuid = typographyUuid,
                    uiDensityType = preferences.uiDensityType,
                    analyticsPreference = policy.allowAnalytics,
                    adPrivacyPreference = policy.allowPersonalizedAds,
                    isPrivacyOptionsRequired = isPrivacyOptionsRequiredUseCase()
                )
            }
            .distinctUntilChanged()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = UserPreferencesState()
            )

    private val _settingsScreenUiState = _userPreferencesState.map { preferences ->

                SettingsScreenUiState(
                    screensaverPreference = preferences.screensaverPreference,
                    networkPreference = preferences.networkPreference,
                    huntWarningAudioPreference = preferences.huntWarningAudioPreference,
                    ghostReorderPreference = preferences.ghostReorderPreference,
                    introductionPermissionPreference = preferences.introductionPermissionPreference,
                    rTLPreference = preferences.rTLPreference,
                    huntWarnDurationPreference = preferences.huntWarnDurationPreference,
                    paletteUiState = PaletteUiState(
                        uuid = preferences.paletteUuid,
                        palette = getPaletteByUUID(preferences.paletteUuid)
                    ),
                    typographyUiState = TypographyUiState(
                        uuid = preferences.typographyUuid,
                        typography = getTypographyByUUID(preferences.typographyUuid)
                    ),
                    uiDensityType = preferences.uiDensityType,
                    analyticsPreference = preferences.analyticsPreference,
                    adPrivacyPreference = preferences.adPrivacyPreference,
                    isPrivacyOptionsRequired = preferences.isPrivacyOptionsRequired
                )
            }
            .distinctUntilChanged()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = SettingsScreenUiState()
            )
    val settingsScreenUiState = _settingsScreenUiState

    fun setScreenSaverPreference(disable: Boolean) {
        viewModelScope.launch {
            setDisableScreenSaverUseCase(disable)
        }
    }

    fun setNetworkPreference(allow: Boolean) {
        viewModelScope.launch {
            setAllowCellularDataUseCase(allow)
        }
    }

    fun setRTLPreference(enable: Boolean) {
        viewModelScope.launch {
            setEnableRTLUseCase(enable)
        }
    }

    fun setUiDensityPreference(enable: Boolean) {
        viewModelScope.launch {
            val type = if(enable) DensityType.COMPACT else DensityType.COMFORTABLE
            setUiDensityTypeUseCase(type)
        }
    }

    fun setGhostReorderPreference(allow: Boolean) {
        viewModelScope.launch {
            setEnableGhostReorderUseCase(allow)
        }
    }

    fun setIntroductionPermissionPreference(allow: Boolean) {
        viewModelScope.launch {
            setAllowIntroductionUseCase(allow)
        }
    }

    fun setHuntWarnDurationPreference(maxTime: Long) {
        val time = maxTime.coerceIn(TIME_MIN, TIME_MAX)
        viewModelScope.launch {
            setMaxHuntWarnFlashTimeUseCase(time)
        }
    }

    fun setHuntWarningAudioPreference(isAllowed: Boolean) {
        viewModelScope.launch {
            setAllowHuntWarnAudioUseCase(isAllowed)
        }
    }

    /**
     * Privacy Preferences
     */

    fun setPersonalizedAdsPreference(isAllowed: Boolean) {
        viewModelScope.launch {
            setAllowPersonalizedAdsUseCase(isAllowed)
        }
    }

    fun setAnalyticsPreference(isAllowed: Boolean) {
        viewModelScope.launch {
            setAllowAnalyticsUseCase(isAllowed)
        }
    }

    fun showPrivacyOptionsForm(activity: Activity, onFinished: () -> Unit = {}) {
        showPrivacyOptionsFormUseCase(activity, onFinished)
    }

    /**
     * Marketplace
     */

    private val _marketCatalogPalettes = MutableStateFlow(emptyList<MarketPalette>())
    private fun initMarketCatalogPalettes() {
        Log.d(TAG, "initMarketCatalogPalettes")
        viewModelScope.launch {
            getMarketCatalogPalettesUseCase()
                .onSuccess { palettes ->
                    Log.d(TAG, "initMarketCatalogPalettes success: $palettes")
                    _marketCatalogPalettes.update { palettes }
                }
                .onFailure { it.printStackTrace() }
        }
    }

    /**
     * Palettes
     */

    private val _unlockedAccountPalettesState = observeAccountUnlockedPalettesUseCase()
        .map { result ->
            result.fold(
                onSuccess = { palettes ->
                    Log.e(TAG, "Observed change to unlocked palettes: ${palettes.map{ it.uuid } }")
                    palettes.map { unlocked ->
                        unlocked.toAccountMarketPalette()
                    }
                },
                onFailure = { error ->
                    Log.e(TAG, "Error observing account unlocked palettes: $error")
                    listOf(
                        AccountMarketPalette(
                            uuid = LocalDefaultPalette.asUuid(),
                            unlocked = true
                        )
                    )
                }
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )


    private val _marketAccountPaletteState = combine(
        _marketCatalogPalettes,
        _unlockedAccountPalettesState
    ) { marketPalettes, unlockedPalettes ->
        val unlockedUUIDs = unlockedPalettes.map { it.uuid }
        unlockedUUIDs.forEach {
            Log.d(TAG, "unlockedPalette: $it")
        }

        val updatedPalettes = marketPalettes.map {
            val found = it.uuid in unlockedUUIDs
            Log.d(TAG, "marketPalette: $it | unlocked: $found")
            it.copy(
                unlocked = found
            )
        }

        updatedPalettes.toAccountMarketPalette()
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        emptyList()
    )

    private fun saveCurrentPaletteUUID(uuid: String) {
        viewModelScope.launch {
            saveCurrentPaletteUseCase(uuid)
        }
    }

    fun setNextAvailablePalette(direction: IncrementDirection) {
        viewModelScope.launch {
            try {
                val result = findNextAvailablePaletteUseCase(
                    palettes = _marketAccountPaletteState.value,
                    currentUUID = settingsScreenUiState.value.paletteUiState.uuid,
                    direction = direction
                )
                result.getOrNull()?.let { uuid ->
                    saveCurrentPaletteUUID(uuid)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getPaletteByUUID(uuid: String): ExtendedPalette {
        return try {
            getPaletteByUUIDUseCase(uuid).getOrThrow().toPaletteResource()
        } catch (e: Exception) {
            Log.e(TAG, "GetMarketCatalogPaletteByUUIDUseCase: ${e.message}. Defaulting.", e)

            val palette = LocalDefaultPalette
            palette.toPaletteResource()
        }
    }

    /**
     * Typographies
     */

    private val _unlockedTypographiesState = observeAccountUnlockedTypographiesUseCase()
        .map {
            it.getOrNull()?.map { unlocked ->
                unlocked.toAccountMarketTypography()
            } ?: emptyList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )

    private fun saveCurrentTypographyUUID(uuid: String) {
        viewModelScope.launch {
            saveCurrentTypographyUseCase(uuid)
        }
    }

    fun setNextAvailableTypography(direction: IncrementDirection) {
        viewModelScope.launch {
            try {
                val result = findNextAvailableTypographyUseCase(
                    typographies = _unlockedTypographiesState.value,
                    currentUUID = settingsScreenUiState.value.typographyUiState.uuid,
                    direction = direction
                )
                result.getOrNull()?.let { uuid ->
                    saveCurrentTypographyUUID(uuid)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getTypographyByUUID(uuid: String): ExtendedTypography {
        return try {
            getTypographyByUUIDUseCase(uuid).getOrThrow()
                .toTypographyResource()
        } catch (e: Exception) {
            Log.e(TAG, "GetMarketCatalogTypographyByUUIDUseCase: ${e.message}. Defaulting.", e)

            val typography = LocalDefaultTypography
            typography.toTypographyResource()
        }
    }

    init {
        initMarketCatalogPalettes()
    }

    companion object {
        const val TAG = "SettingsScreenViewModel"

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as SettingsContainerProvider).provideSettingsContainer()

                // Global Preferences
                val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase = container.initFlowGlobalPreferencesUseCase
                val setAllowCellularDataUseCase: SetAllowCellularDataUseCase = container.setAllowCellularDataUseCase
                val setAllowHuntWarnAudioUseCase: SetAllowHuntWarnAudioUseCase = container.setAllowHuntWarnAudioUseCase
                val setAllowIntroductionUseCase: SetAllowIntroductionUseCase = container.setAllowIntroductionUseCase
                val setDisableScreenSaverUseCase: SetDisableScreenSaverUseCase = container.setDisableScreenSaverUseCase
                val setEnableGhostReorderUseCase: SetEnableGhostReorderUseCase = container.setEnableGhostReorderUseCase
                val setEnableRTLUseCase: SetEnableRTLUseCase = container.setEnableRTLUseCase
                val setUiDensityTypeUseCase: SetUiDensityTypeUseCase = container.setUiDensityTypeUseCase
                val setMaxHuntWarnFlashTimeUseCase: SetMaxHuntWarnFlashTimeUseCase = container.setMaxHuntWarnFlashTimeUseCase
                // Policy Preferences
                val initFlowPolicyUseCase: InitFlowPolicyUseCase = container.initFlowPolicyUseCase
                val setAllowAnalyticsUseCase: SetAllowAnalyticsUseCase = container.setAllowAnalyticsUseCase
                val setAllowPersonalizedAdsUseCase: SetAllowPersonalizedAdsUseCase = container.setAllowPersonalizedAdsUseCase
                val isPrivacyOptionsRequiredUseCase: IsPrivacyOptionsRequiredUseCase = container.isPrivacyOptionsRequiredUseCase
                val showPrivacyOptionsFormUseCase: ShowPrivacyOptionsFormUseCase = container.showPrivacyOptionsFormUseCase
                // Typographies
                val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase = container.observeAccountUnlockedTypographiesUseCase
                val setCurrentTypographyUseCase: SaveCurrentTypographyUseCase = container.saveCurrentTypographyUseCase
                val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase = container.getTypographyByUUIDUseCase
                val findNextAvailableTypographyUseCase: GetNextUnlockedTypographyUseCase = container.findNextAvailableTypographyUseCase
                // Palettes
                val observeAccountUnlockedPaletteUseCase: ObserveAccountUnlockedPalettesUseCase = container.observeAccountUnlockedPalettesUseCase
                val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase = container.saveCurrentPaletteUseCase
                val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase = container.getPaletteByUUIDUseCase
                val findNextAvailablePaletteUseCase: GetNextUnlockedPaletteUseCase = container.findNextAvailablePaletteUseCase
                // Marketplace
                val getMarketCatalogPalettesUseCase: GetMarketCatalogPalettesUseCase = container.getMarketCatalogPalettesUseCase

                SettingsScreenViewModel(
                    // Global Preferences
                    initFlowGlobalPreferencesUseCase = initFlowGlobalPreferencesUseCase,
                    setAllowCellularDataUseCase = setAllowCellularDataUseCase,
                    setAllowHuntWarnAudioUseCase = setAllowHuntWarnAudioUseCase,
                    setAllowIntroductionUseCase = setAllowIntroductionUseCase,
                    setDisableScreenSaverUseCase = setDisableScreenSaverUseCase,
                    setEnableGhostReorderUseCase = setEnableGhostReorderUseCase,
                    setEnableRTLUseCase = setEnableRTLUseCase,
                    setUiDensityTypeUseCase = setUiDensityTypeUseCase,
                    setMaxHuntWarnFlashTimeUseCase = setMaxHuntWarnFlashTimeUseCase,
                    // Policy
                    initFlowPolicyUseCase = initFlowPolicyUseCase,
                    setAllowAnalyticsUseCase = setAllowAnalyticsUseCase,
                    setAllowPersonalizedAdsUseCase = setAllowPersonalizedAdsUseCase,
                    isPrivacyOptionsRequiredUseCase = isPrivacyOptionsRequiredUseCase,
                    showPrivacyOptionsFormUseCase = showPrivacyOptionsFormUseCase,
                    // Typographies
                    observeAccountUnlockedTypographiesUseCase = observeAccountUnlockedTypographiesUseCase,
                    saveCurrentTypographyUseCase = setCurrentTypographyUseCase,
                    getTypographyByUUIDUseCase = getTypographyByUUIDUseCase,
                    findNextAvailableTypographyUseCase = findNextAvailableTypographyUseCase,
                    // Palettes
                    observeAccountUnlockedPalettesUseCase = observeAccountUnlockedPaletteUseCase,
                    saveCurrentPaletteUseCase = saveCurrentPaletteUseCase,
                    getPaletteByUUIDUseCase = getPaletteByUUIDUseCase,
                    findNextAvailablePaletteUseCase = findNextAvailablePaletteUseCase,
                    // Marketplace
                    getMarketCatalogPalettesUseCase = getMarketCatalogPalettesUseCase
                )
            }
        }

        const val TIME_MIN = 0L
        const val TIME_MAX = 300000L

        const val FOREVER = 300000L

        fun percentAsTime(percent: Float, maxTime: Long = TIME_MAX): Long {
            return TimeUtils.percentAsTime(
                percent = percent, maxTime = maxTime)
        }

        fun timeAsPercent(time: Long, maxTime: Long = TIME_MAX): Float {
            return TimeUtils.timeAsPercent(
                time = time, maxTime = maxTime)
        }
    }

    private data class UserPreferencesState(
        val screensaverPreference: Boolean = false,
        val networkPreference: Boolean = true,
        val huntWarningAudioPreference: Boolean = true,
        val ghostReorderPreference: Boolean = true,
        val introductionPermissionPreference: Boolean = true,
        val rTLPreference: Boolean = true,
        val uiDensityType: DensityType = DensityType.COMFORTABLE,
        val huntWarnDurationPreference: Long = FOREVER,
        val paletteUuid: String = LocalDefaultPalette.asUuid(),
        val typographyUuid: String = LocalDefaultTypography.asUuid(),
        val analyticsPreference: Boolean = false,
        val adPrivacyPreference: Boolean = false,
        val isPrivacyOptionsRequired: Boolean? = null,
    )

}
