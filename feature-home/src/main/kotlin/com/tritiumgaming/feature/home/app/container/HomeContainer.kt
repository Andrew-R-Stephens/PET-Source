package com.tritiumgaming.feature.home.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.core.common.network.ConnectivityManagerHelper
import com.tritiumgaming.core.domain.market.user.usecase.DeactivateAccountUseCase
import com.tritiumgaming.core.domain.market.user.usecase.GetSignInCredentialsUseCase
import com.tritiumgaming.core.domain.market.user.usecase.SignInAccountUseCase
import com.tritiumgaming.core.domain.market.user.usecase.SignOutAccountUseCase
import com.tritiumgaming.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.data.contributor.repository.ContributorRepository
import com.tritiumgaming.data.contributor.repository.ContributorRepositoryImpl
import com.tritiumgaming.data.contributor.source.ContributorDataSource
import com.tritiumgaming.data.contributor.source.local.ContributorLocalDataSource
import com.tritiumgaming.data.contributor.usecase.ContributorsUseCase
import com.tritiumgaming.data.language.usecase.GetAvailableLanguagesUseCase
import com.tritiumgaming.data.language.usecase.GetDefaultLanguageUseCase
import com.tritiumgaming.data.language.usecase.InitFlowLanguageUseCase
import com.tritiumgaming.data.language.usecase.SaveCurrentLanguageUseCase
import com.tritiumgaming.data.language.usecase.SetDefaultLanguageUseCase
import com.tritiumgaming.data.marketplace.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.data.marketplace.palette.usecase.GetMarketCatalogPalettesUseCase
import com.tritiumgaming.data.marketplace.palette.usecase.GetNextUnlockedPaletteUseCase
import com.tritiumgaming.data.marketplace.typography.usecase.GetMarketCatalogTypographiesUseCase
import com.tritiumgaming.data.marketplace.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.data.marketplace.typography.usecase.GetNextUnlockedTypographyUseCase
import com.tritiumgaming.data.newsletter.repository.NewsletterRepository
import com.tritiumgaming.data.newsletter.repository.NewsletterRepositoryImpl
import com.tritiumgaming.data.newsletter.source.datastore.NewsletterDatastoreDataSource
import com.tritiumgaming.data.newsletter.source.local.NewsletterLocalDataSource
import com.tritiumgaming.data.newsletter.source.local.NewsletterLocalDataSourceImpl
import com.tritiumgaming.data.newsletter.source.remote.NewsletterRemoteDataSource
import com.tritiumgaming.data.newsletter.source.remote.NewsletterRemoteDataSourceImpl
import com.tritiumgaming.data.newsletter.source.remote.api.NewsletterService
import com.tritiumgaming.data.newsletter.usecase.FetchNewsletterInboxesUseCase
import com.tritiumgaming.data.newsletter.usecase.GetFlowNewsletterDatastoreUseCase
import com.tritiumgaming.data.newsletter.usecase.GetFlowNewsletterInboxesUseCase
import com.tritiumgaming.data.newsletter.usecase.GetNewsletterLastFetchDateFlowUseCase
import com.tritiumgaming.data.newsletter.usecase.SaveNewsletterInboxLastReadDateUseCase
import com.tritiumgaming.data.usecase.InitFlowUserPreferencesUseCase
import com.tritiumgaming.data.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.data.usecase.SaveCurrentTypographyUseCase
import com.tritiumgaming.data.usecase.SetAllowCellularDataUseCase
import com.tritiumgaming.data.usecase.SetAllowHuntWarnAudioUseCase
import com.tritiumgaming.data.usecase.SetAllowIntroductionUseCase
import com.tritiumgaming.data.usecase.SetDisableScreenSaverUseCase
import com.tritiumgaming.data.usecase.SetEnableGhostReorderUseCase
import com.tritiumgaming.data.usecase.SetEnableRTLUseCase
import com.tritiumgaming.data.usecase.SetMaxHuntWarnFlashTimeUseCase
import kotlinx.coroutines.Dispatchers

class HomeContainer(
    applicationContext: Context,
    dataStore: DataStore<Preferences>,
    val getSignInCredentialsUseCase: GetSignInCredentialsUseCase,
    val signInAccountUseCase: SignInAccountUseCase,
    val signOutAccountUseCase: SignOutAccountUseCase,
    val deactivateAccountUseCase: DeactivateAccountUseCase,
    val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase,
    val observeAccountUnlockedPalettesUseCase: ObserveAccountUnlockedPalettesUseCase,
    val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase,
    val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase,
    val setAllowCellularDataUseCase: SetAllowCellularDataUseCase,
    val setAllowHuntWarnAudioUseCase: SetAllowHuntWarnAudioUseCase,
    val setAllowIntroductionUseCase: SetAllowIntroductionUseCase,
    val setDisableScreenSaverUseCase: SetDisableScreenSaverUseCase,
    val setEnableGhostReorderUseCase: SetEnableGhostReorderUseCase,
    val setEnableRTLUseCase: SetEnableRTLUseCase,
    val setMaxHuntWarnFlashTimeUseCase: SetMaxHuntWarnFlashTimeUseCase,
    val getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
    val getDefaultLanguageUseCase: GetDefaultLanguageUseCase,
    val setDefaultLanguageUseCase: SetDefaultLanguageUseCase,
    val initFlowLanguageUseCase: InitFlowLanguageUseCase,
    val saveCurrentLanguageUseCase: SaveCurrentLanguageUseCase,
    val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase,
    val getAvailableTypographiesUseCase: GetMarketCatalogTypographiesUseCase,
    val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase,
    val findNextAvailableTypographyUseCase: GetNextUnlockedTypographyUseCase,
    val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
    val getAvailablePalettesUseCase: GetMarketCatalogPalettesUseCase,
    val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase,
    val findNextAvailablePaletteUseCase: GetNextUnlockedPaletteUseCase,
) {

    // App Info
    private val appInfoRepository: ContributorRepository by lazy {
        val appInfoLocalDataSource: ContributorDataSource = ContributorLocalDataSource()

        ContributorRepositoryImpl(
            localSource = appInfoLocalDataSource
        )
    }
    internal val getContributorsUseCase = ContributorsUseCase(
        appInfoRepository = appInfoRepository
    )

    // Newsletter
    private val newsletterRepository: NewsletterRepository by lazy {
        val newsletterLocalDataSource: NewsletterLocalDataSource = NewsletterLocalDataSourceImpl(
            applicationContext = applicationContext
        )
        val newsletterRemoteDataSource: NewsletterRemoteDataSource = NewsletterRemoteDataSourceImpl(
            newsletterApi = NewsletterService(),
            dispatcher = Dispatchers.IO
        )
        val newsletterDatastore = NewsletterDatastoreDataSource(
            context = applicationContext,
            dataStore = dataStore
        )
        val connectivityManagerHelper = ConnectivityManagerHelper(
            applicationContext = applicationContext
        )

        NewsletterRepositoryImpl(
            localDataSource = newsletterLocalDataSource,
            remoteDataSource = newsletterRemoteDataSource,
            dataStoreSource = newsletterDatastore,
            connectivityManagerHelper = connectivityManagerHelper,
            coroutineDispatcher = Dispatchers.IO
        )
    }
    /*internal val setupNewsletterUseCase = SetupNewsletterUseCase(
        repository = newsletterRepository
    )*/
    internal val getFlowNewsletterDatastoreUseCase = GetFlowNewsletterDatastoreUseCase(
        repository = newsletterRepository
    )
    internal val getFlowNewsletterInboxesUseCase = GetFlowNewsletterInboxesUseCase(
        repository = newsletterRepository
    )
    internal val getNewsletterLastFetchDateFlowUseCase = GetNewsletterLastFetchDateFlowUseCase(
        repository = newsletterRepository
    )
    internal val getNewsletterInboxesUseCase = FetchNewsletterInboxesUseCase(
        repository = newsletterRepository
    )
    internal val saveNewsletterInboxLastReadDateUseCase = SaveNewsletterInboxLastReadDateUseCase(
        repository = newsletterRepository
    )

}
