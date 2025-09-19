package com.tritiumgaming.shared.operation.domain.map.modifier.repsitory

import com.tritiumgaming.shared.operation.domain.map.modifier.model.WorldMapModifier

interface MapModifiersRepository {

    fun getModifiers(): Result<List<WorldMapModifier>>

}