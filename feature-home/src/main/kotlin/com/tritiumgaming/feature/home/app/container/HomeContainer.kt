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
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetAllowCellularDataUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetAllowHuntWarnAudioUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetAllowIntroductionUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetDisableScreenSaverUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetEnableGhostReorderUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetEnableRTLUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetMaxHuntWarnFlashTimeUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.setup.InitFlowGlobalPreferencesUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.setup.SetupGlobalPreferencesUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.GetAvailableLanguagesUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.GetCurrentLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.GetDefaultLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.InitFlowLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.LoadCurrentLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.SaveCurrentLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.SetDefaultLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.SetupLanguageUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.FindNextAvailablePaletteUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.GetAvailablePalettesUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.GetPaletteByUUIDUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.setup.InitFlowPaletteUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.setup.InitPaletteDataStoreUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.FindNextAvailableTypographyUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.GetAvailableTypographiesUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.GetTypographyByUUIDUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.SaveCurrentTypographyUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.setup.InitFlowTypographyUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.setup.InitTypographyDataStoreUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignInAccountUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.setup.InitFlowReviewTrackerUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.setup.SetupReviewTrackerUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.status.GetReviewRequestStatusUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.status.LoadReviewRequestStatusUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.status.SetReviewRequestStatusUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timealive.GetAppTimeAliveUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timealive.LoadAppTimeAliveUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timealive.SetAppTimeAliveUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timesopened.GetAppTimesOpenedUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timesopened.LoadAppTimesOpenedUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timesopened.SetAppTimesOpenedUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.account.DeactivateAccountUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.account.GetSignInCredentialsUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.account.SignOutAccountUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.shared.home.domain.appinfo.repository.ContributorRepository
import com.tritiumgaming.shared.home.domain.appinfo.usecase.ContributorUseCase
import com.tritiumgaming.shared.home.domain.newsletter.repository.NewsletterRepository
import com.tritiumgaming.shared.home.domain.newsletter.usecase.FetchNewsletterInboxesUseCase
import com.tritiumgaming.shared.home.domain.newsletter.usecase.InitFlowNewsletterUseCase
import com.tritiumgaming.shared.home.domain.newsletter.usecase.SaveNewsletterInboxLastReadDateUseCase
import com.tritiumgaming.shared.home.domain.newsletter.usecase.SetupNewsletterUseCase
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
    val setupGlobalPreferencesUseCase: SetupGlobalPreferencesUseCase,
    val initFlowGlobalPreferencesUseCase: InitFlowGlobalPreferencesUseCase,
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
    val initLanguageDataStoreUseCase: SetupLanguageUseCase,
    val initFlowLanguageUseCase: InitFlowLanguageUseCase,
    val saveCurrentLanguageUseCase: SaveCurrentLanguageUseCase,
    val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    val loadCurrentLanguageUseCase: LoadCurrentLanguageUseCase,
    val initTypographyDataStoreUseCase: InitTypographyDataStoreUseCase,
    val initFlowTypographyUseCase: InitFlowTypographyUseCase,
    val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase,
    val getAvailableTypographiesUseCase: GetAvailableTypographiesUseCase,
    val getTypographyByUUIDUseCase: GetTypographyByUUIDUseCase,
    val findNextAvailableTypographyUseCase: FindNextAvailableTypographyUseCase,
    val initPaletteDataStoreUseCase: InitPaletteDataStoreUseCase,
    val initFlowPaletteUseCase: InitFlowPaletteUseCase,
    val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
    val getAvailablePalettesUseCase: GetAvailablePalettesUseCase,
    val getPaletteByUUIDUseCase: GetPaletteByUUIDUseCase,
    val findNextAvailablePaletteUseCase: FindNextAvailablePaletteUseCase,
    val initReviewTrackerDataStoreUseCase: SetupReviewTrackerUseCase,
    val initFlowReviewTrackerUseCase: InitFlowReviewTrackerUseCase,
    val setReviewRequestStatusUseCase: SetReviewRequestStatusUseCase,
    val getReviewRequestStatusUseCase: GetReviewRequestStatusUseCase,
    val loadReviewRequestStatusUseCase: LoadReviewRequestStatusUseCase,
    val setAppTimeAliveUseCase: SetAppTimeAliveUseCase,
    val getAppTimeAliveUseCase: GetAppTimeAliveUseCase,
    val loadAppTimeAliveUseCase: LoadAppTimeAliveUseCase,
    val setAppTimesOpenedUseCase: SetAppTimesOpenedUseCase,
    val getAppTimesOpenedUseCase: GetAppTimesOpenedUseCase,
    val loadAppTimesOpenedUseCase: LoadAppTimesOpenedUseCase
) {

    // App Info
    private val appInfoLocalDataSource: ContributorDataSource = ContributorLocalDataSource()
    private val appInfoRepository: ContributorRepository = ContributorRepositoryImpl(
        localSource = appInfoLocalDataSource
    )
    internal val getContributorsUseCase = ContributorUseCase(
        appInfoRepository = appInfoRepository
    )

    // Newsletter
    private val newsletterLocalDataSource: NewsletterLocalDataSource = NewsletterLocalDataSourceImpl(
        applicationContext = applicationContext
    )
    private val newsletterRemoteDataSource: NewsletterRemoteDataSource = NewsletterRemoteDataSourceImpl(
        newsletterApi = NewsletterService()
    )
    private val newsletterDatastore: NewsletterDatastoreDataSource = NewsletterDatastoreDataSource(
        context = applicationContext,
        dataStore = dataStore
    )
    private val connectivityManagerHelper: ConnectivityManagerHelper = ConnectivityManagerHelper(
        applicationContext = applicationContext
    )

    private val newsletterRepository: NewsletterRepository = NewsletterRepositoryImpl(
        localDataSource = newsletterLocalDataSource,
        remoteDataSource = newsletterRemoteDataSource,
        dataStoreSource = newsletterDatastore,
        connectivityManagerHelper = connectivityManagerHelper,
        coroutineDispatcher = Dispatchers.IO
    )
    internal val setupNewsletterUseCase = SetupNewsletterUseCase(
        repository = newsletterRepository
    )
    internal val initFlowNewsletterUseCase = InitFlowNewsletterUseCase(
        repository = newsletterRepository
    )
    internal val getNewsletterInboxesUseCase = FetchNewsletterInboxesUseCase(
        repository = newsletterRepository
    )
    internal val saveNewsletterInboxLastReadDateUseCase = SaveNewsletterInboxLastReadDateUseCase(
        repository = newsletterRepository
    )

}
