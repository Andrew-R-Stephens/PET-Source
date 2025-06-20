package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.dto.WorldMapModifierDto

interface MapModifiersDataSource {

    fun fetchSizeModifiers(): Result<List<WorldMapModifierDto>>

}