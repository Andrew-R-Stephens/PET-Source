package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.activity

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnAdInspectorClosedListener
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation.PrivacyOptionsRequirementStatus
import com.tritiumgaming.core.common.settings.googleadsconsentmanager.GoogleAdsConsentState
import com.tritiumgaming.phasmophobiaevidencepicker.core.container.AppContainerProvider
import com.tritiumgaming.shared.data.market.palette.mappers.LocalDefaultPalette
import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.market.typography.mappers.LocalDefaultTypography
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.shared.data.policy.usecase.ApplyPolicyUseCase
import com.tritiumgaming.shared.data.policy.usecase.GatherAdsConsentUseCase
import com.tritiumgaming.shared.data.policy.usecase.InitFlowPolicyUseCase
import com.tritiumgaming.shared.data.policy.usecase.InitializeMobileAdsUseCase
import com.tritiumgaming.shared.data.preferences.usecase.InitFlowUserPreferencesUseCase
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
    private val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase,
    private val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase,
    private val gatherAdsConsentUseCase: GatherAdsConsentUseCase,
    private val initializeMobileAdsUseCase: InitializeMobileAdsUseCase
): ViewModel() {

    /** UIState for the ViewModel. */
    private val _googleAdsPermissionsUiState = MutableStateFlow(GoogleAdsConsentState())

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
            e.printStackTrace()
            LocalDefaultPalette
        }
    }

    private fun getTypographyByUUID(uuid: String): TypographyResources.TypographyType {
        return try {
            val result = getTypographyByUUIDUseCase(uuid).getOrThrow()
            result
        } catch (e: Exception) {
            e.printStackTrace()
            LocalDefaultTypography
        }
    }

    /** GDPR consent manager */
    fun initMobileAdsConsentManager(activity: Activity) {
        gatherAdsConsentUseCase(activity) { error ->
            if (error is com.google.android.ump.FormError) {
                Log.d("PETActivityViewModel", "${error.errorCode}: ${error.message}")
            }
            // Update UI State with current consent info
            val consentInformation = com.google.android.ump.UserMessagingPlatform.getConsentInformation(activity)
            _googleAdsPermissionsUiState.update { it.copy(
                canRequestAds = consentInformation.canRequestAds(),
                isPrivacyOptionsRequired = consentInformation.privacyOptionsRequirementStatus == PrivacyOptionsRequirementStatus.REQUIRED
            ) }

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

    init {
        initFlowPolicyUseCase()
            .distinctUntilChanged()
            .onEach {
                Log.d("PETActivityViewModel", "Consent Policy: $it")
                applyPolicyUseCase(it)
            }.launchIn(viewModelScope)
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as AppContainerProvider).provideAppContainer()

                val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase = container.initFlowGlobalPreferencesUseCase
                val initFlowPolicyUseCase: InitFlowPolicyUseCase = container.initFlowPolicyUseCase
                val applyPolicyUseCase: ApplyPolicyUseCase = container.applyPolicyUseCase
                val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase = container.getTypographyByUUIDUseCase
                val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase = container.getPaletteByUUIDUseCase
                val gatherAdsConsentUseCase = container.gatherAdsConsentUseCase
                val initializeMobileAdsUseCase = container.initializeMobileAdsUseCase

                PETActivityViewModel(
                    initFlowGlobalPreferencesUseCase = initFlowGlobalPreferencesUseCase,
                    initFlowPolicyUseCase = initFlowPolicyUseCase,
                    applyPolicyUseCase = applyPolicyUseCase,
                    getTypographyByUUIDUseCase = getTypographyByUUIDUseCase,
                    getPaletteByUUIDUseCase = getPaletteByUUIDUseCase,
                    gatherAdsConsentUseCase = gatherAdsConsentUseCase,
                    initializeMobileAdsUseCase = initializeMobileAdsUseCase
                )
            }
        }
    }
}