package com.tritiumgaming.shared.home.domain.appinfo.usecase

import com.tritiumgaming.shared.home.domain.appinfo.model.Contributor
import com.tritiumgaming.shared.home.domain.appinfo.repository.ContributorRepository

class ContributorsUseCase(
    private val appInfoRepository: ContributorRepository
) {
    operator fun invoke(): Result<List<Contributor>> {
        val result = appInfoRepository.getSpecialThanks()

        return result
    }
}
    