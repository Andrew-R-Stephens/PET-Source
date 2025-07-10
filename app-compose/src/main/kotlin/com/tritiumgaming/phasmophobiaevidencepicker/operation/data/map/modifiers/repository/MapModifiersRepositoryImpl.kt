package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.source.MapModifiersDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.model.WorldMapModifier
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.repsitory.MapModifiersRepository

class MapModifiersRepositoryImpl(
    val localSource: MapModifiersDataSource
): MapModifiersRepository {

    var simpleModifiers: List<WorldMapModifier> = emptyList()

    override fun getModifiers(): Result<List<WorldMapModifier>> {
        if(simpleModifiers.isEmpty()) {
            simpleModifiers = localSource.fetchSizeModifiers().getOrDefault(emptyList()).toDomain()
        }

        return Result.success(simpleModifiers)
    }

}