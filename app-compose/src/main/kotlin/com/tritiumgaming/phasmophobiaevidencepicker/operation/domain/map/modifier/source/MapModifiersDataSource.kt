package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.source

interface MapModifiersDataSource {

    fun fetchSizeModifiers(): Result<List<com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.dto.WorldMapModifierDto>>

}