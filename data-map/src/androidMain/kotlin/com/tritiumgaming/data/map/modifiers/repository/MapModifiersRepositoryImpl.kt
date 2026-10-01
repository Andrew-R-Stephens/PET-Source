package com.tritiumgaming.data.map.modifiers.repository

import com.tritiumgaming.data.map.modifier.model.WorldMapModifier
import com.tritiumgaming.data.map.modifier.repsitory.MapModifiersRepository
import com.tritiumgaming.data.map.modifiers.dto.toDomain
import com.tritiumgaming.data.map.modifiers.source.MapModifiersDataSource

class MapModifiersRepositoryImpl(
    val localSource: MapModifiersDataSource
): MapModifiersRepository {

    var simpleModifiers: List<WorldMapModifier> = emptyList()

    override fun getModifiers(): Result<List<WorldMapModifier>> {
        if(simpleModifiers.isEmpty()) {
            simpleModifiers = localSource.fetchSizeModifiers()
                .getOrDefault(emptyList()).toDomain()
        }

        return Result.success(simpleModifiers)
    }

}