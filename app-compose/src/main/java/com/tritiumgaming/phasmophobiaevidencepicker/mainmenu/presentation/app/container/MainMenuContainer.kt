package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.repository.AppInfoRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.source.local.AppInfoLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.repository.NewsletterRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.datastore.NewsletterDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.NewsletterRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.source.AppInfoDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.newsletter.handler.NewsletterManager

class MainMenuContainer(
    applicationContext: Context,
    dataStore: DataStore<Preferences>
) {

    private val appInfoLocalDataSource: AppInfoDataSource = AppInfoLocalDataSource(applicationContext)
    val appInfoRepository: AppInfoRepositoryImpl =
        AppInfoRepositoryImpl(
            localSource = appInfoLocalDataSource
        )

    private val newsletterLocalDataSource: NewsletterLocalDataSource = NewsletterLocalDataSource(applicationContext)
    private val newsletterRemoteDataSource: NewsletterRemoteDataSource = NewsletterRemoteDataSource()
    private val newsletterRepository: NewsletterRepositoryImpl =
        NewsletterRepositoryImpl(
            localDataSource = newsletterLocalDataSource,
            remoteDataSource = newsletterRemoteDataSource
        )
    private val newsletterDatastore: NewsletterDatastore =
        NewsletterDatastore(
            context = applicationContext,
            dataStore = dataStore
        )
    val newsletterManager: NewsletterManager = NewsletterManager(
        repository = newsletterRepository,
        datastore = newsletterDatastore
    )

}
