package com.tritiumgaming.shared.data.investigation.usecase

import com.tritiumgaming.shared.data.investigation.InvestigationRepository
import com.tritiumgaming.shared.data.investigation.model.SanityData


class UpdateInvestigationSanityUseCase(private val repository: InvestigationRepository) {
    //operator fun invoke(insanity: Float, sanity: Float) = repository.updateSanity(insanity, sanity)
    operator fun invoke(sanityData: SanityData) = repository.updateSanity(sanityData)
}
