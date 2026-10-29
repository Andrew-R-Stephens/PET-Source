package com.tritiumstudios.data.policy

import com.tritiumstudios.data.policy.source.PolicyDatastore.Policy
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Unit tests verifying [Policy] state immutability, copy functionality, and default initial values.
 */
class PolicyDatastoreTest {

    @Test
    fun `Policy default constructor - defaults to non-explicit consent with all flags false`() {
        val policy = Policy()

        assertFalse(policy.allowAnalytics, "Default policy must not allow analytics before consent")
        assertFalse(policy.allowPersonalizedAds, "Default policy must not allow personalized ads before consent")
        assertFalse(policy.hasExplicitAnalyticsConsent, "Default policy must have false for explicit consent")
    }

    @Test
    fun `Policy copy with GDPR Opt-In updates all fields correctly`() {
        val initialPolicy = Policy()

        val updatedPolicy = initialPolicy.copy(
            allowAnalytics = true,
            allowPersonalizedAds = true,
            hasExplicitAnalyticsConsent = true
        )

        assertTrue(updatedPolicy.allowAnalytics)
        assertTrue(updatedPolicy.allowPersonalizedAds)
        assertTrue(updatedPolicy.hasExplicitAnalyticsConsent)
    }

    @Test
    fun `Policy copy with CCPA Opt-Out disables personalized ads while keeping analytics`() {
        val initialPolicy = Policy()

        val ccpaPolicy = initialPolicy.copy(
            allowAnalytics = true,
            allowPersonalizedAds = false, // CCPA Opt-out of sale/sharing/targeted ads
            hasExplicitAnalyticsConsent = true
        )

        assertTrue(ccpaPolicy.allowAnalytics)
        assertFalse(ccpaPolicy.allowPersonalizedAds)
        assertTrue(ccpaPolicy.hasExplicitAnalyticsConsent)
    }

    @Test
    fun `Policy equality checks`() {
        val policy1 = Policy(allowAnalytics = true, allowPersonalizedAds = false, hasExplicitAnalyticsConsent = true)
        val policy2 = Policy(allowAnalytics = true, allowPersonalizedAds = false, hasExplicitAnalyticsConsent = true)
        val policy3 = Policy(allowAnalytics = false, allowPersonalizedAds = false, hasExplicitAnalyticsConsent = true)

        assertEquals(policy1, policy2)
        assertFalse(policy1 == policy3)
    }
}
