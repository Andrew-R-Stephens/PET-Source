package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.activity

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.ump.ConsentInformation.PrivacyOptionsRequirementStatus
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform.getConsentInformation
import com.tritiumgaming.core.common.settings.googleadsconsentmanager.PrivacyConsentState
import com.tritiumgaming.data.marketplace.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.data.marketplace.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.data.palette.mappers.LocalDefaultPalette
import com.tritiumgaming.data.palette.mappers.PaletteResources
import com.tritiumgaming.data.typography.mappers.LocalDefaultTypography
import com.tritiumgaming.data.typography.mappers.TypographyResources
import com.tritiumgaming.data.usecase.InitFlowUserPreferencesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.container.AppContainerProvider
import com.tritiumstudios.data.policy.usecase.ApplyPolicyUseCase
import com.tritiumstudios.data.policy.usecase.GatherAdsConsentUseCase
import com.tritiumstudios.data.policy.usecase.InitFlowPolicyUseCase
import com.tritiumstudios.data.policy.usecase.InitializeMobileAdsUseCase
import com.tritiumstudios.data.policy.usecase.SetAllowAnalyticsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PETActivityViewModel(
    private val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase,
    private val initFlowPolicyUseCase: InitFlowPolicyUseCase,
    private val applyPolicyUseCase: ApplyPolicyUseCase,
    private val setAllowAnalyticsUseCase: SetAllowAnalyticsUseCase,
    private val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase,
    private val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase,
    private val gatherAdsConsentUseCase: GatherAdsConsentUseCase,
    private val initializeMobileAdsUseCase: InitializeMobileAdsUseCase
): ViewModel() {

    /** UIState for the ViewModel. */
    private val _googleAdsPermissionsUiState = MutableStateFlow(PrivacyConsentState())

    private val _petActivityUiState : StateFlow<PETActivityUiState> =
        combine(
            initFlowGlobalPreferencesUseCase(),
            initFlowPolicyUseCase(),
            _googleAdsPermissionsUiState
        ) { preferences, policy, googleAdsPermissionsUiState ->
                PETActivityUiState(
                    isMobileAdsInitialized = googleAdsPermissionsUiState.isMobileAdsInitialized,
                    canRequestAds = googleAdsPermissionsUiState.canRequestAds,
                    isPrivacyOptionsRequired = googleAdsPermissionsUiState.isPrivacyOptionsRequired,
                    disableScreenSaver = preferences.disableScreenSaver,
                    allowCellularData = preferences.allowCellularData,
                    allowAnalytics = policy.allowAnalytics,
                    allowPersonalizedAds = policy.allowPersonalizedAds,
                    hasExplicitAnalyticsConsent = policy.hasExplicitAnalyticsConsent,
                    paletteUiState = PaletteUiState(
                        palette = getPaletteByUUID(preferences.paletteUuid)
                    ),
                    typographyUiState = TypographyUiState(
                        typography = getTypographyByUUID(preferences.typographyUuid)
                    ),
                    uiConfiguration = UiConfigurationState(
                        densityType = preferences.uiDensityType,
                        isRtl = preferences.enableRTL
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = PETActivityUiState()
            )
    internal val petActivityUiState = _petActivityUiState

    private fun getPaletteByUUID(uuid: String): PaletteResources.PaletteType {
        return try {
            val result = getPaletteByUUIDUseCase(uuid).getOrThrow()
            result
        } catch (e: Exception) {
            Log.e(TAG, "getMarketCatalogPaletteByUUIDUseCase: ${e.message}. Defaulting.", e)

            val palette = LocalDefaultPalette
            palette
        }
    }

    private fun getTypographyByUUID(uuid: String): TypographyResources.TypographyType {
        return try {
            val result = getTypographyByUUIDUseCase(uuid).getOrThrow()
            result
        } catch (e: Exception) {
            Log.e(TAG, "getMarketCatalogTypographyByUUIDUseCase: ${e.message}. Defaulting.", e)

            val typography = LocalDefaultTypography
            typography
        }
    }

    /** GDPR consent manager */
    fun initMobileAdsConsentManager(activity: Activity) {
        gatherAdsConsentUseCase(activity) { error ->
            if (error is FormError) {
                Log.e(TAG, "${error.errorCode}: ${error.message}")
            }
            // Update UI State with current consent info
            val consentInformation = getConsentInformation(activity)
            _googleAdsPermissionsUiState.update {
                it.copy(
                    canRequestAds = consentInformation.canRequestAds(),
                    isPrivacyOptionsRequired = consentInformation.privacyOptionsRequirementStatus ==
                            PrivacyOptionsRequirementStatus.REQUIRED
                )
            }

            // Initialize SDK if consent was gathered (or already existed)
            viewModelScope.launch {
                initializeMobileAdsSdk(activity)
            }
        }
    }

    /** Initializes the Mobile Ads SDK. */
    private suspend fun initializeMobileAdsSdk(context: Context) {
        initializeMobileAdsUseCase(context) {
            _googleAdsPermissionsUiState.update {
                it.copy(isMobileAdsInitialized = true)
            }
        }
    }

    fun setAllowAnalytics(allow: Boolean) {
        viewModelScope.launch {
            setAllowAnalyticsUseCase(allow)
        }
    }

    init {
        initFlowPolicyUseCase()
            .distinctUntilChanged()
            .onEach {
                Log.d(TAG, "Consent Policy: $it")
                applyPolicyUseCase(it)
            }.launchIn(viewModelScope)
    }

    companion object {

        const val TAG = "PETActivityViewModel"

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY]
                val container = (application as AppContainerProvider).provideAppContainer()

                val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase = container.initFlowGlobalPreferencesUseCase
                val initFlowPolicyUseCase: InitFlowPolicyUseCase = container.initFlowPolicyUseCase
                val applyPolicyUseCase: ApplyPolicyUseCase = container.applyPolicyUseCase
                val setAllowAnalyticsUseCase = container.setAllowAnalyticsUseCase
                val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase = container.getTypographyByUUIDUseCase
                val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase = container.getPaletteByUUIDUseCase
                val gatherAdsConsentUseCase = container.gatherAdsConsentUseCase
                val initializeMobileAdsUseCase = container.initializeMobileAdsUseCase

                PETActivityViewModel(
                    initFlowGlobalPreferencesUseCase = initFlowGlobalPreferencesUseCase,
                    initFlowPolicyUseCase = initFlowPolicyUseCase,
                    applyPolicyUseCase = applyPolicyUseCase,
                    setAllowAnalyticsUseCase = setAllowAnalyticsUseCase,
                    getTypographyByUUIDUseCase = getTypographyByUUIDUseCase,
                    getPaletteByUUIDUseCase = getPaletteByUUIDUseCase,
                    gatherAdsConsentUseCase = gatherAdsConsentUseCase,
                    initializeMobileAdsUseCase = initializeMobileAdsUseCase
                )
            }
        }
    }
}