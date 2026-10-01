package com.tritiumgaming.data.trait.repository

import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.data.trait.model.GhostTrait

interface GhostTraitRepository {
    fun getAllTraits(): Result<List<GhostTrait>>

    fun getByCategory(category: TraitCategory): Result<List<GhostTrait>>

    fun getString(resId: Int): String
}
