package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.activity

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnAdInspectorClosedListener
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.ump.ConsentForm
import com.tritiumgaming.core.common.settings.googleadsconsentmanager.GoogleMobileAdsConsentManager
import com.tritiumgaming.core.ui.mapper.toPaletteResource
import com.tritiumgaming.core.ui.mapper.toTypographyResource
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalDefaultPalette
import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.core.ui.theme.type.LocalDefaultTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.container.AppContainerProvider
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.shared.data.preferences.usecase.InitFlowUserPreferencesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicBoolean

class PETActivityViewModel(
    private val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase,
    private val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase,
    private val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase,
): ViewModel() {

    private lateinit var _googleMobileAdsConsentManager: GoogleMobileAdsConsentManager

    /** Initialization state for the ViewModel. */
    private var _isGoogleAdsConsentManagerInitialized: Boolean = false
    private var _isMobileAdsInitializeCalled = AtomicBoolean(false)

    /** UIState for the ViewModel. */
    private val _googleAdsPermissionsUiState = MutableStateFlow(PETActivityUiState())
    private val googleAdsPermissionsUiState = _googleAdsPermissionsUiState.asStateFlow()

    private val _unlockedPalettes = MutableStateFlow(UnlockedPalettes())
    private val unlockedPalettes = _unlockedPalettes

    private val _unlockedTypographies = MutableStateFlow(UnlockedTypographies())
    private val unlockedTypographies = _unlockedTypographies

    private val _petActivityUiState : StateFlow<PETActivityUiState> =
        initFlowGlobalPreferencesUseCase()
            .combine(_googleAdsPermissionsUiState) {
                    preferences, googleAdsPermissionsUiState ->

                PETActivityUiState(
                    isMobileAdsInitialized = googleAdsPermissionsUiState.isMobileAdsInitialized,
                    canRequestAds = googleAdsPermissionsUiState.canRequestAds,
                    isPrivacyOptionsRequired = googleAdsPermissionsUiState.isPrivacyOptionsRequired,
                    paletteUiState = PaletteUiState(
                        uuid = preferences.paletteUuid,
                        palette = getPaletteByUUID(preferences.paletteUuid)
                    ),
                    typographyUiState = TypographyUiState(
                        uuid = preferences.typographyUuid,
                        typography = getTypographyByUUID(preferences.typographyUuid)
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = PETActivityUiState()
            )
    internal val petActivityUiState = _petActivityUiState

    private fun getPaletteByUUID(uuid: String): ExtendedPalette {
        return try {
            getPaletteByUUIDUseCase(uuid).getOrThrow().toPaletteResource()
        } catch (e: Exception) {
            e.printStackTrace()
            LocalDefaultPalette.palette
        }
    }

    private fun getTypographyByUUID(uuid: String): ExtendedTypography {
        return try {
            val result = getTypographyByUUIDUseCase(uuid).getOrThrow()
            result.toTypographyResource()
        } catch (e: Exception) {
            e.printStackTrace()
            LocalDefaultTypography.typography
        }
    }

    /** GDPR consent manager */
    fun initMobileAdsConsentManager(activity: Activity) {
        if(_isGoogleAdsConsentManagerInitialized) return

        viewModelScope.launch {
            withContext(Dispatchers.Main) {

                _isGoogleAdsConsentManagerInitialized = true
                _googleMobileAdsConsentManager = GoogleMobileAdsConsentManager.getInstance(activity)

                // Initializes the consent manager and calls the UMP SDK methods to request consent information
                // and load/show a consent form if necessary.
                gatherConsent(activity) { error ->
                    if (error != null) {
                        // Consent not obtained in current session.
                        Log.d("PermissionsViewModel", "${error.errorCode}: ${error.message}")
                    }
                    // canRequestAds can be updated when gatherConsent is completed.
                    _googleAdsPermissionsUiState.update { it.copy(
                        canRequestAds = _googleMobileAdsConsentManager.canRequestAds) }
                }
                // canRequestAds can be updated when gatherConsent is called.
                _googleAdsPermissionsUiState.update { it.copy(
                    canRequestAds = _googleMobileAdsConsentManager.canRequestAds) }

                googleAdsPermissionsUiState.collect { state ->
                    // when canRequestAds is true initializeMobileAdsSdk
                    if (state.canRequestAds) {
                        initializeMobileAdsSdk(activity)
                    } else {
                        // when canRequestAds is false, show the consent form
                        showPrivacyOptionsForm(activity) {}
                    }

                }
            }
        }

    }

    /** Initializes the Mobile Ads SDK. */
    private suspend fun initializeMobileAdsSdk(context: Context) {

        // Ensure that MobileAdsInitialize is called only once.
        if (_isMobileAdsInitializeCalled.getAndSet(true)) {
            return
        }

        // Set your test devices.
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(TEST_DEVICE_HASHED_IDS).build()
        )

        // Initialize the Google Mobile Ads SDK on a background thread.
        withContext(Dispatchers.IO) {
            MobileAds.initialize(context) { _googleAdsPermissionsUiState.update { it.copy(isMobileAdsInitialized = true) } }

            Log.d("PermissionsViewModel", "Mobile Ads SDK initialized.")
        }
    }

    /** Opens the ad inspector. */
    fun openAdInspector(context: Context, listener: OnAdInspectorClosedListener?) {
        MobileAds.openAdInspector(context) { error ->
            if (error != null) {
                val errorMessage = error.message
                Log.e("PermissionsViewModel", errorMessage)
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
            // Notify listener of ad inspector closed.
            listener?.onAdInspectorClosed(error)
        }
    }

    /** Calls the UMP SDK method to show the privacy options form. */
    private fun showPrivacyOptionsForm(
        activity: Activity,
        onConsentFormDismissedListener: ConsentForm.OnConsentFormDismissedListener?,
    ) {
        _googleMobileAdsConsentManager.showPrivacyOptionsForm(activity) { error ->
            if (error != null) {
                val errorMessage = error.message
                Log.e("PermissionsViewModel", errorMessage)
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
            }
            // Notify listener of consent form dismissal.
            onConsentFormDismissedListener?.onConsentFormDismissed(error)
        }
    }

    /** Calls the UMP SDK methods to gatherConsent. */
    private fun gatherConsent(
        activity: Activity,
        onConsentGatheringCompleteListener:
        GoogleMobileAdsConsentManager.OnConsentGatheringCompleteListener,
    ) {
        _googleMobileAdsConsentManager.gatherConsent(activity) { error ->
            // Update UIState and notify listener of updated consent status.
            _googleAdsPermissionsUiState.update {
                Log.d("PermissionsViewModel", "Consent gathering from current session complete. " +
                        "${_googleMobileAdsConsentManager.canRequestAds} / " +
                        "${_googleMobileAdsConsentManager.isPrivacyOptionsRequired}")
                it.copy(
                    canRequestAds = _googleMobileAdsConsentManager.canRequestAds,
                    isPrivacyOptionsRequired = _googleMobileAdsConsentManager.isPrivacyOptionsRequired,
                )
            }
            onConsentGatheringCompleteListener.consentGatheringComplete(error)
        }
        // Update UIState based on consent obtained in the previous session.
        _googleAdsPermissionsUiState.update {
            Log.d("PermissionsViewModel", "Consent gathering from previous session complete. " +
                    "${_googleMobileAdsConsentManager.canRequestAds} / " +
                    "${_googleMobileAdsConsentManager.isPrivacyOptionsRequired}")
            it.copy(
                canRequestAds = _googleMobileAdsConsentManager.canRequestAds,
                isPrivacyOptionsRequired = _googleMobileAdsConsentManager.isPrivacyOptionsRequired,
            )
        }
    }

    companion object {

        // Check your logcat output for the test device hashed ID e.g.
        // "Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
        // to get test ads on this device" or
        // "Use new ConsentDebugSettings.Builder().addTestDeviceHashedId("ABCDEF012345") to set
        // this as a debug device".
        val TEST_DEVICE_HASHED_IDS = listOf (
            "00E2BE3BE3FB3298734CA8B92655E237",
            "B3C272DE5AEAB81CA9CBBCB2A928A38E",
            "35C63C64AD5C412021F7831FF07C5411",
            "4980A1A8D6C7BD9E599217DE73CD36EB"
        )

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as AppContainerProvider).provideAppContainer()

                val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase = container.initFlowGlobalPreferencesUseCase
                val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase = container.getTypographyByUUIDUseCase
                val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase = container.getPaletteByUUIDUseCase

                PETActivityViewModel(
                    initFlowGlobalPreferencesUseCase = initFlowGlobalPreferencesUseCase,
                    getTypographyByUUIDUseCase = getTypographyByUUIDUseCase,
                    getPaletteByUUIDUseCase = getPaletteByUUIDUseCase,
                )
            }
        }
    }
}