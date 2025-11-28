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
import com.tritiumgaming.shared.data.preferences.usecase.preferences.SetAllowCellularDataUseCase
import com.tritiumgaming.shared.data.preferences.usecase.preferences.SetAllowHuntWarnAudioUseCase
import com.tritiumgaming.shared.data.preferences.usecase.preferences.SetAllowIntroductionUseCase
import com.tritiumgaming.shared.data.preferences.usecase.preferences.SetDisableScreenSaverUseCase
import com.tritiumgaming.shared.data.preferences.usecase.preferences.SetEnableGhostReorderUseCase
import com.tritiumgaming.shared.data.preferences.usecase.preferences.SetEnableRTLUseCase
import com.tritiumgaming.shared.data.preferences.usecase.preferences.SetMaxHuntWarnFlashTimeUseCase
import com.tritiumgaming.shared.data.preferences.usecase.setup.InitFlowGlobalPreferencesUseCase
import com.tritiumgaming.shared.data.preferences.usecase.setup.SetupGlobalPreferencesUseCase
import com.tritiumgaming.shared.data.language.usecase.GetAvailableLanguagesUseCase
import com.tritiumgaming.shared.data.language.usecase.GetCurrentLanguageUseCase
import com.tritiumgaming.shared.data.language.usecase.GetDefaultLanguageUseCase
import com.tritiumgaming.shared.data.language.usecase.InitFlowLanguageUseCase
import com.tritiumgaming.shared.data.language.usecase.LoadCurrentLanguageUseCase
import com.tritiumgaming.shared.data.language.usecase.SaveCurrentLanguageUseCase
import com.tritiumgaming.shared.data.language.usecase.SetDefaultLanguageUseCase
import com.tritiumgaming.shared.data.language.usecase.SetupLanguageUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.FindNextAvailablePaletteUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetAvailablePalettesUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.FindNextAvailableTypographyUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetAvailableTypographiesUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetTypographyByUUIDUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.SaveCurrentTypographyUseCase
import com.tritiumgaming.shared.data.review.usecase.setup.InitFlowReviewTrackerUseCase
import com.tritiumgaming.shared.data.review.usecase.setup.SetupReviewTrackerUseCase
import com.tritiumgaming.shared.data.review.usecase.status.GetReviewRequestStatusUseCase
import com.tritiumgaming.shared.data.review.usecase.status.LoadReviewRequestStatusUseCase
import com.tritiumgaming.shared.data.review.usecase.status.SetReviewRequestStatusUseCase
import com.tritiumgaming.shared.data.review.usecase.timealive.GetAppTimeAliveUseCase
import com.tritiumgaming.shared.data.review.usecase.timealive.LoadAppTimeAliveUseCase
import com.tritiumgaming.shared.data.review.usecase.timealive.SetAppTimeAliveUseCase
import com.tritiumgaming.shared.data.review.usecase.timesopened.GetAppTimesOpenedUseCase
import com.tritiumgaming.shared.data.review.usecase.timesopened.LoadAppTimesOpenedUseCase
import com.tritiumgaming.shared.data.review.usecase.timesopened.SetAppTimesOpenedUseCase
import com.tritiumgaming.shared.data.newsletter.repository.NewsletterRepository
import com.tritiumgaming.shared.data.newsletter.usecase.FetchNewsletterInboxesUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.GetFlowNewsletterDatastoreUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.GetFlowNewsletterInboxesUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.SaveNewsletterInboxLastReadDateUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.SetupNewsletterUseCase
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
    val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase,
    val getAvailableTypographiesUseCase: GetAvailableTypographiesUseCase,
    val getTypographyByUUIDUseCase: GetTypographyByUUIDUseCase,
    val findNextAvailableTypographyUseCase: FindNextAvailableTypographyUseCase,
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
    internal val setupNewsletterUseCase = SetupNewsletterUseCase(
        repository = newsletterRepository
    )
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
