package com.tritiumgaming.data.map.modifiers.source

import com.tritiumgaming.data.map.modifiers.dto.WorldMapModifierDto

interface MapModifiersDataSource {

    fun fetchSizeModifiers(): Result<List<WorldMapModifierDto>>

}