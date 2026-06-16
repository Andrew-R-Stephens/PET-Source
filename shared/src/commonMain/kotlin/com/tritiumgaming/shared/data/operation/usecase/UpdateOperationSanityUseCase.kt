package com.tritiumgaming.shared.data.operation.usecase

import com.tritiumgaming.shared.data.operation.OperationRepository
import com.tritiumgaming.shared.data.operation.model.SanityData


class UpdateOperationSanityUseCase(private val repository: OperationRepository) {
    //operator fun invoke(insanity: Float, sanity: Float) = repository.updateSanity(insanity, sanity)
    operator fun invoke(sanityData: SanityData) = repository.updateSanity(sanityData)
}
