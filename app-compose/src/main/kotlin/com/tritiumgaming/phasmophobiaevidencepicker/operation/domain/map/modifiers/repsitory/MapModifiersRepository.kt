package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifiers.repsitory

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.WorldMapModifier

interface MapModifiersRepository {

    fun getModifiers(): Result<List<WorldMapModifier>>

}