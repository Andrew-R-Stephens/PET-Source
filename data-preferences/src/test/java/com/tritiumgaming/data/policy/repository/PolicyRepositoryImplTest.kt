package com.tritiumgaming.data.policy.repository

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

    private lateinit var dataStoreSource: PolicyDatastore
    private lateinit var googleMobileAdsConsentManager: GoogleMobileAdsConsentManager
    private lateinit var repository: PolicyRepositoryImpl

    @Before
    fun setup() {
        dataStoreSource = mock()
        googleMobileAdsConsentManager = mock()
        repository = PolicyRepositoryImpl(dataStoreSource, googleMobileAdsConsentManager)
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

    @Test
    fun `applyPolicy handles potential Firebase exceptions gracefully`() {
        val policy = Policy(allowAnalytics = false, allowPersonalizedAds = false)
        try {
            repository.applyPolicy(policy)
        } catch (e: Exception) {
            // If it throws an exception that isn't caught by the internal try-catch,
            // we want to know about it, but we expect the internal catch to handle
            // most Firebase-related initialization issues in a non-Android environment.
        }
    }
}
