package com.tritiumgaming.data.map.modifier.repsitory

import com.tritiumgaming.data.map.modifier.model.WorldMapModifier

interface MapModifiersRepository {

    fun getModifiers(): Result<List<WorldMapModifier>>

}