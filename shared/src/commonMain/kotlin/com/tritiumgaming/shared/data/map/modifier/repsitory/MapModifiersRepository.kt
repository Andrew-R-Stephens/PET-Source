package com.tritiumgaming.shared.data.map.modifier.repsitory

import com.tritiumgaming.shared.data.map.modifier.model.WorldMapModifier

interface MapModifiersRepository {

    fun getModifiers(): Result<List<WorldMapModifier>>

}