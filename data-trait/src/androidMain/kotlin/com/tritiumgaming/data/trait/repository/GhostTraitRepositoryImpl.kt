package com.tritiumgaming.data.trait.repository

import android.content.Context
import com.tritiumgaming.data.trait.dto.toDomain
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.data.trait.model.GhostTrait
import com.tritiumgaming.data.trait.source.GhostTraitDataSource

class GhostTraitRepositoryImpl(
    private val applicationContext: Context,
    private val localSource: GhostTraitDataSource
): GhostTraitRepository {

    private var ghostTraits: List<GhostTrait> = emptyList()

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

    override fun getString(resId: Int): String {
        return applicationContext.getString(resId)
    }

}
