package com.tritiumgaming.data.map.modifiers.repository

import com.tritiumgaming.data.map.modifiers.dto.toDomain
import com.tritiumgaming.data.map.modifiers.source.MapModifiersDataSource
import com.tritiumgaming.shared.data.map.modifier.model.WorldMapModifier
import com.tritiumgaming.shared.data.map.modifier.repsitory.MapModifiersRepository

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