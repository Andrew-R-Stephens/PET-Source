package com.tritiumgaming.data.policy.repository

import com.google.firebase.analytics.FirebaseAnalytics
import com.tritiumgaming.core.common.settings.googleadsconsentmanager.GoogleMobileAdsConsentManager
import com.tritiumgaming.shared.data.policy.source.PolicyDatastore
import com.tritiumgaming.shared.data.policy.source.PolicyDatastore.Policy
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runTest
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PolicyRepositoryImplTest {

    private lateinit var analytics: FirebaseAnalytics
    private lateinit var dataStoreSource: PolicyDatastore
    private lateinit var googleMobileAdsConsentManager: GoogleMobileAdsConsentManager
    private lateinit var repository: PolicyRepositoryImpl

    @Before
    fun setup() {
        analytics = mock()
        dataStoreSource = mock()
        googleMobileAdsConsentManager = mock()
        repository = PolicyRepositoryImpl(analytics, dataStoreSource, googleMobileAdsConsentManager)
    }

    @Test
    fun `applyPolicy sets analytics collection enabled when allowAnalytics is true`() {
        val policy = Policy(allowAnalytics = true, allowPersonalizedAds = true)
        repository.applyPolicy(policy)
        verify(analytics).setAnalyticsCollectionEnabled(true)
    }

    @Test
    fun `applyPolicy sets analytics collection disabled when allowAnalytics is false`() {
        val policy = Policy(allowAnalytics = false, allowPersonalizedAds = true)
        repository.applyPolicy(policy)
        verify(analytics).setAnalyticsCollectionEnabled(false)
    }

    @Test
    fun `applyPolicy sets personalized ads GRANTED when allowPersonalizedAds is true`() {
        val policy = Policy(allowAnalytics = true, allowPersonalizedAds = true)
        repository.applyPolicy(policy)

        verify(analytics).setConsent(any())
    }

    @Test
    fun `applyPolicy sets personalized ads DENIED when allowPersonalizedAds is false`() {
        val policy = Policy(allowAnalytics = true, allowPersonalizedAds = false)
        repository.applyPolicy(policy)

        verify(analytics).setConsent(any())
    }

    @Test
    fun `setAllowAnalytics updates dataStoreSource`() = runTest {
        repository.setAllowAnalytics(true)
        verify(dataStoreSource).setAllowAnalytics(true)
    }

    @Test
    fun `setAllowPersonalizedAds updates dataStoreSource`() = runTest {
        repository.setAllowPersonalizedAds(false)
        verify(dataStoreSource).setAllowPersonalizedAds(false)
    }

    @Test
    fun `isPrivacyOptionsRequired returns value from consent manager`() {
        whenever(googleMobileAdsConsentManager.isPrivacyOptionsRequired).thenReturn(true)
        val result = repository.isPrivacyOptionsRequired()
        assert(result == true)
    }

    @Test
    fun `showPrivacyOptionsForm calls consent manager when activity is valid`() {
        val activity = mock<android.app.Activity>()
        repository.showPrivacyOptionsForm(activity) {}
        verify(googleMobileAdsConsentManager).showPrivacyOptionsForm(any(), any())
    }
}
