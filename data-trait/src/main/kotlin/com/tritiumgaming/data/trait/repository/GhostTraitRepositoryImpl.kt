package com.tritiumgaming.data.trait.repository

import com.tritiumgaming.data.trait.dto.toDomain
import com.tritiumgaming.data.trait.source.GhostTraitDataSource
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.shared.data.ghosttrait.model.GhostTrait
import com.tritiumgaming.shared.data.ghosttrait.repository.GhostTraitRepository

class GhostTraitRepositoryImpl(
    private val localSource: GhostTraitDataSource
): GhostTraitRepository {

    var ghostTraits: List<GhostTrait> = emptyList()

    private fun populateCache(): List<GhostTrait> {
        ghostTraits = ghostTraits.ifEmpty {
            localSource.get().map { it.toDomain() }
        }
        return ghostTraits
    }

    override fun getAllTraits(): Result<List<GhostTrait>> {
        return Result.success(populateCache())
    }

    override fun getByCategory(category: TraitCategory): Result<List<GhostTrait>> {
        val data = populateCache()
            .filter { it.category == category }
            .sortedBy { it.id }

        return Result.success(data)
    }

}
