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
import com.tritiumgaming.core.ui.mappers.toPaletteResource
import com.tritiumgaming.core.ui.mappers.toTypographyResource
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalDefaultPalette
import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.core.ui.theme.type.LocalDefaultTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.container.AppContainerProvider
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.setup.InitFlowGlobalPreferencesUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.setup.SetupGlobalPreferencesUseCase
import com.tritiumgaming.shared.core.domain.market.palette.model.PaletteResources.PaletteType
import com.tritiumgaming.shared.core.domain.market.palette.source.PaletteDatastore
import com.tritiumgaming.shared.core.domain.market.palette.usecase.GetPaletteByUUIDUseCase
import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyResources.TypographyType
import com.tritiumgaming.shared.core.domain.market.typography.source.TypographyDatastore
import com.tritiumgaming.shared.core.domain.market.typography.usecase.GetTypographyByUUIDUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicBoolean

class PETActivityViewModel(
    private val initGlobalPreferencesDataStoreUseCase: SetupGlobalPreferencesUseCase,
    private val initFlowGlobalPreferencesUseCase: InitFlowGlobalPreferencesUseCase,
    private val getTypographyByUUIDUseCase: GetTypographyByUUIDUseCase,
    private val getPaletteByUUIDUseCase: GetPaletteByUUIDUseCase,
): ViewModel() {

    private lateinit var _googleMobileAdsConsentManager: GoogleMobileAdsConsentManager

    /** Initialization state for the ViewModel. */
    private var _isGoogleAdsConsentManagerInitialized: Boolean = false
    private var _isMobileAdsInitializeCalled = AtomicBoolean(false)

    /** UIState for the ViewModel. */
    private val _googleAdsPermissionsUiState = MutableStateFlow(PETActivityUiState())
    private val googleAdsPermissionsUiState = _googleAdsPermissionsUiState.asStateFlow()

    /**
     * Palettes
     */
    private val _currentPaletteUUID : StateFlow<PaletteDatastore.PalettePreferences> =
        initFlowGlobalPreferencesUseCase()
            .map {
                PaletteDatastore.PalettePreferences(
                    uuid = it.paletteUuid
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = PaletteDatastore.PalettePreferences(
                    uuid = LocalDefaultPalette.uuid
                )
            )
    val currentPaletteUUID = _currentPaletteUUID

    fun getPaletteByUUID(uuid: String): ExtendedPalette =
        getPaletteByUUIDUseCase(uuid, PaletteType.CLASSIC)
            .toPaletteResource()

    /**
     * Typographies
     */
    private val _currentTypographyUUID : StateFlow<TypographyDatastore.TypographyPreferences> =
        initFlowGlobalPreferencesUseCase()
            .map {
                TypographyDatastore.TypographyPreferences(
                    uuid = it.typographyUuid
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = TypographyDatastore.TypographyPreferences(
                    uuid = LocalDefaultTypography.uuid
                )
            )
    val currentTypographyUUID = _currentTypographyUUID

    fun getTypographyByUUID(uuid: String): ExtendedTypography =
        getTypographyByUUIDUseCase(uuid, TypographyType.CLASSIC)
            .toTypographyResource()

    private fun initialDataStoreSetupEvent() {
        initGlobalPreferencesDataStoreUseCase()
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

    init {
        Log.d("GlobalPreferencesViewModel", "Initializing...")

        initialDataStoreSetupEvent()
    }

    companion object {

        // Check your logcat output for the test device hashed ID e.g.
        // "Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
        // to get test ads on this device" or
        // "Use new ConsentDebugSettings.Builder().addTestDeviceHashedId("ABCDEF012345") to set this as
        // a debug device".
        val TEST_DEVICE_HASHED_IDS = listOf (
            "00E2BE3BE3FB3298734CA8B92655E237",
            "B3C272DE5AEAB81CA9CBBCB2A928A38E",
            "35C63C64AD5C412021F7831FF07C5411",
            "4980A1A8D6C7BD9E599217DE73CD36EB"
        )

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY]
                val container = (application as AppContainerProvider).provideAppContainer()

                val setupGlobalPreferencesUseCase: SetupGlobalPreferencesUseCase = container.setupGlobalPreferencesUseCase
                val initFlowGlobalPreferencesUseCase: InitFlowGlobalPreferencesUseCase = container.initFlowGlobalPreferencesUseCase
                val getTypographyByUUIDUseCase: GetTypographyByUUIDUseCase = container.getTypographyByUUIDUseCase
                val getPaletteByUUIDUseCase: GetPaletteByUUIDUseCase = container.getPaletteByUUIDUseCase

                PETActivityViewModel(
                    initGlobalPreferencesDataStoreUseCase = setupGlobalPreferencesUseCase,
                    initFlowGlobalPreferencesUseCase = initFlowGlobalPreferencesUseCase,
                    getTypographyByUUIDUseCase = getTypographyByUUIDUseCase,
                    getPaletteByUUIDUseCase = getPaletteByUUIDUseCase,
                )
            }
        }
    }
}