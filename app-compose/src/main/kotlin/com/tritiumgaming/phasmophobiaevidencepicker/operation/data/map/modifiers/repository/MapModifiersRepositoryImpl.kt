package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifiers.repsitory.MapModifiersRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifiers.source.local.MapModifiersDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.WorldMapModifier

class MapModifiersRepositoryImpl(
    val localSource: MapModifiersDataSource
): MapModifiersRepository {

    var simpleModifiers: List<WorldMapModifier> = emptyList()

    override fun getModifiers(): Result<List<WorldMapModifier>> = Result.success(simpleModifiers)

    fun sync() {
        simpleModifiers = localSource.fetchSizeModifiers().getOrDefault(emptyList()).toDomain()
    }

    init {
        sync()
    }

}