package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifiers.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.dto.WorldMapModifierDto

interface MapModifiersDataSource {

    fun fetchSizeModifiers(): Result<List<WorldMapModifierDto>>

}


