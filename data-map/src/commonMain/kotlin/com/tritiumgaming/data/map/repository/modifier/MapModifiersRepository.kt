package com.tritiumgaming.data.map.repository.modifier

import com.tritiumgaming.data.map.model.modifier.WorldMapModifier

interface MapModifiersRepository {

    fun getModifiers(): Result<List<WorldMapModifier>>

}