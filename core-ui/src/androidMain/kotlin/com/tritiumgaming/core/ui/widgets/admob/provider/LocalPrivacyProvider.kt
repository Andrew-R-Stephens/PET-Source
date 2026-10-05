package com.tritiumgaming.core.ui.widgets.admob.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun LocalPrivacyProvider(
    consentState: ConsentState,
    content: @Composable () -> Unit = {}
) {
    val rememberConsent = remember(consentState) {
        consentState
    }

    CompositionLocalProvider(
        LocalAdConsent provides rememberConsent
    ) {
        content()
    }
}

val LocalAdConsent = staticCompositionLocalOf { ConsentState() }

data class ConsentState(
    val allowPersonalizedAds: Boolean = true,
    val allowAnalytics: Boolean = true
)
