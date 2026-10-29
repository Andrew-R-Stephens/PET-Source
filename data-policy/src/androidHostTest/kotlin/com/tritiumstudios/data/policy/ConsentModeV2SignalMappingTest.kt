package com.tritiumstudios.data.policy

import com.tritiumstudios.data.policy.source.PolicyDatastore.Policy
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Unit tests verifying Google Consent Mode v2 signal mappings under GDPR and CCPA/CPRA requirements.
 *
 * Consent Mode v2 introduces 4 primary consent signals:
 * - ANALYTICS_STORAGE (analytics_storage)
 * - AD_STORAGE (ad_storage)
 * - AD_USER_DATA (ad_user_data)
 * - AD_PERSONALIZATION (ad_personalization)
 *
 * Advanced Consent Mode v2 requires that analytics collection remains enabled so cookieless / anonymous
 * pings (modeled data) are sent when consent is DENIED, while full measurement and personalized ads
 * are enabled when consent is GRANTED.
 */
class ConsentModeV2SignalMappingTest {

    enum class ConsentStatus {
        GRANTED,
        DENIED
    }

    data class ConsentModeV2Signals(
        val analyticsStorage: ConsentStatus,
        val adStorage: ConsentStatus,
        val adUserData: ConsentStatus,
        val adPersonalization: ConsentStatus,
        val analyticsCollectionEnabled: Boolean = true // Advanced Consent Mode v2 requirement
    )

    /**
     * Maps a [Policy] domain object to Google Analytics / Firebase Analytics Consent Mode v2 signals.
     * Replicates the mapping logic used by PolicyRepository.
     */
    private fun mapPolicyToConsentSignals(policy: Policy): ConsentModeV2Signals {
        val analyticsStatus = if (policy.allowAnalytics) ConsentStatus.GRANTED else ConsentStatus.DENIED
        val adsStatus = if (policy.allowPersonalizedAds) ConsentStatus.GRANTED else ConsentStatus.DENIED

        return ConsentModeV2Signals(
            analyticsStorage = analyticsStatus,
            adStorage = adsStatus,
            adUserData = adsStatus,
            adPersonalization = adsStatus,
            // To support Advanced Consent Mode v2, collection MUST remain enabled (sending cookieless pings when denied)
            analyticsCollectionEnabled = true
        )
    }

    /**
     * Evaluates string manifest metadata values (e.g., "eu_consent_policy" or boolean strings).
     */
    private fun parseMetadataBoolean(rawValue: Any?, defaultValue: Boolean): Boolean {
        if (rawValue == null) return defaultValue
        return when (rawValue) {
            is Boolean -> rawValue
            is String -> {
                if (rawValue == "eu_consent_policy") {
                    // Under EU Consent Policy / GDPR rules, default value is non-explicit (false)
                    false
                } else {
                    rawValue.toBoolean()
                }
            }
            else -> defaultValue
        }
    }

    // ============================================================================================
    // GDPR COMPLIANCE TESTS
    // ============================================================================================

    @Test
    fun `GDPR Default State - no explicit consent - all signals DENIED with Advanced Consent Mode active`() {
        // Under GDPR, default state before explicit user consent must have all storage/ad flags DENIED
        val defaultPolicy = Policy(
            allowAnalytics = false,
            allowPersonalizedAds = false,
            hasExplicitAnalyticsConsent = false
        )

        val signals = mapPolicyToConsentSignals(defaultPolicy)

        assertEquals(ConsentStatus.DENIED, signals.analyticsStorage, "Analytics storage must be DENIED by default under GDPR")
        assertEquals(ConsentStatus.DENIED, signals.adStorage, "Ad storage must be DENIED by default under GDPR")
        assertEquals(ConsentStatus.DENIED, signals.adUserData, "Ad user data must be DENIED by default under GDPR")
        assertEquals(ConsentStatus.DENIED, signals.adPersonalization, "Ad personalization must be DENIED by default under GDPR")
        assertTrue(signals.analyticsCollectionEnabled, "Advanced Consent Mode v2 requires collection to remain enabled for cookieless pings")
        assertFalse(defaultPolicy.hasExplicitAnalyticsConsent, "Explicit consent flag must be false prior to user action")
    }

    @Test
    fun `GDPR Opt-In - user accepts all - all signals GRANTED`() {
        // User clicks "Accept All" in GDPR UMP form
        val optInPolicy = Policy(
            allowAnalytics = true,
            allowPersonalizedAds = true,
            hasExplicitAnalyticsConsent = true
        )

        val signals = mapPolicyToConsentSignals(optInPolicy)

        assertEquals(ConsentStatus.GRANTED, signals.analyticsStorage)
        assertEquals(ConsentStatus.GRANTED, signals.adStorage)
        assertEquals(ConsentStatus.GRANTED, signals.adUserData)
        assertEquals(ConsentStatus.GRANTED, signals.adPersonalization)
        assertTrue(signals.analyticsCollectionEnabled)
        assertTrue(optInPolicy.hasExplicitAnalyticsConsent)
    }

    @Test
    fun `GDPR Partial Opt-In - user accepts analytics but rejects personalized ads`() {
        val partialPolicy = Policy(
            allowAnalytics = true,
            allowPersonalizedAds = false,
            hasExplicitAnalyticsConsent = true
        )

        val signals = mapPolicyToConsentSignals(partialPolicy)

        assertEquals(ConsentStatus.GRANTED, signals.analyticsStorage, "Analytics storage granted")
        assertEquals(ConsentStatus.DENIED, signals.adStorage, "Ad storage denied")
        assertEquals(ConsentStatus.DENIED, signals.adUserData, "Ad user data denied")
        assertEquals(ConsentStatus.DENIED, signals.adPersonalization, "Ad personalization denied")
        assertTrue(signals.analyticsCollectionEnabled)
    }

    @Test
    fun `GDPR Full Rejection - user denies all - signals DENIED with Advanced pings enabled`() {
        val rejectionPolicy = Policy(
            allowAnalytics = false,
            allowPersonalizedAds = false,
            hasExplicitAnalyticsConsent = true
        )

        val signals = mapPolicyToConsentSignals(rejectionPolicy)

        assertEquals(ConsentStatus.DENIED, signals.analyticsStorage)
        assertEquals(ConsentStatus.DENIED, signals.adStorage)
        assertEquals(ConsentStatus.DENIED, signals.adUserData)
        assertEquals(ConsentStatus.DENIED, signals.adPersonalization)
        assertTrue(signals.analyticsCollectionEnabled, "Advanced Consent Mode v2 active for cookieless modeling")
    }

    // ============================================================================================
    // CCPA / CPRA COMPLIANCE TESTS
    // ============================================================================================

    @Test
    fun `CCPA Opt-Out of Sale and Targeted Ads - analytics GRANTED but ad targeting signals DENIED`() {
        // Under CCPA/CPRA (California), users may opt out of sale/sharing & targeted advertising.
        // First-party app measurement analytics may remain allowed while ad targeting signals are DENIED.
        val ccpaOptOutPolicy = Policy(
            allowAnalytics = true,
            allowPersonalizedAds = false, // Opted out under "Do Not Sell or Share My Personal Information"
            hasExplicitAnalyticsConsent = true
        )

        val signals = mapPolicyToConsentSignals(ccpaOptOutPolicy)

        assertEquals(ConsentStatus.GRANTED, signals.analyticsStorage, "CCPA allows analytics measurement")
        assertEquals(ConsentStatus.DENIED, signals.adStorage, "CCPA opt-out denies ad storage")
        assertEquals(ConsentStatus.DENIED, signals.adUserData, "CCPA opt-out denies sharing user data with Google for ad targeting")
        assertEquals(ConsentStatus.DENIED, signals.adPersonalization, "CCPA opt-out denies ad personalization signals")
        assertTrue(signals.analyticsCollectionEnabled)
    }

    @Test
    fun `CCPA Full Opt-Out - user opts out of both analytics and personalized advertising`() {
        val ccpaFullOptOutPolicy = Policy(
            allowAnalytics = false,
            allowPersonalizedAds = false,
            hasExplicitAnalyticsConsent = true
        )

        val signals = mapPolicyToConsentSignals(ccpaFullOptOutPolicy)

        assertEquals(ConsentStatus.DENIED, signals.analyticsStorage)
        assertEquals(ConsentStatus.DENIED, signals.adStorage)
        assertEquals(ConsentStatus.DENIED, signals.adUserData)
        assertEquals(ConsentStatus.DENIED, signals.adPersonalization)
        assertTrue(signals.analyticsCollectionEnabled)
    }

    // ============================================================================================
    // MANIFEST METADATA DEFAULT EVALUATION TESTS
    // ============================================================================================

    @Test
    fun `Manifest MetaData - eu_consent_policy string resolves to false by default`() {
        val parsedAnalytics = parseMetadataBoolean("false", false)
        val parsedAdStorage = parseMetadataBoolean("false", false)
        val parsedAdUserData = parseMetadataBoolean("eu_consent_policy", false)
        val parsedAdPersonalization = parseMetadataBoolean("eu_consent_policy", false)

        assertFalse(parsedAnalytics, "analytics_storage metadata default must be false")
        assertFalse(parsedAdStorage, "ad_storage metadata default must be false")
        assertFalse(parsedAdUserData, "eu_consent_policy for ad_user_data must evaluate to false")
        assertFalse(parsedAdPersonalization, "eu_consent_policy for ad_personalization must evaluate to false")
    }

    @Test
    fun `Manifest MetaData - boolean string true resolves to true`() {
        val parsedValue = parseMetadataBoolean("true", false)
        assertTrue(parsedValue)
    }

    @Test
    fun `Manifest MetaData - null value fallback to default`() {
        val parsedValue = parseMetadataBoolean(null, true)
        assertTrue(parsedValue)
    }
}
