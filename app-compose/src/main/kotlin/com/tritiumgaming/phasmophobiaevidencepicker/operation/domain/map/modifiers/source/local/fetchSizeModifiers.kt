package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifiers.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.WorldMapModifier

interface MapModifiersDataSource {

    fun fetchSizeModifiers(): Result<List<WorldMapModifier>>

}


