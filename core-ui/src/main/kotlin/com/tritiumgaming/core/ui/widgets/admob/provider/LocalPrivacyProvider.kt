package com.tritiumgaming.core.ui.widgets.admob.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun LocalPrivacyProvider(
    adConsent: AdConsent,
    content: @Composable () -> Unit = {}
) {
    CompositionLocalProvider(
        LocalAdConsent provides adConsent
    ) {
        content()
    }
}

val LocalAdConsent = staticCompositionLocalOf { AdConsent() }

data class AdConsent(
    val allowPersonalizedAds: Boolean = true,
    val allowAnalytics: Boolean = true
)
