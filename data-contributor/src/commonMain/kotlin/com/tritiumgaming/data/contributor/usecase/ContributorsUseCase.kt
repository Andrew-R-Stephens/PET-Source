package com.tritiumgaming.data.contributor.usecase

import com.tritiumgaming.data.contributor.model.Contributor
import com.tritiumgaming.data.contributor.repository.ContributorRepository

class ContributorsUseCase(
    private val appInfoRepository: ContributorRepository
) {
    operator fun invoke(): Result<List<Contributor>> {
        val result = appInfoRepository.getSpecialThanks()

        return result
    }
}
    