package com.tritiumgaming.data.trait.source

import com.tritiumgaming.data.trait.dto.GhostTraitDto

interface GhostTraitDataSource {

    fun get(): List<GhostTraitDto>

}