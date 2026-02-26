package com.tritiumgaming.feature.home.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.core.common.network.ConnectivityManagerHelper
import com.tritiumgaming.data.contributor.repository.ContributorRepositoryImpl
import com.tritiumgaming.data.contributor.source.ContributorDataSource
import com.tritiumgaming.data.contributor.source.local.ContributorLocalDataSource
import com.tritiumgaming.data.newsletter.repository.NewsletterRepositoryImpl
import com.tritiumgaming.data.newsletter.source.datastore.NewsletterDatastoreDataSource
import com.tritiumgaming.data.newsletter.source.local.NewsletterLocalDataSource
import com.tritiumgaming.data.newsletter.source.local.NewsletterLocalDataSourceImpl
import com.tritiumgaming.data.newsletter.source.remote.NewsletterRemoteDataSource
import com.tritiumgaming.data.newsletter.source.remote.NewsletterRemoteDataSourceImpl
import com.tritiumgaming.data.newsletter.source.remote.api.NewsletterService
import com.tritiumgaming.shared.core.domain.market.user.usecase.DeactivateAccountUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.GetSignInCredentialsUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignInAccountUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignOutAccountUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.shared.data.contributor.repository.ContributorRepository
import com.tritiumgaming.shared.data.contributor.usecase.ContributorsUseCase
import com.tritiumgaming.shared.data.language.usecase.GetAvailableLanguagesUseCase
import com.tritiumgaming.shared.data.language.usecase.GetDefaultLanguageUseCase
import com.tritiumgaming.shared.data.language.usecase.InitFlowLanguageUseCase
import com.tritiumgaming.shared.data.language.usecase.SaveCurrentLanguageUseCase
import com.tritiumgaming.shared.data.language.usecase.SetDefaultLanguageUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPalettesUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetNextUnlockedPaletteUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographiesUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetNextUnlockedTypographyUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.SaveCurrentTypographyUseCase
import com.tritiumgaming.shared.data.newsletter.repository.NewsletterRepository
import com.tritiumgaming.shared.data.newsletter.usecase.FetchNewsletterInboxesUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.GetFlowNewsletterDatastoreUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.GetFlowNewsletterInboxesUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.SaveNewsletterInboxLastReadDateUseCase
import com.tritiumgaming.shared.data.preferences.usecase.InitFlowUserPreferencesUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetAllowCellularDataUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetAllowHuntWarnAudioUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetAllowIntroductionUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetDisableScreenSaverUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetEnableGhostReorderUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetEnableRTLUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetMaxHuntWarnFlashTimeUseCase
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
    internal val getNewsletterInboxesUseCase = FetchNewsletterInboxesUseCase(
        repository = newsletterRepository
    )
    internal val saveNewsletterInboxLastReadDateUseCase = SaveNewsletterInboxLastReadDateUseCase(
        repository = newsletterRepository
    )

}
