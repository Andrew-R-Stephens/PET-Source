import com.tritiumgaming.data.marketplace.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.data.marketplace.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.data.source.GlobalPreferencesDatastore.GlobalPreferences
import com.tritiumgaming.data.usecase.InitFlowUserPreferencesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.ui.activity.PETActivityViewModel
import com.tritiumstudios.data.policy.source.PolicyDatastore.Policy
import com.tritiumstudios.data.policy.usecase.ApplyPolicyUseCase
import com.tritiumstudios.data.policy.usecase.GatherAdsConsentUseCase
import com.tritiumstudios.data.policy.usecase.InitFlowPolicyUseCase
import com.tritiumstudios.data.policy.usecase.InitializeMobileAdsUseCase
import com.tritiumstudios.data.policy.usecase.SetAllowAnalyticsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import com.tritiumgaming.data.palette.mappers.LocalDefaultPalette
import com.tritiumgaming.data.typography.mappers.LocalDefaultTypography
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher

/**
 * Unit tests for [PETActivityViewModel] focusing on Advanced Consent Mode v2, GDPR, and CCPA/CPRA consent flows.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class PETActivityViewModelConsentTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase = mock()
    private val initFlowPolicyUseCase: InitFlowPolicyUseCase = mock()
    private val applyPolicyUseCase: ApplyPolicyUseCase = mock()
    private val setAllowAnalyticsUseCase: SetAllowAnalyticsUseCase = mock()
    private val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase = mock()
    private val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase = mock()
    private val gatherAdsConsentUseCase: GatherAdsConsentUseCase = mock()
    private val initializeMobileAdsUseCase: InitializeMobileAdsUseCase = mock()

    private val preferencesFlow = MutableStateFlow(GlobalPreferences())
    private val policyFlow = MutableStateFlow(Policy())

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        whenever(initFlowGlobalPreferencesUseCase()).thenReturn(preferencesFlow)
        whenever(initFlowPolicyUseCase()).thenReturn(policyFlow)
        whenever(getPaletteByUUIDUseCase(any())).thenReturn(Result.success(LocalDefaultPalette))
        whenever(getTypographyByUUIDUseCase(any())).thenReturn(Result.success(LocalDefaultTypography))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testDefaultPolicyState_reflectsGdprPrivacyDefaults() = runTest {
        val defaultPolicy = Policy(
            allowAnalytics = false,
            allowPersonalizedAds = false,
            hasExplicitAnalyticsConsent = false
        )
        policyFlow.value = defaultPolicy

        val viewModel = createViewModel()
        backgroundScope.launch(testDispatcher) { viewModel.petActivityUiState.collect {} }

        val uiState = viewModel.petActivityUiState.value

        assertFalse("Analytics must default to false under GDPR", uiState.allowAnalytics)
        assertFalse("Personalized ads must default to false under GDPR", uiState.allowPersonalizedAds)
        assertFalse("Explicit consent must be false before user selection", uiState.hasExplicitAnalyticsConsent)
    }

    @Test
    fun testGdprConsentGranted_uiStateUpdatesAllConsentFlagsToTrue() = runTest {
        val viewModel = createViewModel()
        backgroundScope.launch(testDispatcher) { viewModel.petActivityUiState.collect {} }

        val optInPolicy = Policy(
            allowAnalytics = true,
            allowPersonalizedAds = true,
            hasExplicitAnalyticsConsent = true
        )
        policyFlow.value = optInPolicy

        val uiState = viewModel.petActivityUiState.value

        assertTrue(uiState.allowAnalytics)
        assertTrue(uiState.allowPersonalizedAds)
        assertTrue(uiState.hasExplicitAnalyticsConsent)
        verify(applyPolicyUseCase).invoke(optInPolicy)
    }

    @Test
    fun testCcpaOptOutState_analyticsAllowed_personalizedAdsDisabled() = runTest {
        val viewModel = createViewModel()
        backgroundScope.launch(testDispatcher) { viewModel.petActivityUiState.collect {} }

        val ccpaOptOutPolicy = Policy(
            allowAnalytics = true,
            allowPersonalizedAds = false, // Opted out under CCPA
            hasExplicitAnalyticsConsent = true
        )
        policyFlow.value = ccpaOptOutPolicy

        val uiState = viewModel.petActivityUiState.value

        assertTrue("Analytics permitted in CCPA opt-out mode", uiState.allowAnalytics)
        assertFalse("Personalized ads disabled in CCPA opt-out mode", uiState.allowPersonalizedAds)
        assertTrue(uiState.hasExplicitAnalyticsConsent)
        verify(applyPolicyUseCase).invoke(ccpaOptOutPolicy)
    }

    @Test
    fun testSetAllowAnalytics_invokesSetAllowAnalyticsUseCase() = runTest {
        val viewModel = createViewModel()
        backgroundScope.launch(testDispatcher) { viewModel.petActivityUiState.collect {} }

        viewModel.setAllowAnalytics(true)

        verify(setAllowAnalyticsUseCase).invoke(true)
    }

    private fun createViewModel(): PETActivityViewModel {
        return PETActivityViewModel(
            initFlowGlobalPreferencesUseCase = initFlowGlobalPreferencesUseCase,
            initFlowPolicyUseCase = initFlowPolicyUseCase,
            applyPolicyUseCase = applyPolicyUseCase,
            setAllowAnalyticsUseCase = setAllowAnalyticsUseCase,
            getTypographyByUUIDUseCase = getTypographyByUUIDUseCase,
            getPaletteByUUIDUseCase = getPaletteByUUIDUseCase,
            gatherAdsConsentUseCase = gatherAdsConsentUseCase,
            initializeMobileAdsUseCase = initializeMobileAdsUseCase
        )
    }
}
