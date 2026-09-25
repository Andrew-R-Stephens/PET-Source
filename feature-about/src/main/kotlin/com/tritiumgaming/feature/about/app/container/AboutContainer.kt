package com.tritiumgaming.feature.about.app.container

import com.tritiumgaming.data.contributor.repository.ContributorRepositoryImpl
import com.tritiumgaming.data.contributor.source.ContributorDataSource
import com.tritiumgaming.data.contributor.source.local.ContributorLocalDataSource
import com.tritiumgaming.shared.data.contributor.repository.ContributorRepository
import com.tritiumgaming.shared.data.contributor.usecase.ContributorsUseCase

class AboutContainer {

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

}
