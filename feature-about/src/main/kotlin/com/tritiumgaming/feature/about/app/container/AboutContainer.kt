package com.tritiumgaming.feature.about.app.container

import com.tritiumgaming.shared.data.contributor.repository.ContributorRepositoryImpl
import com.tritiumgaming.shared.data.contributor.source.ContributorDataSource
import com.tritiumgaming.shared.data.contributor.source.local.ContributorLocalDataSource
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
