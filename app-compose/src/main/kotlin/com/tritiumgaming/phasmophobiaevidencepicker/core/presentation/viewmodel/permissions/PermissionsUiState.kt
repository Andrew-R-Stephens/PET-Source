package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.permissions
data class PermissionsUiState(
    /** Represents current initialization states for the Google Mobile Ads SDK. */
    val isMobileAdsInitialized: Boolean = false,
    /** Indicates whether the app has completed the steps for gathering updated user consent. */
    val canRequestAds: Boolean = false,
    /** Indicates whether a privacy options form is required. */
    val isPrivacyOptionsRequired: Boolean = false,
)