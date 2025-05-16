package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.repository.AppInfoRepository
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.source.local.AppInfoLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.repository.NewsletterRepository
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.datastore.NewsletterDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.NewsletterRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.newsletter.handler.NewsletterManager

class MainMenuContainer(context: Context, dataStore: DataStore<Preferences>) {

    private val appInfoLocalDataSource: AppInfoLocalDataSource = AppInfoLocalDataSource()
    val appInfoRepository: AppInfoRepository =
        AppInfoRepository(
            context = context,
            localSource = appInfoLocalDataSource
        )

    private val newsletterLocalDataSource: NewsletterLocalDataSource = NewsletterLocalDataSource()
    private val newsletterRemoteDataSource: NewsletterRemoteDataSource = NewsletterRemoteDataSource()
    private val newsletterRepository: NewsletterRepository =
        NewsletterRepository(
            context = context,
            localDataSource = newsletterLocalDataSource,
            remoteDataSource = newsletterRemoteDataSource
        )
    private val newsletterDatastore: NewsletterDatastore =
        NewsletterDatastore(
            context = context,
            dataStore = dataStore
        )
    val newsletterManager: NewsletterManager = NewsletterManager(
        repository = newsletterRepository,
        datastore = newsletterDatastore
    )

}
