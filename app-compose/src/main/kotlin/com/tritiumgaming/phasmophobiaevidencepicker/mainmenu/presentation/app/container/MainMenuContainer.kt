package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.core.common.network.ConnectivityManagerHelper
import com.tritiumgaming.data.contributor.repository.AppInfoRepositoryImpl
import com.tritiumgaming.data.contributor.source.ContributorDataSource
import com.tritiumgaming.data.contributor.source.local.ContributorLocalDataSource
import com.tritiumgaming.data.newsletter.repository.NewsletterRepositoryImpl
import com.tritiumgaming.data.newsletter.source.datastore.NewsletterDatastoreDataSource
import com.tritiumgaming.data.newsletter.source.local.NewsletterLocalDataSource
import com.tritiumgaming.data.newsletter.source.local.NewsletterLocalDataSourceImpl
import com.tritiumgaming.data.newsletter.source.remote.NewsletterRemoteDataSource
import com.tritiumgaming.data.newsletter.source.remote.NewsletterRemoteDataSourceImpl
import com.tritiumgaming.data.newsletter.source.remote.api.NewsletterService
import com.tritiumgaming.shared.mainmenu.domain.appinfo.usecase.GetSpecialThanksUseCase
import com.tritiumgaming.shared.mainmenu.domain.newsletter.repository.NewsletterRepository
import com.tritiumgaming.shared.mainmenu.domain.newsletter.usecase.FetchNewsletterInboxesUseCase
import com.tritiumgaming.shared.mainmenu.domain.newsletter.usecase.InitFlowNewsletterUseCase
import com.tritiumgaming.shared.mainmenu.domain.newsletter.usecase.SaveNewsletterInboxLastReadDateUseCase
import com.tritiumgaming.shared.mainmenu.domain.newsletter.usecase.SetupNewsletterUseCase
import kotlinx.coroutines.Dispatchers

class MainMenuContainer(
    applicationContext: Context,
    dataStore: DataStore<Preferences>
) {

    // App Info
    private val appInfoLocalDataSource: ContributorDataSource = ContributorLocalDataSource()
    private val appInfoRepository: AppInfoRepositoryImpl =
        AppInfoRepositoryImpl(
            localSource = appInfoLocalDataSource
        )
    internal val getSpecialThanksUseCase = GetSpecialThanksUseCase(appInfoRepository)

    // Newsletter
    private val newsletterLocalDataSource: NewsletterLocalDataSource = NewsletterLocalDataSourceImpl(applicationContext)
    private val newsletterRemoteDataSource: NewsletterRemoteDataSource = NewsletterRemoteDataSourceImpl(
        newsletterApi = NewsletterService()
    )
    private val newsletterDatastore: NewsletterDatastoreDataSource =
        NewsletterDatastoreDataSource(
            context = applicationContext,
            dataStore = dataStore
        )
    private val connectivityManagerHelper: ConnectivityManagerHelper =
        ConnectivityManagerHelper(applicationContext)
    private val newsletterRepository: NewsletterRepository =
        NewsletterRepositoryImpl(
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
