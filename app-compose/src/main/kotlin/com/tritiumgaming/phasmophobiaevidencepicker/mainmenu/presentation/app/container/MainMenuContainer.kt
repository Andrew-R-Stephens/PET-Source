package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.network.ConnectivityManagerHelper
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.repository.AppInfoRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.source.local.AppInfoLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.repository.NewsletterRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.datastore.NewsletterDatastoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.NewsletterRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.api.NewsletterService
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.source.AppInfoDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.usecase.GetSpecialThanksUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase.FetchNewsletterInboxesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase.InitFlowNewsletterUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase.SaveNewsletterInboxLastReadDateUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase.SetupNewsletterUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase.SyncNewsletterInboxesUseCase
import kotlinx.coroutines.Dispatchers

class MainMenuContainer(
    applicationContext: Context,
    dataStore: DataStore<Preferences>
) {

    // App Info
    private val appInfoLocalDataSource: AppInfoDataSource = AppInfoLocalDataSource()
    private val appInfoRepository: AppInfoRepositoryImpl =
        AppInfoRepositoryImpl(
            localSource = appInfoLocalDataSource
        )
    internal val getSpecialThanksUseCase = GetSpecialThanksUseCase(appInfoRepository)

    // Newsletter
    private val newsletterLocalDataSource: NewsletterLocalDataSource = NewsletterLocalDataSource(applicationContext)
    private val newsletterRemoteDataSource: NewsletterRemoteDataSource = NewsletterRemoteDataSource(
        newsletterApi = NewsletterService()
    )
    private val newsletterDatastore: NewsletterDatastoreDataSource =
        NewsletterDatastoreDataSource(
            context = applicationContext,
            dataStore = dataStore
        )
    private val connectivityManagerHelper: ConnectivityManagerHelper =
        ConnectivityManagerHelper(applicationContext)
    private val newsletterRepository: NewsletterRepositoryImpl =
        NewsletterRepositoryImpl(
            localDataSource = newsletterLocalDataSource,
            remoteDataSource = newsletterRemoteDataSource,
            dataStoreSource = newsletterDatastore,
            connectivityManagerHelper = connectivityManagerHelper,
            coroutineDispatcher = Dispatchers.IO
        )
    internal val setupNewsletterUseCase = SetupNewsletterUseCase(
        repository = newsletterRepository)
    internal val initFlowNewsletterUseCase = InitFlowNewsletterUseCase(
        repository = newsletterRepository)
    internal val getNewsletterInboxesUseCase = FetchNewsletterInboxesUseCase(
        repository = newsletterRepository)
    internal val syncNewsletterInboxesUseCase = SyncNewsletterInboxesUseCase(
        repository = newsletterRepository)
    internal val saveNewsletterInboxLastReadDateUseCase = SaveNewsletterInboxLastReadDateUseCase(
        repository = newsletterRepository)

}
