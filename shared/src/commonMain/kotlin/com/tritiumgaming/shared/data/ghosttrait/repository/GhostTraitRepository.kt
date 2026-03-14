package com.tritiumgaming.shared.data.ghosttrait.repository

import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.shared.data.ghosttrait.model.GhostTrait

interface GhostTraitRepository {
    fun getAllTraits(): Result<List<GhostTrait>>

    fun getByCategory(category: TraitCategory): Result<List<GhostTrait>>
}
