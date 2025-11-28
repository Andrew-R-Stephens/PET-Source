package com.tritiumgaming.shared.data.contributor.usecase

class ContributorsUseCase(
    private val appInfoRepository: com.tritiumgaming.shared.data.contributor.repository.ContributorRepository
) {
    operator fun invoke(): Result<List<com.tritiumgaming.shared.data.contributor.model.Contributor>> {
        val result = appInfoRepository.getSpecialThanks()

        return result
    }
}
    