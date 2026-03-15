package com.tritiumgaming.shared.data.investigation.usecase

import com.tritiumgaming.shared.data.investigation.InvestigationRepository


class UpdateInvestigationSanityUseCase(private val repository: InvestigationRepository) {
    operator fun invoke(insanity: Float, sanity: Float) = repository.updateSanity(insanity, sanity)
}
